package javafiles;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        int port = 8082;
        HASUP hasup = new HASUP();
        ExecutorService pool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Sunucu " + port + " portunda başlatıldı...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ServerHandler(clientSocket, hasup));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
