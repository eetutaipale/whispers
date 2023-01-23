package fi.utu.tech.telephonegame.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

import fi.utu.tech.telephonegame.Message;

public class ClientHandler extends Thread {
    private Socket socket;
    private ObjectOutputStream outCome;
    private ObjectInputStream inCome;
    private NetworkService networkService;

    public ClientHandler(Socket socket, NetworkService networkService) {
        this.socket = socket;
        this.networkService = networkService;
        networkService.addToSocketList(this);
    }

    public void run() {
        try {
            InputStream iS = socket.getInputStream();
            OutputStream oS = socket.getOutputStream();
            outCome = new ObjectOutputStream(oS);
            inCome = new ObjectInputStream(iS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            // Refactor message from serializable
            try {
                Message packet = (Message) (((Serializable) inCome.readObject()));
                networkService.getInputQueue().add(packet);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public void send(Object put) {
        try {
            outCome.writeObject(put);
            outCome.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}