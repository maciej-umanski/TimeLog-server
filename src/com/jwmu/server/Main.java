package com.jwmu.server;

import com.jwmu.configuration.Configuration;

public class Main {
    public static void main(String[] args) {

        String activeProfile = "develop";

        Configuration configuration = new Configuration(activeProfile);
        ServerLogger logger = new ServerLogger();

        new ConnectionHandler(configuration.getDatabaseUrl(),
                configuration.getDatabaseUser(),
                configuration.getDatabasePassword(),
                configuration.getServerPort(),
                logger);

    }
}
