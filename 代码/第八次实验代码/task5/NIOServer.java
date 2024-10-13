package HW8.task5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    private static int BYTE_LENGTH=64;
    private Selector selector;

    public static void main(String[] args) throws IOException {
        try {
            new NIOServer().startServer();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void startServer()throws IOException{
        this.selector=Selector.open();
        ServerSocketChannel serverSocket=ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9091));
        serverSocket.configureBlocking(false);
        serverSocket.register(this.selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端已启动");

        for(;;){
            int readyCount= selector.select();
            if(readyCount==0){
                continue;
            }

            Set<SelectionKey> readyKeys=selector.selectedKeys();
            Iterator<SelectionKey> iterator=readyKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key=iterator.next();
                iterator.remove();
                if(!key.isValid()){
                    continue;
                }
                if(key.isAcceptable()){
                    this.accept(key);
                } else if (key.isReadable()) {
                    this.read(key);
                }
            }
        }
    }

    private void accept(SelectionKey key)throws IOException{
        ServerSocketChannel serverChannel=(ServerSocketChannel) key.channel();
        SocketChannel channel=serverChannel.accept();
        channel.configureBlocking(false);
        Socket socket=channel.socket();
        socket.setKeepAlive(true);
        SocketAddress remoteAddress=socket.getRemoteSocketAddress();
        System.out.println("已连接："+remoteAddress);

        channel.register(this.selector,SelectionKey.OP_READ);
    }

    private void read(SelectionKey key)throws IOException{
        SocketChannel channel=(SocketChannel) key.channel();
        ByteBuffer buffer=ByteBuffer.allocate(BYTE_LENGTH);
        int numRead= channel.read(buffer);
        if(numRead==-1){
            Socket socket=channel.socket();
            SocketAddress remoteAddress=socket.getRemoteSocketAddress();
            System.out.println("连接关闭："+remoteAddress);
            socket.close();
            key.cancel();
            return;
        }
        byte[] data=new byte[numRead];
        System.arraycopy(buffer.array(),0,data,0,numRead);
        String str=new String(data);
        System.out.println("服务器已收到信息："+str);

        write(key,"该消息已被服务器接收："+str);
    }

    private void write(SelectionKey key,String msg)throws IOException{
        SocketChannel channel=(SocketChannel) key.channel();
        ByteBuffer buffer=ByteBuffer.wrap(msg.getBytes());
        channel.write(buffer);
        System.out.println("服务器发送消息："+msg);
    }
}
