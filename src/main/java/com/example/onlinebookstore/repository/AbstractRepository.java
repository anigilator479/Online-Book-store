package com.example.onlinebookstore.repository;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractRepository {
    protected EntityManagerFactory manager;

    @Autowired
    protected AbstractRepository(EntityManagerFactory entityManagerFactory) {
        this.manager = entityManagerFactory;
    }
}
