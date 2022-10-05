import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Main {

    // TCP Server
    ServerSocket serverSocket;
    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;

    public void startServer() {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(Inet4Address.getByName("127.0.0.1"), 3000));
            System.out.println("Listen......");
            clientSocket = serverSocket.accept();
            System.out.println("Accept");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message = in.readLine();
            System.out.println(message);

            byte[] decodedBytes = Base64.getDecoder().decode(message);
            Files.write(Paths.get("image.png"), decodedBytes);
            out.println("Get Image Success!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.startServer();
    }
}