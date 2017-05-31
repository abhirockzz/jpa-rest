package com.oracle.cloud.jsr.jpa;

import com.oracle.cloud.jsr.jpa.entities.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 * Provides REST interface for clients to work with JSRs. Leverages
 * JPARepository class
 *
 */
@Path("employees")
public class EmployeesResource {

    @GET
    @Path("{id}")
    public Response fetchById(@PathParam("id") String id) {
        
        System.out.println("Searching for employee with ID "+ id);

        EntityManager em = null;
        Employee emp = null;
        try {
            
            emp = new EmployeeRepository().get(id);
        } catch (Exception e) {
            throw e;
        } finally {

            if (em != null) {
                em.close();
            }

        }
        
        return Response.ok(emp).build();
    }
    
    @GET
    public Response all() {
        
        System.out.println("Listing all employees......");

        EntityManager em = null;
        List<Employee> employees = null;
        try {
            employees = new EmployeeRepository().all();
        } catch (Exception e) {
            throw e;
        } finally {

            if (em != null) {
                em.close();
            }

        }
        GenericEntity<List<Employee>> list = new GenericEntity<List<Employee>>(employees) {
        };
        return Response.ok(list).build();
    }

}
