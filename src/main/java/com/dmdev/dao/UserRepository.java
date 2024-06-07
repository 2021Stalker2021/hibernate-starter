package com.dmdev.dao;

import com.dmdev.entity.User;
import jakarta.persistence.EntityManager;
import lombok.Getter;

@Getter
public class UserRepository extends RepositoryBase<Long, User> {

    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
        this.entityManager = entityManager;
    }
    // todo
}
