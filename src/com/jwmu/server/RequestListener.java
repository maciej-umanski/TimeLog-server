package com.jwmu.server;

import java.io.*;
import java.sql.SQLException;

public class RequestListener extends Thread{
    private final BufferedReader inputStreamReader;
    RequestHandler requestHandler;

    public RequestListener(RequestHandler requestHandler, InputStream inputStream) throws IOException {
        this.inputStreamReader = new BufferedReader(new InputStreamReader(inputStream));
        this.requestHandler = requestHandler;
        this.start();
    }

    @Override
    public void run() {
        try {
            listen();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void listen() throws IOException, SQLException {
        String userInput;
        while((userInput = inputStreamReader.readLine()) != null){
            String[] tokens = userInput.split(" ");
            if("login".equalsIgnoreCase(tokens[0])){
                requestHandler.LoginUser(tokens);
            }else if("logoff".equalsIgnoreCase(tokens[0])){
                requestHandler.logoffUser();
            }else if("connection".equalsIgnoreCase(tokens[0])){
                requestHandler.connectionNotification();
            }else if("task".equalsIgnoreCase(tokens[0])){
                requestHandler.sendExampleTask();
            }else{
                requestHandler.wrongCommand();
            }
        }
    }
}
