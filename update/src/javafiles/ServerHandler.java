package javafiles;
import java.io.*;
import java.net.Socket;

public class ServerHandler implements Runnable {
    private final Socket clientSocket;
    private final HASUP hasup;

    public ServerHandler(Socket clientSocket, HASUP hasup) {
        this.clientSocket = clientSocket;
        this.hasup = hasup;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String message;
            while ((message = in.readLine()) != null) {
                String response = hasup.processMessage(message);
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}