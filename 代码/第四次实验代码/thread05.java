package HW4;
class MyRunnable01 implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i < 100; i++) {
            System.out.println("线程" + Thread.currentThread().getName() + "第" + i + "次执行！");
            //Thread.yield();
        }
    }
}

class MyRunnable02 implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i < 100; i++) {
            System.out.println("线程" + Thread.currentThread().getName() + "第" + i + "次执行！");
        }
    }
}

public class thread05 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new MyRunnable01(), "线程1");
        Thread t2 = new Thread(new MyRunnable02(), "线程2");
        //t1.setPriority(4);
        //t2.setPriority(6);
        t1.start();
        t2.start();
    }
}
