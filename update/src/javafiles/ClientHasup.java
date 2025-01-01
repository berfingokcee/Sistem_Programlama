package javafiles;  // Paket adı doğru

import java.io.*;
import java.net.Socket;

public class ClientHasup {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public ClientHasup(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendCommand(String command) throws IOException {
        out.println(command);
    }

    public String receiveResponse() throws IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
