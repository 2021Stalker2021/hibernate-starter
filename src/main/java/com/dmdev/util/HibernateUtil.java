package com.dmdev.util;

import com.dmdev.converter.BirthdayConverter;
import com.dmdev.entity.Audit;
import com.dmdev.entity.Revision;
import com.dmdev.entity.User;
import com.dmdev.interceptor.GlobalInterceptor;
import com.dmdev.listener.AuditTableListener;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

@UtilityClass // создаст private конструктор и final класс
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        configuration.configure();

        var sessionFactory = configuration.buildSessionFactory();
//        registerListeners(sessionFactory);
        return sessionFactory;
    }

    private static void registerListeners(SessionFactory sessionFactory) {
        var sessionFactoryImpl = sessionFactory.unwrap(SessionFactoryImpl.class);
        var listenerRegistry = sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);
        var auditTableListener = new AuditTableListener();
        listenerRegistry.appendListeners(EventType.PRE_INSERT, auditTableListener);
        listenerRegistry.appendListeners(EventType.PRE_DELETE, auditTableListener);
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAttributeConverter(new BirthdayConverter(), true); // конвертация birthDate
        configuration.addAnnotatedClass(Audit.class);
        configuration.addAnnotatedClass(Revision.class);
        configuration.setInterceptor(new GlobalInterceptor());
        return configuration;
    }
}
