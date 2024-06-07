package com.dmdev;

import com.dmdev.entity.*;
//import com.dmdev.util.HibernateTestUtil;
import com.dmdev.util.HibernateTestUtil;
import com.dmdev.util.HibernateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.QueryHint;
import jakarta.persistence.Table;
import lombok.Cleanup;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.QueryHints;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

class HibernateRunnerTest {

//    @Test
//    void checkHql() {
//        try (var sessionFactory = HibernateTestUtil.buildSessionFactory();
//             var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            String name = "Vadimov";
//            var result = session.createNamedQuery("findUserByName",User.class)
//                    .setParameter("firstname", name)
//                    .setParameter("companyName", "Razer")
//                    .setFlushMode(FlushModeType.COMMIT)
//                    .setHint(QueryHints.FETCH_SIZE, "50")
//                    .list();
//
//            var countRows = session.createQuery("update User u set u.role = 'ADMIN'")
//                    .executeUpdate();
//
//            session.createNativeQuery("select u.* from users u where u.firstname = 'Ivan' ", User.class);
//
//            session.getTransaction().commit();
//        }
//    }

//    @Test
//    void checkH2() {
//        try (var sessionFactory = HibernateTestUtil.buildSessionFactory();
//             var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            var google = Company.builder()
//                    .name("Google")
//                    .build();
//            session.persist(google);
//
//            Programmer programmer = Programmer.builder()
//                    .username("ivan@gmail.com")
//                    .language(Language.JAVA)
//                    .company(google)
//                    .build();
//            session.persist(programmer);
//
//            Manager manager = Manager.builder()
//                    .username("sveta@gmail.com")
//                    .projectName("Starter")
//                    .company(google)
//                    .build();
//            session.persist(manager);
//            session.flush();
//
//            session.clear();
//
//            var programmer1 = session.get(Programmer.class, 1L);
//            var manager1 = session.get(User.class, 9L);
//            System.out.println();
//            session.getTransaction().commit();
//        }
//    }

//    @Test
//    void localeInfo() {
//        try (var sessionFactory = HibernateTestUtil.buildSessionFactory();
//             var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            var company = session.get(Company.class, 12);
////            company.getLocales().add(LocaleInfo.of("ru", "Описание на русском"));
////            company.getLocales().add(LocaleInfo.of("en", "English description"));
////            System.out.println(company.getLocales());
//            company.getUsers().forEach((k, v) -> System.out.println(v));
//
//
//            session.getTransaction().commit();
//        }
//    }


//    @Test
//    void checkManyToMany() {
//        try (var sessionFactory = HibernateUtil.buildSessionFactory();
//             var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            var user = session.get(User.class, 9L);
//            var chat = session.get(Chat.class, 1L);
//
//            var userChat = UserChat.builder()
//                    .build();
//
//            userChat.setCreatedAt(Instant.now());
//            userChat.setCreatedBy(user.getUsername());
//            userChat.setUser(user);
//            userChat.setChat(chat);
//
//            session.persist(userChat);
////            user.getChats().clear();
//
////            var chat = Chat.builder()
////                    .name("dmdev")
////                    .build();
////
////            user.addChat(chat);
////            session.persist(chat);
//
//
//            session.getTransaction().commit();
//        }
//    }

//    @Test
//    void checkOneToOne() {
//        try (var sessionFactory = HibernateUtil.buildSessionFactory();
//             var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            var user = session.get(User.class, 9L);
//            System.out.println();
////            var user = User.builder()
////                    .username("test3@gmail.com")
////                    .build();
////
////            var profile = Profile.builder()
////                    .language("ru")
////                    .street("Centralnaya 18")
////                    .build();
////
////            profile.setUser(user);
////            session.persist(user); // сначала сохраняем нашего юзера для того чтобы установить его id в profile
//
//            session.getTransaction().commit();
//        }
//    }
//
//    @Test
//    void checkOrhanRemoval() {
//        try (var sessionFactory = HibernateUtil.buildSessionFactory();
//             var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            Company company = session.getReference(Company.class, 5); // Proxy object
////            company.getUsers().removeIf(user -> user.getId().equals(5L)); // удаление только пользователя
//            // при удалении нужно убрать Cascade.ALL чтобы не удаить наши компании при удаления пользователя
//
//            session.getTransaction().commit();
//         }
//    }

//    @Test
//    void checkLazyInitialization() {
//        Company company = null;
//        try (var sessionFactory = HibernateUtil.buildSessionFactory();
//                var session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            company = session.getReference(Company.class, 5);
//
//            session.getTransaction().commit();
//        }
//        var users = company.getUsers();
//        System.out.println(users.size());
//    }

//    @Test
//    void deleteCompany() {
//        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
//        @Cleanup var session = sessionFactory.openSession();
//        session.beginTransaction();
//
//
//        session.clear();
//
//        session.getTransaction().commit();
//    }
//
//    @Test
//    void addUserToNewCompany() {
//        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
//        @Cleanup var session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var company = Company.builder()
//                .name("Google")
//                .build();
//
////        var user = User.builder()
////                .username("Oleg@gmail.com")
////                .build();
////
////        company.addUser(user);
//
//        session.persist(company);
//
//        session.getTransaction().commit();
//    }


//    @Test
//    void oneToMany() {
//        // с помощью cleanup закрываем наши потоки
//        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
//        @Cleanup var session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var company = session.get(Company.class, 5);
//        Hibernate.initialize(company.getUsers());
//        System.out.println(company.getUsers());
//
//        session.getTransaction().commit();
//    }
//
//    @Test
//    void checkGetReflectionApi() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = preparedStatement.executeQuery();
//        resultSet.getString("username");
//        resultSet.getString("lastname");
//        resultSet.getString("lastname");
//
//        Class<User> clazz = User.class;
//        Constructor<User> constructor = clazz.getConstructor();
//        User user = constructor.newInstance();
//        Field usernameField = clazz.getDeclaredField("username");
//        usernameField.setAccessible(true);
//        usernameField.set(user, resultSet.getString("username"));
//    }

//    @Test
//    void checkReflectionApi() throws SQLException, IllegalAccessException {
//        User user = null;
//
//        String sql = """
//                insert
//                into
//                %s
//                (%s)
//                values
//                (%s)
//                """;
//        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
//                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
//                .orElse(user.getClass().getName());
//
//        Field[] declaredFields = user.getClass().getDeclaredFields();
//
//        String columnNames = Arrays.stream(declaredFields)
//                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
//                        .map(Column::name)
//                        .orElse(field.getName()))
//                .collect(Collectors.joining(", ")); // разделяем запятой имена наших полей
//
//        String columnValues = Arrays.stream(declaredFields)
//                .map(field -> "?")
//                .collect(Collectors.joining(", "));
//
//        System.out.println(sql.formatted(tableName, columnNames, columnValues));
//
//        Connection connection = null;
//        PreparedStatement preparedStatement = connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));
//        for (Field declaredField : declaredFields) {
//            declaredField.setAccessible(true);
//            preparedStatement.setObject(1, declaredField.get(user));
//        }
//
//    }
}