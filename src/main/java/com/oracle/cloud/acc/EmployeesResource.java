package com.oracle.cloud.acc;

import com.oracle.cloud.acc.domain.Employee;
import java.util.List;

import javax.persistence.EntityManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

@Path("employees")
public class EmployeesResource {

    @GET
    @Path("{id}")
    public Response fetchById(@PathParam("id") String id) {

        System.out.println("Searching for employee with ID " + id);
        Response resp = null;
        EntityManager em = null;
        Employee emp = null;
        try {

            emp = new EmployeeRepository().get(id);
            if (emp == null) {
                System.out.println("Could not find employee with id " + id);
                resp = Response.status(Response.Status.NOT_FOUND).entity("Could not find employee with id " + id).build();
            } else {
                resp = Response.ok().entity(emp).build();
            }
        } catch (Exception e) {
            //throw e;
            resp = Response.serverError().entity(e.getMessage()).build();
        } finally {
            if (em != null) {
                em.close();
            }

        }

        return resp;
    }

    @GET
    public Response all() {

        System.out.println("Listing all employees......");
        Response resp = null;

        EntityManager em = null;
        List<Employee> employees = null;
        try {
            employees = new EmployeeRepository().all();
            if (employees == null | employees.isEmpty()) {
                System.out.println("Could not find employees");
                resp = Response.status(Response.Status.NOT_FOUND).entity("Could not find employees").build();
            } else {
                GenericEntity<List<Employee>> list = new GenericEntity<List<Employee>>(employees) {
                };
                resp = Response.ok(list).build();
            }
        } catch (Exception e) {
            resp = Response.serverError().entity(e.getMessage()).build();
        } finally {

            if (em != null) {
                em.close();
            }

        }

        return resp;
    }

}
