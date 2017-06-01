package com.oracle.cloud.acc;

import com.oracle.cloud.acc.domain.Employee;

import java.util.List;
import javax.persistence.EntityManager;

public class EmployeeRepository {

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


    public List<Employee> all() {
        EntityManager em = null;
        List<Employee> employees = null;
        try {
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
