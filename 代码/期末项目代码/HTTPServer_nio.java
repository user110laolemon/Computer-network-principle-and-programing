package http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServer_nio {
    // 服务器Socket通道
    private ServerSocketChannel server;
    // 选择器
    private Selector selector;
    // 运行状态标志
    private boolean running = false;
    // 初始化线程池（最多15个：14核+1）
    private ExecutorService executor = Executors.newFixedThreadPool(15);


    // 初始化非阻塞NIO服务器
    public void init(int port) throws IOException {
        // 打开服务器Socket通道，监听新的TCP连接
        this.server = ServerSocketChannel.open();
        // 打开选择器，监视channel的I/O事件
        this.selector = Selector.open();
        // 服务器通道配置为非阻塞模式
        // 非阻塞时，I/O立即返回，不阻塞线程
        // 服务器可以同时处理多个连接，不用等待单个I/O操作直到完成
        server.configureBlocking(false);
        // 服务器通道绑定到8080，监听端口上的连接请求
        // InetSocketAddress:(IP地址,端口号)的Socket地址实现类
        server.bind(new InetSocketAddress(port));
        // 服务器通道注册到选择器上，指定感兴趣的事件类型为接受连接事件:SelectionKey.OP_ACCEPT
        server.register(selector, SelectionKey.OP_ACCEPT);
    }

    // 启动非阻塞NIO服务器
    public void start() throws IOException{
        if (server == null || selector == null) {
            // 未初始化
            throw new RuntimeException("服务器未初始化");
        }
        if (running) {
            // 已经运行
            throw new RuntimeException("重复启动服务器");
        }

        // 服务器开始运行
        this.running = true;
        System.out.println("NIO HTTP服务端已启动... ...");

        // Lambda表达式实现Runnnable接口
        Runnable main = () -> {
            while (running) {
                try {
                    // 阻塞等待I/O事件
                    selector.select();
                    // 创建选择器中准备好的通道的键集
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    // 创建键集的迭代器
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey current = iterator.next();

                        // 可接受新的连接
                        if (current.isAcceptable()) {
                            // 接受新的连接并创建新的Socket通道
                            SocketChannel channel = server.accept();
                            //System.out.println("连接成功" + channel.getRemoteAddress());
                            // 新通道配置为非阻塞模式，注册到选择器上，指定感兴趣读操作
                            channel.configureBlocking(false);
                            channel.register(selector, SelectionKey.OP_READ);
                        }
                        // 可读
                        if (current.isReadable()) {
                            // 将通道转换为SocketChannel，处理读操作
                            SocketChannel channel = (SocketChannel) current.channel();
                            requestSolver(channel);
                        }
                        // 从键集中移除当前键
                        iterator.remove();
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        // 主任务给executor执行
        executor.execute(main);
    }

    public void requestSolver(SocketChannel channel) {
        try {
            // 分配非直接缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // 创建拼接字符串实例
            StringBuilder builder = new StringBuilder();

            // 从通道中读取数据到byteBuffer
            while (channel.read(byteBuffer) > 0) {
                // 缓冲区从写模式切换到读模式
                byteBuffer.flip();
                // byteBuffer中的字节解码为字符串，给StringBuilder
                builder.append(StandardCharsets.UTF_8.decode(byteBuffer));
                // 清除缓冲区
                byteBuffer.clear();
            }
            String requestStr = builder.toString();

            // 移除请求字符串首尾的空白字符后是空的
            if (requestStr.trim().isEmpty()) {
                return;
            }
            // 请求字符串按空格分裂
            String[] mainArgs = requestStr.split(" ");
            if (mainArgs.length < 2) {
                System.out.println("异常请求，忽略！");
                return;
            }
            // 获取方法，并判断POST
            String method = mainArgs[0];
            if (method.equals("POST")) {
                System.out.println("接收到POST方法请求！忽略一次！");
                return;
            }
            // 获取路径，并分别处理
            String path = mainArgs[1];
            if (path.equals("/index.html")) {
                RequestTask task = new RequestTask(channel);
                executor.execute(task);
            } else {
                String[] lines = requestStr.split("\r\n");
                StringBuilder modifiedRequest = new StringBuilder();
                for (String line : lines) {
                    if (line.startsWith("Host: ")) {
                        line = "Host: dase.550w.host";
                    }
                    modifiedRequest.append(line).append("\r\n");
                }
                modifiedRequest.append("\r\n");

                TranspondTask task = new TranspondTask(channel, modifiedRequest.toString());
                System.out.println("已提交处理task2");
                executor.execute(task);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 关闭非阻塞NIO服务器
    private void close() {
        // 停止服务器的主循环
        running = false;
        // 关闭服务器通道
        if (server != null && server.isOpen()) {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 关闭选择器
        if (selector != null && selector.isOpen()) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 关闭线程池，不接受新任务，等提交的任务完成
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }


    public static void main(String[] args) {
        HTTPServer_nio server = new HTTPServer_nio();
        try {
            server.init(8080);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            server.close();
        }
    }
}

class RequestTask implements Runnable {
    private final SocketChannel socketChannel;
    // 响应字节数组包装到静态Buffer中
    private static final ByteBuffer responseBuffer = ByteBuffer.wrap((
            "HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n10214602404 李芳<br><br>简易 HTTP 服务器"
    ).getBytes(StandardCharsets.UTF_8));

    public RequestTask(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            // 创建responseBuffer的副本，避免多线程发生异常
            ByteBuffer buffer = responseBuffer.duplicate();
            socketChannel.write(buffer);
            socketChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class TranspondTask implements Runnable {
    private final SocketChannel socketChannel;
    private final String requestStr;
    // 响应时间阈值为10秒
    private final long RESPONSE_TIME_THRESHOLD_MS = 10000;

    public TranspondTask(SocketChannel socketChannel, String requestStr) {
        this.socketChannel = socketChannel;
        this.requestStr = requestStr;
    }

    @Override
    public void run() {
        // 建立目标服务器通通连接
        try (SocketChannel serverChannel = SocketChannel.open()) {
            serverChannel.configureBlocking(false);
            serverChannel.connect(new InetSocketAddress("dase.550w.host", 80));
            // 等待连接完成
            while (!serverChannel.finishConnect()) {
            }

            // 发送请求数据
            ByteBuffer requestBuffer = ByteBuffer.wrap(requestStr.getBytes(StandardCharsets.UTF_8));
            while (requestBuffer.hasRemaining()) {
                serverChannel.write(requestBuffer);
            }

            // 读取响应数据并转发回客户端
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            StringBuilder responseBuilder = new StringBuilder();

            // 开始计时
            long startTime = System.currentTimeMillis();
            while (true) {
                // 从目标服务器读数据
                int bytesRead = serverChannel.read(buffer);
                if (bytesRead == -1) {
                    // 流末尾
                    break;
                } else if (bytesRead > 0) {
                    // 从写模式切换到读模式
                    buffer.flip();
                    // 复制缓冲区中的数据
                    byte[] data = new byte[bytesRead];
                    buffer.get(data);
                    responseBuilder.append(new String(data, StandardCharsets.UTF_8));
                    // 从写模式切换到读模式
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        // 写入socketChannel
                        socketChannel.write(buffer);
                    }
                    // 清除，以便下一次读取
                    buffer.clear();
                }
                // 检查响应时间
                if (System.currentTimeMillis() - startTime > RESPONSE_TIME_THRESHOLD_MS) {
                    break;
                }
            }
            // 关闭serverChannel输出
            serverChannel.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
