package HW4;

public class interruptDeamon01 {
    public static void main(String[] args) throws InterruptedException {
        Thread daemonThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("守护线程正在运行中");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("守护线程被中断");
                    return;
                }
            }
        });
        daemonThread.setDaemon(true); // 设置为守护线程
        daemonThread.start();

        // 主线程执行一段时间后中断守护线程
        Thread.sleep(5000);
        System.out.println("主线程试图中断守护线程");
        daemonThread.interrupt(); // 中断守护线程
    }
}

