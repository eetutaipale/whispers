package fi.utu.tech.telephonegame.network;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;

public class Server extends Thread {

    private ServerSocket serverSocket = null;
    private NetworkService networkService;
    private Socket socket = null;
    private int port;

    public Server(int port, NetworkService networkService) throws IOException {
        this.networkService = networkService;
        this.port = port;
        this.serverSocket = new ServerSocket(port);

    }

    public void run() {
        while (true) {
            try {
                socket = serverSocket.accept();
                ClientHandler s = new ClientHandler(socket, networkService);
                s.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}