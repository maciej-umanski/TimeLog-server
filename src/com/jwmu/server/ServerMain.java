package com.jwmu.server;

import com.jwmu.configuration.Configuration;

public class ServerMain {
    public static void main(String[] args) {

        Configuration configuration = new Configuration("local");
        ServerLogger logger = new ServerLogger();

        new ConnectionHandler(configuration.getDatabaseUrl(),
                configuration.getDatabaseUser(),
                configuration.getDatabasePassword(),
                configuration.getServerPort(),
                logger);

    }
}
