package HW7.hw1;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TCPClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);

        out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String respon = in.readLine();
        return respon;
    }

    public void stopConnection() {
        try {
            if (in != null) {
                in.close();
            }
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

            /*String response = client.sendMessage("⽤户名: ECNUDaSE;");
            System.out.println(response);*/

            Scanner sc=new Scanner(System.in);
            System.out.println("输入英文字符串：（如须退出请输入exit0）");
            while (true){
                String inputStr= sc.nextLine();
                if(inputStr.equals("exit0")){
                    break;
                }
                String outputStr= client.sendMessage(inputStr);
                System.out.println("服务器返回："+outputStr);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.stopConnection();
        }
    }
}
