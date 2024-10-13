package HW8.task2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TCPServer {
    private ServerSocket serverSocket;
    private static List<Socket> clientSockets;

    public TCPServer() {
        clientSockets = new ArrayList<>();
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        for (; ; ) {
            System.out.println("阻塞等待客户端连接...");
            Socket clientSocket = serverSocket.accept();

            clientSockets.add(clientSocket);
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            clientHandler.start();

            System.out.println("客户端已连接：" + clientSocket.getRemoteSocketAddress());
            sendAddressToAllClients();
        }
    }

    public void sendAddressToAllClients() throws IOException {
        String msg = "现有所有连接客户端：";

        Iterator<Socket> iterator = clientSockets.iterator();
        while (iterator.hasNext()) {
            Socket socket = iterator.next();
            if (socket.isClosed()) {
                iterator.remove();
            } else {
                msg += socket.getRemoteSocketAddress() + "; ";
            }
        }

        if (clientSockets.isEmpty()) {
            return;
        }
        msg = msg.substring(0, msg.length() - 2);
        for (Socket socket : clientSockets) {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println(msg);
        }
    }

    public static void removeClient(Socket socket){
        clientSockets.remove(socket);
    }


    public void stop() {
        try {
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

class ClientReadHandler extends Thread {
    private final BufferedReader bufferedReader;
    private Socket socket;

    ClientReadHandler(Socket socket)throws IOException {
        this.socket=socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
    }

    public void run() {
        try {
            while (true) {
                String str = bufferedReader.readLine();
                if (str == null) {
                    System.out.println("读到客户端数据为空");
                    handlerClientDisconnect();
                    break;
                } else {
                    System.out.println("读到客户端数据为：" + str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlerClientDisconnect(){
        try {
            socket.close();
            TCPServer.removeClient(socket);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

class ClientWriteHandler extends Thread {
    private final PrintWriter printWriter;
    private final Scanner sc;

    ClientWriteHandler(OutputStream outputStream) {
        this.printWriter = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
        this.sc = new Scanner(System.in);
    }

    void send(String str) {
        this.printWriter.println(str);
    }

    public void run() {
        while (sc.hasNext()) {
            System.out.println("服务端请写入数据：");
            String str = sc.next();
            send(str);
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private final ClientReadHandler clientReadHandler;
    private final ClientWriteHandler clientWriteHandler;

    ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.clientReadHandler = new ClientReadHandler(socket);
        this.clientWriteHandler = new ClientWriteHandler(socket.getOutputStream());
    }

    public void run() {
        clientReadHandler.start();
        clientWriteHandler.start();
    }
}