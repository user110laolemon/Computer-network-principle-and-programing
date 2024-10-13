package HW9.bntask;

import java.io.*;
import java.net.*;

public class UDPFileReceiver {
    private static final int PACKET_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        File file = new File("checksum_recv1e8_udp.txt"); // 要接收的文件存放路径
        FileOutputStream output = new FileOutputStream(file);
        DatagramSocket socket = new DatagramSocket(9091);

        long expectedSequenceNumber = 0;
        while (true) {
            byte[] buffer = new byte[PACKET_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            int dataSize=packet.getLength();
            byte[] data = new byte[dataSize];
            System.arraycopy(packet.getData(), 0, data, 0, dataSize);

            if (getSequenceNumber(packet.getData()) == expectedSequenceNumber) {
                output.write(data);
                expectedSequenceNumber++;
                //System.out.println("接收数据包，序号：" + (expectedSequenceNumber - 1));
                // 发送 ACK
                byte[] ackData = {1}; // ACK 数据
                DatagramPacket ackPacket = new DatagramPacket(ackData, ackData.length, packet.getAddress(), packet.getPort());
                socket.send(ackPacket);
                //System.out.println("发送 ACK，序号：" + (expectedSequenceNumber - 1));
                if (packet.getLength() < PACKET_SIZE) {
                    // 收到空数据包，文件接收完成
                    break;
                }
            } else {
                // 收到重复的数据包，不写入文件
                //System.out.println("收到重复数据包，序号：" + getSequenceNumber(packet.getData()));
                // 发送 ACK
                byte[] ackData = {1}; // ACK 数据
                DatagramPacket ackPacket = new DatagramPacket(ackData, ackData.length, packet.getAddress(), packet.getPort());
                socket.send(ackPacket);
               // System.out.println("发送 ACK，序号：" + getSequenceNumber(packet.getData()));
            }
        }

        output.close();
        socket.close();
        System.out.println("文件接收完毕！");
        System.out.println("接收文件的md5为: " + MD5Util.getMD5(file));
    }

    private static byte[] removeSequenceNumber(byte[] data) {
        byte[] newData = new byte[data.length - Long.BYTES];
        System.arraycopy(data, Long.BYTES, newData, 0, newData.length);
        return newData;
    }

    private static long getSequenceNumber(byte[] data) {
        byte[] sequenceNumberBytes = new byte[Long.BYTES];
        System.arraycopy(data, 0, sequenceNumberBytes, 0, Long.BYTES);
        return bytesToLong(sequenceNumberBytes);
    }

    private static long bytesToLong(byte[] bytes) {
        long value = 0;
        for (int i = 0; i < bytes.length; i++) {
            value <<= 8;
            value |= (bytes[i] & 0xFF);
        }
        return value;
    }
}