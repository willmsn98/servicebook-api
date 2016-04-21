package com.christopherlabs.service;

import com.christopherlabs.service.model.*;
import com.yahoo.elide.contrib.dropwizard.elide.ElideBundle;
import com.yahoo.elide.resources.JsonApiEndpoint;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ServicebookApiApplication extends Application<ServicebookApiConfiguration> {

    private final ElideBundle<ServicebookApiConfiguration> elideBundle;

    public ServicebookApiApplication() {
        elideBundle = new ElideBundle<ServicebookApiConfiguration>(Event.class, User.class, Photo.class, Comment.class, Organization.class) {
            public PooledDataSourceFactory getDataSourceFactory(ServicebookApiConfiguration servicebookApiConfiguration) {
                return servicebookApiConfiguration.getDataSourceFactory();
            }
        };
    }

    @Override
    public void initialize(Bootstrap<ServicebookApiConfiguration> bootstrap) {
        bootstrap.addBundle(elideBundle);
    }

    public void run(ServicebookApiConfiguration servicebookApiConfiguration, Environment environment) throws Exception {
        environment.jersey().register(JsonApiEndpoint.class);
    }

    public static void main(String[] args) throws Exception {
        new ServicebookApiApplication().run(args);
    }
 }
