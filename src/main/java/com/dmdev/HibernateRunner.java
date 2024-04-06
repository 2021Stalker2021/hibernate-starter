package com.dmdev;

import com.dmdev.entity.*;
import com.dmdev.converter.BirthdayConverter;
import com.dmdev.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
public class HibernateRunner {

//    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class); или используем @Slf4j

    public static void main(String[] args) throws SQLException {
        Company company = Company.builder()
                .name("Google")
                .build();
        User user = User.builder()
                .username("petr2@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .lastname("Petrov")
                        .firstname("Petr")
                        .birthDate(new Birthday(LocalDate.of(2000, 1, 2)))
                        .build())
                .company(company)
                .build();


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();
                User user1 = session1.get(User.class, 4L);
//                session1.persist(company);
//                session1.persist(user);

                session1.getTransaction().commit();
            }
        }
    }
}
