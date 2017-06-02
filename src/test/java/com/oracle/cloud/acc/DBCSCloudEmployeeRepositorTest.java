package com.oracle.cloud.acc;

import com.oracle.cloud.acc.domain.Employee;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DBCSCloudEmployeeRepositorTest {

    public DBCSCloudEmployeeRepositorTest() {
    }
    static Map<String, String> props = new HashMap<>();
    final static String PU_NAME = "dbcs-pu";

    @BeforeClass
    public static void setUpClass() {

        props.put("javax.persistence.jdbc.url", System.getenv().get("DBCS_JDBC_URL"));
        props.put("javax.persistence.jdbc.user", System.getenv().get("DBCS_USER"));
        props.put("javax.persistence.jdbc.password", System.getenv().get("DBCS_PASSWORD"));

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
