package com.jwmu.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ResponseSender {
    private final OutputStream outputStream;
    private final ObjectOutputStream objectOutputStream;

    public ResponseSender(OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        objectOutputStream = new ObjectOutputStream(outputStream);
    }

    public void sendCode(String codeToSend) throws IOException {
        outputStream.write(codeToSend.getBytes());
    }

    public void sendObject(Object object) throws IOException {
        objectOutputStream.writeObject(object);
    }
}
