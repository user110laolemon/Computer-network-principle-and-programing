package HW7.hw3;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    /*private static int BYTE_LENGTH = 64;*/

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("阻塞等待客户端连接中...");
        clientSocket = serverSocket.accept();

        /*InputStream is = clientSocket.getInputStream();*/
        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
        for (; ; ) {
            int length = dis.readInt();

            if (length > 0) {
                byte[] messageBytes = new byte[length];
                dis.readFully(messageBytes, 0, length);
                String message = new String(messageBytes);
                System.out.println("服务器已收到消息：" + message);
            }
        }
        /*try {
            for(;;) {
                *//*byte[] bytes = new byte[BYTE_LENGTH];

                int cnt = is.read(bytes, 0, BYTE_LENGTH);
                if (cnt > 0) {
                    System.out.println("服务器已收到消息：" + new String(bytes).trim());
                }*//*
                int length = 0; // 读取消息长度
                try {
                    length = dis.readInt();
                } catch (IOException e) {
                    System.out.println("客户端已关闭连接");
                    break;
                }
                if (length > 0) {
                    byte[] messageBytes = new byte[length];
                    dis.readFully(messageBytes, 0, length); // 读取完整消息
                    String message = new String(messageBytes);
                    System.out.println("服务器已收到消息：" + message);
                }
            }
        } finally {
            stop();
        }*/
    }

    public void stop() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 9091;
        TCPServer server = new TCPServer();
        try {
            server.start(port);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
