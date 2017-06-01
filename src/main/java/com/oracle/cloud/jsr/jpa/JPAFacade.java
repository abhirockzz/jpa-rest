package com.oracle.cloud.jsr.jpa;

import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Creates a 'global' (we're just handling a single persistence unit) instance
 * of Entity Manager Factory and provides access to the Entity Manager. This is
 * fine, since EMF is thread-safe (but EM is not)
 *
 * @author Abhishek
 */
public class JPAFacade {

    private static EntityManagerFactory emf;

    private JPAFacade() {
    }

    public static void bootstrapEMF(String persistenceUnitName, Map<String, String> props) {
        if (emf == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory(persistenceUnitName, props);
            emf.createEntityManager().close(); //a hack to initiate 'eager' deployment of persistence unit during deploy time as opposed to on-demand
        }
    }

    public static EntityManager getEM() {
        if (emf == null) {
            throw new IllegalStateException("Please call bootstrapEMF first");
        }

        return emf.createEntityManager();
    }

    public static void closeEMF() {

        if (emf == null) {
            throw new IllegalStateException("Please call bootstrapEMF first");
        }

        emf.close();
    }

}
