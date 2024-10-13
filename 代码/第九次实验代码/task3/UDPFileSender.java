package HW9.task3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class UDPFileSender {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {// 生成并写入发送文件
        try (FileWriter fileWriter = new FileWriter("checksum1e8.txt")) {
            Random r = new Random(2023);// 尝试 1e3 and 1e8
            for (int i = 0; i < 1e8; i++) {
                fileWriter.write(r.nextInt());
            }
        }
        File file = new File("checksum1e8.txt");
        System.out.println("发送文件生成完毕");
        System.out.println("发送文件的md5为: " + MD5Util.getMD5(file));
        FileInputStream fis = new FileInputStream(file);
        DatagramSocket socket = new DatagramSocket();
        byte[] bytes = new byte[1024];
        /*byte[] bytes = new byte[2000];*/
        DatagramPacket packet;// 不断从文件读取字节并将其组装成数据报发送

        int len;
        for (; ; ) {
            len = fis.read(bytes);
            if (len == -1) break;
            packet = new DatagramPacket(bytes, len, InetAddress.getLocalHost(),
                    9091);
            socket.send(packet);
        }// 空数组作为发送终止符

        byte[] a = new byte[0];
        packet = new DatagramPacket(a, a.length, InetAddress.getLocalHost(), 9091);
        socket.send(packet);
        fis.close();
        socket.close();
        System.out.println("向" + packet.getAddress().toString() + "发送文件完毕！端口号为:" + packet.getPort());
    }
}