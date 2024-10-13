package HW9.bntask;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class TCPFileReceiver {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String fileName = "checksum_recv1e8_tcp.txt";
        File file = new File(fileName);
        FileOutputStream output = new FileOutputStream(file);
        ServerSocket serverSocket = new ServerSocket(9091);
        System.out.println("阻塞等待客户端连接中...");
        Socket socket = serverSocket.accept();

        InputStream is = socket.getInputStream();

        try {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            System.out.println("文件接收完毕！");
            System.out.println("接收文件的md5为: " + MD5Util.getMD5(file));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            output.close();
            is.close();
            socket.close();
            serverSocket.close();
        }
    }
}