package com.oracle.cloud.jsr;

import com.oracle.cloud.jsr.jpa.EmployeeRepository;
import com.oracle.cloud.jsr.jpa.JPAFacade;
import com.oracle.cloud.jsr.jpa.entities.Employee;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class InMemoryDBEmployeeRepositoryTest {

    public InMemoryDBEmployeeRepositoryTest() {
    }
    static Map<String, String> props = new HashMap<>();
    final static String PU_NAME = "derby-in-memory-PU";

    @BeforeClass
    public static void setUpClass() {

        props.put("javax.persistence.jdbc.url", "jdbc:derby:target/derbydb;create=true");
        JPAFacade.bootstrapEMF(PU_NAME, props);

    }

    @AfterClass
    public static void tearDownClass() {
        props.clear();
        props = null;

        JPAFacade.closeEMF();
    }

    EmployeeRepository cut;

    @Before
    public void setUp() {
        cut = new EmployeeRepository();
    }

    @After
    public void tearDown() {
        //nothing to do
    }

    @Test
    public void getSingleEmployeeTest() {
        String id = "007";
        String name = "Abhishek Gupta";
        
        Employee emp = cut.get(id);
        assertNotNull("Employee was null!", emp);
        assertEquals("Wrong employee id", emp.getEmpId(), Integer.valueOf(id));
        assertEquals("Wrong employee name", emp.getFullName(), name);
    }
    
    @Test
    public void getAllEmployeesTest() {
        List<Employee> emps = cut.all();
        assertNotNull("Employee list was null!", emps);
        assertEquals("2 employees were not found", emps.size(), 2);
    }

}
