package com.jwmu.server;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseSender {
    private final OutputStream outputStream;

    public ResponseSender(OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
    }

    public void sendCode(String codeToSend) throws IOException {
        outputStream.write(codeToSend.getBytes());
    }
}
