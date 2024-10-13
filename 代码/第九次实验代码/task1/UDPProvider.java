package HW9.task1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class UDPProvider {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(9091);
        byte[] b = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(b, 0, b.length);

        System.out.println("阻塞等待发送者的消息...");
        datagramSocket.receive(receivePacket);

        String ip = receivePacket.getAddress().getHostAddress();
        int port = receivePacket.getPort();
        int length = receivePacket.getLength();
        String data=new String(receivePacket.getData(),0,length);
        System.out.println("我是接收者，"+ip+":"+port+"的发送者说："+data);

        String respon="已收到您的消息："+data;
        byte[] responData=respon.getBytes(StandardCharsets.UTF_8);
        DatagramPacket sendResponPacket=new DatagramPacket(responData,responData.length,receivePacket.getAddress(),receivePacket.getPort());
        datagramSocket.send(sendResponPacket);

        datagramSocket.close();
    }
}
