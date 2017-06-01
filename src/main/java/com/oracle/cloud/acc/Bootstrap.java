package com.oracle.cloud.acc;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class Bootstrap {

    static void bootstrapREST() throws IOException {

        String hostname = "0.0.0.0";
        String port = Optional.ofNullable(System.getenv("PORT")).orElse("8080");

        URI baseUri = UriBuilder.fromUri("http://" + hostname + "/").port(Integer.parseInt(port)).build();

        ResourceConfig config = new ResourceConfig(EmployeesResource.class)
                                                    .register(MoxyJsonFeature.class);

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        Logger.getLogger(Bootstrap.class.getName()).log(Level.INFO, "Application accessible at {0}", baseUri.toString());

        //gracefully exit Grizzly and Eclipselink services when app is shut down
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                Logger.getLogger(Bootstrap.class.getName()).info("Exiting......");
                server.shutdownNow();
                JPAFacade.closeEMF();
                Logger.getLogger(Bootstrap.class.getName()).info("REST and Persistence services stopped");
            }
        }));
        server.start();

    }

    private static final String PERSISTENCE_UNIT_NAME = "dbcs-pu";

    static void bootstrapJPA(String puName, Map<String, String> props) {

        JPAFacade.bootstrapEMF(puName, props);
        Logger.getLogger(Bootstrap.class.getName()).info("EMF bootstrapped");

    }

    public static void main(String[] args) throws IOException {
        Map<String, String> props = new HashMap<>();
        props.put("javax.persistence.jdbc.url", "jdbc:oracle:thin:@" + System.getenv("DBAAS_DEFAULT_CONNECT_DESCRIPTOR"));
        props.put("javax.persistence.jdbc.user", System.getenv("DBAAS_USER_NAME"));
        props.put("javax.persistence.jdbc.password", System.getenv("DBAAS_USER_PASSWORD"));
        bootstrapREST();
        bootstrapJPA(PERSISTENCE_UNIT_NAME, props);

    }
}
