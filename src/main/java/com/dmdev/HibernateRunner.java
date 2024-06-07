package com.dmdev;

import com.dmdev.dao.CompanyRepository;
import com.dmdev.dao.PaymentRepository;
import com.dmdev.dao.UserRepository;
import com.dmdev.dto.UserCreateDto;
import com.dmdev.entity.*;
import com.dmdev.interceptor.TransactionInterceptor;
import com.dmdev.mapper.CompanyReadMapper;
import com.dmdev.mapper.UserCreateMapper;
import com.dmdev.mapper.UserReadMapper;
import com.dmdev.service.UserService;
import com.dmdev.util.HibernateUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.assertj.core.internal.bytebuddy.ByteBuddy;
import org.assertj.core.internal.bytebuddy.implementation.MethodDelegation;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers;
import org.hibernate.*;


import java.lang.InstantiationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.time.LocalDate;


@Slf4j
public class HibernateRunner {

    //    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class); или используем @Slf4j
    @Transactional
    public static void main(String[] args) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            /**
             * Мы не можем использовать один и тот же оъект Типа(Session) так как он будет
             * открываться при использовании метода session.beginTransaction() и сразу закрываться,
             * и если мы будем в рамках другого потока использовать, например(CompanyRepository(session))
             * то, у нас ничего не выйдет, так как наша session уже была закрыта.
             * Поэтому мы должны использовать Proxy который будет ("каждый раз") получать нашу текущую сессию с помощью
             * sessionFactory.getCurrentSession().
             * Прокси обращается в SessionFactory и каждый раз получает getCurrentSession() и
             * используется для нашего PaymentRepository
             */

//            session.beginTransaction();

            var companyRepository = new CompanyRepository(session);

            var companyReadMapper = new CompanyReadMapper();
            var userReadMapper = new UserReadMapper(companyReadMapper);
            var userCreateMapper = new UserCreateMapper(companyRepository);

            var userRepository = new UserRepository(session);
            var paymentRepository = new PaymentRepository(session);
//            var userService = new UserService(userRepository, userCreateMapper, userReadMapper);
            var transactionInterceptor = new TransactionInterceptor(sessionFactory);

            var userService = new ByteBuddy()
                    .subclass(UserService.class)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(transactionInterceptor))
                    .make()
                    .load(UserService.class.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor(UserRepository.class, UserReadMapper.class, UserCreateMapper.class)
                    .newInstance(userRepository, userReadMapper, userCreateMapper);

            userService.findById(1L).ifPresent(System.out::println);

            UserCreateDto userCreateDto = new UserCreateDto(
                    PersonalInfo.builder()
                            .firstname("Liza")
                            .lastname("Stepanova")
                            .birthDate(LocalDate.now())
                            .build(),
                    "liza2@gmail.com",
                    null,
                    Role.USER,
                    1
            );
            userService.create(userCreateDto);

//            session.getTransaction().commit();

        }
    }


}
