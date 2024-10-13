package HW9.task3;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPFileReceiver {
    public static void main(String[] args) throws IOException {
        File file = new File("checksum_recv1e8.txt"); //要接收的文件存放路径
        FileOutputStream output = new FileOutputStream(file);
        byte[] data = new byte[1024];
        DatagramSocket ds = new DatagramSocket(9091);
        DatagramPacket dp = new DatagramPacket(data, data.length);

        while (true) {
            ds.receive(dp);
            int len = dp.getLength();
            if (len == 0) {
                break;
            }
            output.write(data, 0, len);
        }

        output.close();
        ds.close();
        System.out.println("接收来自" + dp.getAddress().toString() + "的文件已完成！对方所使用的端口号为：" + dp.getPort());
        file = new File("checksum_recv1e8.txt");
        System.out.println("接收文件的md5为: " + MD5Util.getMD5(file));
    }
}
