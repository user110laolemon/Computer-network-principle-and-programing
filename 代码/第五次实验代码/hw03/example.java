package HW5.hw03;

public class example {
    public static void main(String[] args) {
        PlusMinus plusMinus1 = new PlusMinus();
        plusMinus1.num = 1000;
        PlusMinus plusMinus2 = new PlusMinus();
        plusMinus2.num = 1000;
        Thread thread1 = new Thread(() -> {
            synchronized (plusMinus1) {
                System.out.println("thread1 正在占用 plusMinus1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread1 试图继续占用 plusMinus2");
                System.out.println("thread1 等待中...");
                synchronized (plusMinus2) {
                    System.out.println("thread1 成功占用了 plusMinus2 ");
                }
            }
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            synchronized (plusMinus2) {
                System.out.println("thread2 正在占用 plusMinus2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2 试图继续占用 plusMinus1");
                synchronized (plusMinus1) {
                    System.out.println("thread1 成功占用了 plusMinus1 ");
                }
            }
        });
        thread2.start();
    }
}
