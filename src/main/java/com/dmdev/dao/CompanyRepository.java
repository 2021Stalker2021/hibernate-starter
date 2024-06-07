package com.dmdev.dao;

import com.dmdev.entity.Company;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;

public class CompanyRepository extends RepositoryBase<Integer, Company> {

    public CompanyRepository(EntityManager entityManager) {
        super(Company.class, entityManager);
    }
}
