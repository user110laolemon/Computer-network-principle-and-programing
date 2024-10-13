package HW9.bntask;

import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class UDPFileSender {
    private static final int PACKET_SIZE = 1024;
    private static final int TIMEOUT = 5000; // 5秒超时时间

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        File file = new File("checksum1e8_udp.txt");
        FileWriter fileWriter = new FileWriter("checksum1e8_udp.txt");
        try {
            Random r = new Random(2023);
            for (int i = 0; i < 1e8; i++) {
                fileWriter.write(r.nextInt());
            }
            System.out.println("发送文件生成完毕");
            System.out.println("发送文件的md5为: " + MD5Util.getMD5(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] buffer = new byte[PACKET_SIZE];
        FileInputStream fis = new FileInputStream(file);
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost(), 9091);

        try {
            int len;
            long sequenceNumber = 0; // 包序号
            while ((len = fis.read(buffer)) != -1) {
                byte[] dataWithSequenceNumber = addSequenceNumber(buffer, sequenceNumber); // 添加包序号
                packet.setData(addSequenceNumber(dataWithSequenceNumber, sequenceNumber)); // 添加包序号
                socket.send(packet);
                //System.out.println("发送数据包，序号：" + sequenceNumber);

                socket.setSoTimeout(TIMEOUT);// 等待 ACK
                byte[] ackBuffer = new byte[1];
                DatagramPacket ackPacket = new DatagramPacket(ackBuffer, ackBuffer.length);
                while (true) {
                    try {
                        socket.receive(ackPacket);
                        break; // 收到 ACK，跳出循环
                    } catch (SocketTimeoutException e) {
                        // 未收到 ACK，重传数据包
                        socket.send(packet);
                        //System.out.println("超时，重传数据包，序号：" + sequenceNumber);
                    }
                }
                sequenceNumber++;
            }
            // 发送空数据包作为终止符
            DatagramPacket terminatePacket = new DatagramPacket(new byte[0], 0, InetAddress.getLocalHost(), 9091);
            socket.send(terminatePacket);
            System.out.println("文件发送完毕！");
            System.out.println("发送文件的md5为: " + MD5Util.getMD5(file));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    private static byte[] addSequenceNumber(byte[] data, long sequenceNumber) {
        byte[] sequenceNumberBytes = longToBytes(sequenceNumber);
        byte[] newData = new byte[data.length + sequenceNumberBytes.length];
        System.arraycopy(sequenceNumberBytes, 0, newData, 0, sequenceNumberBytes.length);
        System.arraycopy(data, 0, newData, sequenceNumberBytes.length, data.length);
        return newData;
    }

    private static byte[] longToBytes(long value) {
        byte[] result = new byte[Long.BYTES];
        for (int i = Long.BYTES - 1; i >= 0; i--) {
            result[i] = (byte) (value & 0xFF);
            value >>= 8;
        }
        return result;
    }
}
