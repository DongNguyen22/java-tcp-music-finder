package com.musicfinder.network;

import com.musicfinder.shared.request.Request;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientConnection {

    private static final ClientConnection INSTANCE = new ClientConnection();

    private Socket socket;
    private PrintWriter writer;

    private ClientConnection() {
    }

    public static ClientConnection getInstance() {
        return INSTANCE;
    }

    public void connect(String host, int port) throws IOException {

        if (isConnected()) {
            return;
        }

        socket = new Socket(host, port);

        writer = new PrintWriter(
                new OutputStreamWriter(
                        socket.getOutputStream(),
                        StandardCharsets.UTF_8),
                true);
    }

    public void send(Request request) throws IOException {

        if (!isConnected()) {
            throw new IOException("Client chưa kết nối tới server");
        }

        writer.println(request.toJson());

        if (writer.checkError()) {
            throw new IOException("Không thể gửi request");
        }
    }

    public boolean isConnected() {

        return socket != null
                && socket.isConnected()
                && !socket.isClosed();
    }

    public void close() throws IOException {

        if (writer != null) {
            writer.close();
        }

        if (socket != null) {
            socket.close();
        }
    }
}