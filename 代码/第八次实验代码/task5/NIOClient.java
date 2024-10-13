package HW8.task5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    private static int BYTE_LENGTH=64;
    public static void main(String[] args) {
        try {
            SocketChannel socketChannel=SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1",9091));
            System.out.println("客户端已连接至服务器");

            sendMessage(socketChannel,"Hello Server");
            receiveMessage(socketChannel);

            socketChannel.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private static void sendMessage(SocketChannel socketChannel,String msg)throws IOException{
        ByteBuffer buffer=ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(buffer);
        System.out.println("客户端发送消息："+msg);
    }

    private static void receiveMessage(SocketChannel socketChannel)throws IOException{
        ByteBuffer buffer=ByteBuffer.allocate(BYTE_LENGTH);
        socketChannel.read(buffer);
        buffer.flip();
        byte[] bytes=new byte[buffer.remaining()];
        buffer.get(bytes);
        String msg=new String(bytes);
        System.out.println("客户端收到消息："+msg);
    }
}
