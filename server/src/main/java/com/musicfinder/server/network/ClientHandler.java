package com.musicfinder.server.network;

import com.musicfinder.server.handler.RequestDispatcher;
import com.musicfinder.shared.response.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {
    private static final int READ_TIMEOUT_MS = 5 * 60 * 1000; // 5 phút không gửi gì thì ngắt

    private final Socket socket;
    private final RequestDispatcher dispatcher;

    public ClientHandler(Socket socket, RequestDispatcher dispatcher) {
        this.socket = socket;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        String client = String.valueOf(socket.getRemoteSocketAddress());
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter writer = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

            socket.setSoTimeout(READ_TIMEOUT_MS);

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                if ("bye".equalsIgnoreCase(line.trim())) {
                    System.out.println("Client " + client + " yêu cầu đóng kết nối.");
                    break;
                }
                Response response = dispatcher.handle(line);
                writer.println(response.toJson());
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Client " + client + " quá thời gian chờ, đóng kết nối.");
        } catch (IOException e) {
            System.out.println("Client " + client + " mất kết nối: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
            System.out.println("Đã đóng kết nối với " + client);
        }
    }
}