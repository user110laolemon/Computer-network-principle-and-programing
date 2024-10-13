package HW4;

public class interruptDeamon02 extends Thread {
    private volatile boolean exitFlag = false;

    public void run() {
        while (!exitFlag) {
            System.out.println("守护线程正在执行");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("守护线程已退出");
    }

    public void setExitFlag(boolean exitFlag) {
        this.exitFlag = exitFlag;
    }

    public static void main(String[] args) throws InterruptedException {
        interruptDeamon02 daemonThread = new interruptDeamon02();
        daemonThread.setDaemon(true); // 设置为守护线程
        daemonThread.start();

        // 主线程执行一段时间后设置退出标志位
        Thread.sleep(5000);
        System.out.println("主线程设置退出标志位");
        daemonThread.setExitFlag(true); // 设置退出标志位
    }
}

