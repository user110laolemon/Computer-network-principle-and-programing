package HW9.task1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPSearcher {
    public static void main(String[] args) throws IOException {
        String sendData="用户名:admin;密码:123";
        byte[] sendB=sendData.getBytes(StandardCharsets.UTF_8);

        DatagramSocket datagramSocket=new DatagramSocket(9092);
        DatagramPacket sendPacket=new DatagramPacket(sendB,0,sendB.length, InetAddress.getLocalHost(),9091);

        datagramSocket.send(sendPacket);
        System.out.println("数据发送完毕...");

        byte[]receiveResB=new byte[1024];
        DatagramPacket receiveResponPacket=new DatagramPacket(receiveResB,receiveResB.length);
        datagramSocket.receive(receiveResponPacket);
        String receiveResData=new String(receiveResponPacket.getData(),0,receiveResponPacket.getLength());
        System.out.println("接收到服务端回写消息："+receiveResData);

        datagramSocket.close();
    }
}
