package HW9.task2;

import java.io.IOException;
import java.net.*;
import java.util.UUID;

public class UDPProvider {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(9091);
        byte[] buffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(buffer, 0, buffer.length);

        System.out.println("阻塞等待发送者的消息...");
        datagramSocket.receive(receivePacket);

        String ip = receivePacket.getAddress().getHostAddress();
        int port = receivePacket.getPort();
        int length = receivePacket.getLength();
        String data = new String(receivePacket.getData(), 0, length);
        System.out.println("我是接收者，" + ip + ":" + port + "的发送者说：" + data);

        int targetPort = MessageUtil.parsePort(data);
        String tag = UUID.randomUUID().toString();
        String sendMsg = MessageUtil.buildWithTag(tag);
        DatagramPacket sendData = new DatagramPacket(sendMsg.getBytes(), sendMsg.getBytes().length,
                InetAddress.getByName(ip), targetPort);
        datagramSocket.send(sendData);
        System.out.println("回应消息已发送到端口:" + targetPort);
        datagramSocket.close();
    }
}

