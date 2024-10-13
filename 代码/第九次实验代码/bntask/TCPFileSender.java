package HW9.bntask;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class TCPFileSender {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String fileName = "checksum1e8_tcp.txt";
        FileInputStream fis = new FileInputStream(fileName);
        FileWriter fileWriter = new FileWriter(fileName);
        Socket socket = new Socket("127.0.0.1", 9091);
        BufferedInputStream bis = new BufferedInputStream(fis);
        OutputStream os = socket.getOutputStream();
        try {
            Random random = new Random(2023);
            for (int i = 0; i < 1e8; i++) {
                fileWriter.write(random.nextInt());
            }
            System.out.println("发送文件生成完毕");
            System.out.println("发送文件的md5为: " + MD5Util.getMD5(new File(fileName)));

            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
            System.out.println("文件发送完毕！");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            socket.close();
        }
    }
}