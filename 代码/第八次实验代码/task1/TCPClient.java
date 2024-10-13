package HW8.task1;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TCPClient {
    private Socket clientSocket;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        for (; ; ) {
            ServerHandler serverHandler = new ServerHandler(clientSocket);
            serverHandler.start();
        }
    }

    public void stopConnection() {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.stopConnection();
        }
    }
}


class ServerReadHandler extends Thread {
    private final BufferedReader bufferedReader;

    ServerReadHandler(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public void run() {
        try {
            while (true) {
                String str = bufferedReader.readLine();
                if (str == null) {
                    System.out.println("读到服务端数据为空");
                    break;
                } else {
                    System.out.println("读到服务端数据为：" + str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ServerWriteHandler extends Thread {
    private final PrintWriter printWriter;
    private final Scanner sc;

    ServerWriteHandler(OutputStream outputStream) {
        this.printWriter = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
        this.sc = new Scanner(System.in);
    }

    void send(String str) {
        this.printWriter.println(str);
    }

    public void run() {
        while (sc.hasNext()) {
            System.out.println("客户端请写入数据：");
            String str = sc.next();
            send(str);
        }
    }
}

class ServerHandler extends Thread {
    private Socket socket;
    private final ServerReadHandler serverReadHandler;
    private final ServerWriteHandler serverWriteHandler;

    ServerHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.serverReadHandler = new ServerReadHandler(socket.getInputStream());
        this.serverWriteHandler = new ServerWriteHandler(socket.getOutputStream());
    }

    public void run() {
        super.run();
        serverReadHandler.start();
        serverWriteHandler.start();
    }
}
