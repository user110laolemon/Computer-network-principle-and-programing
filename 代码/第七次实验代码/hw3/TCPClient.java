package HW7.hw3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {
    private Socket clientSocket;
    /*private OutputStream out;*/
    private DataOutputStream out;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        /*out=clientSocket.getOutputStream();*/
        out = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void sendMessage(String msg) throws IOException {
        for (int i = 0; i < 10; i++) {
            /*out.write(msg.getBytes());*/
            byte[] messageBytes = msg.getBytes();
            out.writeInt(messageBytes.length);
            out.write(messageBytes);
        }
    }

    public void stopConnection() {
        try {
            if (out != null) {
                out.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 9091;
        TCPClient client = new TCPClient();
        try {
            client.startConnection("127.0.0.1", port);
            String message = "NETWORK PRINCIPLE";
            client.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.stopConnection();
        }
    }
}
