package com.oracle.cloud.jsr.jpa;

import com.oracle.cloud.jsr.jpa.entities.Employee;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Encapsulates database interactions
 *
 * @author Abhishek
 */
public class EmployeeRepository {

    /**
     * Fetches JSR info, given JSR ID
     *
     * @param id JSR ID
     * @return JSR info
     */
    public Employee get(String id) {
        EntityManager em = null;
        Employee emp = null;
        try {
            em = JPAFacade.getEM();
            emp = em.find(Employee.class, Integer.valueOf(id));
        } catch (Exception e) {
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return emp;
    }

    /**
     * Fetches ALL JSRs
     *
     * @return List of JSRs
     */
    public List<Employee> all() {
        EntityManager em = null;
        List<Employee> employees = null;
        try {
            //em = emf.createEntityManager();
            em = JPAFacade.getEM();
            employees = em.createQuery("SELECT c FROM Employee c").getResultList();
        } catch (Exception e) {
            throw e;
        } finally {

            if (em != null) {
                em.close();
            }

        }

        return employees;
    }


}
