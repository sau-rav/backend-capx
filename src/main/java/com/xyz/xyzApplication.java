package com.xyz;

import com.codahale.metrics.health.HealthCheck;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.xyz.resources.DashboardResource;
import com.xyz.resources.HomeResource;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.EnumSet;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class xyzApplication extends Application<xyzConfiguration> {

    public static void main(final String[] args) throws Exception {
        new xyzApplication().run(args);
    }

    @Override
    public String getName() {
        return "xyz";
    }

    @Override
    public void initialize(final Bootstrap<xyzConfiguration> bootstrap) {
        // TODO: application initialization
        // Define GOOGLE_APPLICATION_CREDENTIALS in env pionting to the file : service-account-details.json
        try {
            FileInputStream serviceAccount = new FileInputStream("./service-account-details.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                                        .setDatabaseUrl("https://xyz-beta-a84c2-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                        .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(final xyzConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new DashboardResource());
        environment.jersey().register(new HomeResource());
        environment.healthChecks().register(getName(), new HealthCheck() {
            @Override
            protected Result check() throws Exception {
                return Result.healthy();
            }
        });

        // CORS support
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin,Authorization,ReferralCode");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping for the CORS filter
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "*"); 
    }

}
