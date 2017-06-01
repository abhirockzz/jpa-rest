package com.oracle.cloud.acc;

import com.oracle.cloud.acc.domain.Employee;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ACCSAppIT {

    public ACCSAppIT() {
    }

    static String accsAppBaseURL;
    static String root = "employees";

    @BeforeClass
    public static void setUpClass() {
        accsAppBaseURL = System.getenv().getOrDefault("ACCS_APP_BASE_URL", "https://acc-dbcs-jpa-rest-paasdemo015.apaas.us6.oraclecloud.com");
        System.out.println("Base REST URL "+ accsAppBaseURL);
    }

    @AfterClass
    public static void tearDownClass() {
        accsAppBaseURL = null;
    }

    Client client;
    WebTarget target;

    @Before
    public void setUp() {
        client = ClientBuilder.newClient();
        target = client
                .register(MoxyJsonFeature.class)
                .target(accsAppBaseURL);
        
        System.out.println("JAX-RS client setup");
    }

    @After
    public void tearDown() {
        client.close();
        System.out.println("JAX-RS client closed");
    }

    @Test
    public void testGETSingleEmployee() {

        String empid = "42";

        Response response = target.path(root)
                .path(empid)
                .request().get();

        System.out.println("Response status " + response.getStatus());
        assertEquals(String.format("Could not employee with id %s", empid), 200, response.getStatus());

        Employee emp = response.readEntity(Employee.class);
        System.out.println("Employee " + emp);

        String expectedEmpId = "42";
        String expectedEmpName = "John Doe";
        String expectedEmpEmail = "jdoes@test.com";

        assertEquals("Employee name must be " + expectedEmpName, expectedEmpName, emp.getFullName());
        assertEquals("Employee id must be " + expectedEmpId, Integer.valueOf(expectedEmpId), emp.getEmpId());
        assertEquals("Employee email must be " + expectedEmpEmail, expectedEmpEmail, emp.getEmail());

    }
    
    @Test
    public void testGETAllEmployees() {


        Response response = target.path(root)
                .request().get();

        System.out.println("Response status " + response.getStatus());
        assertEquals("Could not get employees info", 200, response.getStatus());

    }
}
