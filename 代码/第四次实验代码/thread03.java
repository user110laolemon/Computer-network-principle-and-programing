package HW4;

public class thread03 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        thread03 join = new thread03();
        Thread thread1 = new Thread(join, "上课铃响");
        Thread thread2 = new Thread(join, "老师上课");
        Thread thread3 = new Thread(join, "下课铃响");
        Thread thread4 = new Thread(join, "老师下课");
        thread1.start();
        //thread1.join();
        thread2.start();
        //thread2.join();
        thread3.start();
        //thread3.join();
        thread4.start();
    }
}
