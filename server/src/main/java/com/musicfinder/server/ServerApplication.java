package com.musicfinder.server;

import com.musicfinder.server.handler.RequestDispatcher;
import com.musicfinder.server.network.TcpServer;

public class ServerApplication {
    private static final int PORT = 12345;
    private static final int CORE_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 50;

    public static void main(String[] args) {
        RequestDispatcher dispatcher = new RequestDispatcher();
        TcpServer server = new TcpServer(PORT, dispatcher, CORE_POOL_SIZE, MAX_POOL_SIZE);

        // Nhấn Ctrl+C thì dừng server gọn gàng
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));

        server.start();
    }
}