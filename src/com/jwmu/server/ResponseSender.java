package com.jwmu.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ResponseSender {

    private final ObjectOutputStream objectOutputStream;

    public ResponseSender(OutputStream outputStream) throws IOException {
        this.objectOutputStream = new ObjectOutputStream(outputStream);
    }

    public void send(Object objectToSend) throws IOException {
        objectOutputStream.writeObject(objectToSend);
    }
}
