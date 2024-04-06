package com.dmdev;

import com.dmdev.converter.BirthdayConverter;
import com.dmdev.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Information {
    public static void main(String[] args) {
//        BlockingQueue<Connection> pool = null;
////        Connection connection = pool.take();
////        SessionFactory - (Pool) объект у которго мы будем получать наши объекты типа session
//
////        Connection connection = DriverManager
////                .getConnection("db.url", "db.username", "db.pass");
////        Session
//        Configuration configuration = new Configuration();
////        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
////        configuration.addAnnotatedClass(User.class);
//        configuration.addAttributeConverter(new BirthdayConverter(), true);
//        configuration.configure();
//        /*
//         configure() - можно не указывать путь к hibernate.cfg.xml т.к. по
//         умолчанию он указывает на рутовую директорию resources:
//         public static final String DEFAULT_CFG_RESOURCE_NAME = "hibernate.cfg.xml";
//        */
//        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
//             Session session = sessionFactory.openSession()) {
//            session.beginTransaction(); // открывем нашу транзакцию
///*
//            session - это как обёртка вокруг нашего connection которая
//            предоставляет нам гораздо больший функционал для hibernate
//            merge - это save or update
//            persist - это save
//            remove - удалить
//*/
////            User user = User.builder()
////                    .username("ivan10@gmail.com")
////                    .firstname("Ivan")
////                    .lastname("Ivanov")
////                    .info("""
////                            {
////                                "name": "Ivan",
////                                "id": 25
////                            }
////                            """)
////                    .birthDate(new Birthday(LocalDate.of(2000, 1, 19)))
////                    .role(Role.ADMIN)
////                    .build();
////            session.remove(user);
//            User user1 = session.get(User.class, "ivan1@gmail.com");
//            user1.setLastname("Petrov2");
//            session.flush(); // сливаем состояние first level cash в БД
//
//
//            System.out.println(session.isDirty());
////            session.evict(user1); // удаление нашей сущности из кэша
////            session.clear(); // чистим весь наш кэш
////            session.close(); // закрытиве нашей session(выход из try with resources блока)
//
//            session.getTransaction().commit(); // закрываем транзакцию
    }
}
