package HW7.hw2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPServer {
    private ServerSocket serverSocket;
    public void start(int port) throws IOException{
        serverSocket=new ServerSocket(port);
        for(;;){//可以使用while(true)，效果不变
            System.out.println("阻塞等待客户端连接...");
            Socket clientSocket=serverSocket.accept();

            ClientHandler clientHandler =new ClientHandler(clientSocket);
            clientHandler.start();
        }
    }

    public void stop(){
        try {
            if(serverSocket!=null){
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port =9091;
        TCPServer server=new TCPServer();
        try{
            server.start(port);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            server.stop();
        }
    }
}

class ClientHandler extends Thread{
    private Socket socket;
    ClientHandler(Socket s){
        this.socket=s;
    }
    public void run(){
        super.run();
        try{
            PrintWriter out=new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),true);
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));
            String str;
            while ((str=in.readLine())!=null){
                System.out.println("我是服务器，客户端说："+str);
                String upperStr=str.toUpperCase();
                out.println("服务端已收到信息："+upperStr);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            closeConnection();
        }
    }
    private void closeConnection(){
        try {
            if(socket!=null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
