package com.dmdev.util;

import com.dmdev.converter.BirthdayConverter;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass // создаст private конструктор и final класс
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAttributeConverter(new BirthdayConverter(), true); // конвертация birthDate
        configuration.configure();
        return configuration.buildSessionFactory();
    }
}
