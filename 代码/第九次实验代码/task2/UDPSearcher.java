package HW9.task2;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPSearcher {
    public static void main(String[] args) throws IOException {
        String sendData = MessageUtil.buildWithPort(30000);
        byte[] sendBuffer = sendData.getBytes(StandardCharsets.UTF_8);
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, 0, sendBuffer.length,
                InetAddress.getByName("255.255.255.255"), 9091);

        DatagramSocket datagramSocket = new DatagramSocket(30000);
        //datagramSocket.setBroadcast(true);
        datagramSocket.send(sendPacket);
        System.out.println("数据已发送...");

        byte[] receiveBuffer = new byte[1024];
        DatagramPacket receiveResponsePacket = new DatagramPacket(receiveBuffer, 0, receiveBuffer.length);
        System.out.println("阻塞等待接收者回写的消息...");
        datagramSocket.receive(receiveResponsePacket);

        String receiveData = new String(receiveResponsePacket.getData(), 0, receiveResponsePacket.getLength());
        System.out.println("成功收到接收者回写的消息，开始解析：");
        String tag = MessageUtil.parseTag(receiveData);
        if (tag != null) {
            System.out.println("接收到服务端回写消息：" + "ip是：" + receiveResponsePacket.getAddress().getHostAddress()
                    + "、端口号是：" + receiveResponsePacket.getPort() + "、tag是：" + tag);
        } else {
            System.out.println("未能解析到有效的回写消息");
        }
        datagramSocket.close();
    }
}