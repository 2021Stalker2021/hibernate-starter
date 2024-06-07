package com.dmdev.dao;

import com.dmdev.entity.Payment;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;

public class PaymentRepository extends RepositoryBase<Long, Payment> {

    public PaymentRepository(EntityManager entityManager) {
        super(Payment.class, entityManager);
    }
}
