package com.jwmu.server;

import com.jwmu.configuration.Configuration;

public class Main {
    public static void main(String[] args) {

        ServerLogger logger = new ServerLogger();

        String activeProfile = "local";

        if(args.length >= 1){
            activeProfile = args[0];
        }

        logger.activeProfile(activeProfile);

        Configuration configuration = new Configuration(activeProfile);

        new ConnectionHandler(configuration.getDatabaseUrl(),
                configuration.getDatabaseUser(),
                configuration.getDatabasePassword(),
                configuration.getServerPort(),
                logger);
    }
}
