package com.jwmu.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler extends Thread{

    private final int serverPort;
    private ServerSocket serverSocket;
    protected DatabaseHandler databaseHandler;
    private boolean isServerActive = false;
    private boolean isDatabaseConnected = false;

    public ConnectionHandler(int serverPort) {
        this.serverPort = serverPort;
        this.start();
    }

    @Override
    public void run() {
        try {
            initializeServer();
            initializeDatabase();
            while(isServerActive && isDatabaseConnected){
                handleNewConnections();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeServer() throws IOException {
        serverSocket = new ServerSocket(serverPort);
        isServerActive = true;
    }

    private void initializeDatabase(){
        databaseHandler = new DatabaseHandler();
        isDatabaseConnected = true;
    }

    private void handleNewConnections() throws IOException {
        new ClientHandler(serverSocket.accept(), databaseHandler);
    }

}
