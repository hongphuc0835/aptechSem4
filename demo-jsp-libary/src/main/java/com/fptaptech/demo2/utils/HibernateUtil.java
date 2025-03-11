package com.fptaptech.demo2.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("default");

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void close() {
        entityManagerFactory.close();
    }
}
