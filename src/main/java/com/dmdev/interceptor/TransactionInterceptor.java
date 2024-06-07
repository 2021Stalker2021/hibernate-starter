package com.dmdev.interceptor;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.Origin;
import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.SuperCall;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class TransactionInterceptor {

    private final SessionFactory sessionFactory;

    @RuntimeType // мы не знаем какой тип будет возвращен из intercept поэтому мы используем эту аннотацию
    public Object intercept(@SuperCall Callable<Object> call, @Origin Method method) throws Exception {
        // @SuperCall - означает что мы будем возвращать реальный метод нашего сервиса
        // @Origin - метод, который вызывается у нашего UserService
        Transaction transaction = null;
        boolean transactionStarted = false;
        if (method.isAnnotationPresent(Transactional.class)) {
            transaction = sessionFactory.getCurrentSession().getTransaction();// получаем транзакцию
            if (!transaction.isActive()) { // получаем транзакцию если она не активна
                transaction.begin();
                transactionStarted = true;
            }
        }

        Object result;
        try {
            result = call.call();
            if (transactionStarted) {
                transaction.commit();
            }

        } catch (Exception exception) {
            if (transactionStarted) {
                transaction.rollback();
            }
            throw exception;
        }

        return result;
    }
}
