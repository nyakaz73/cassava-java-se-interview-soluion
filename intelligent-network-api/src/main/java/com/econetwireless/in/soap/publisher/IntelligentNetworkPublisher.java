package com.econetwireless.in.soap.publisher;

import com.econetwireless.in.soap.service.IntelligentNetworkServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Endpoint;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import org.apache.commons.dbcp2.*;

/**
 * Created by tnyamakura on 17/3/2017.
 */
public class IntelligentNetworkPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntelligentNetworkPublisher.class);
    private BasicDataSource connectionPool;
    private IntelligentNetworkPublisher(BasicDataSource connectionPool) {
        super();
        this.connectionPool = connectionPool;
    }

    public  void  main(String ... publishingParameters) throws URISyntaxException, SQLException {
        final String endpointUrl = "http://localhost:8888/intelligent-network-api/IntelligentNetworkService";

        LOGGER.info("Configuring DB Connection Pooling...");
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
        connectionPool = new BasicDataSource();

        if (dbUri.getUserInfo() != null) {
            connectionPool.setUsername(dbUri.getUserInfo().split(":")[0]);
            connectionPool.setPassword(dbUri.getUserInfo().split(":")[1]);
        }
        connectionPool.setDriverClassName("org.postgresql.Driver");
        connectionPool.setUrl(dbUrl);
        //Setting the size of the Pool
        connectionPool.setInitialSize(2);

        LOGGER.info("Finished Configuring DB Connection Pooling...");

        LOGGER.info("Starting Intelligent network service....");


        Endpoint.publish(endpointUrl, new IntelligentNetworkServiceImpl());
        LOGGER.info("Finished publishing WS");
    }
}
