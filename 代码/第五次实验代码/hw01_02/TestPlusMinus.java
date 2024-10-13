package HW5.hw01_02;

public class TestPlusMinus {
    public static void main(String[] args) {
        PlusMinus plusMinus = new PlusMinus();
        plusMinus.num = 1000; // 初始值设置为 1000
        int threadNum = 1000; // 线程数目设置为 1000
        Thread[] plusThreads = new Thread[threadNum];
        Thread[] minusThreads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            Thread thread1 = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                plusMinus.plusOne();
            });
            Thread thread2 = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                plusMinus.minusOne();
            });
            thread1.start();
            thread2.start();
            plusThreads[i] = thread1;
            minusThreads[i] = thread2;
        }
        for (Thread thread : plusThreads) { // 等待所有加一线程结束
            try {
                thread.join();
            } catch (InterruptedException e) { // 等待所有减一线程结束
                e.printStackTrace();
            }
        }
        for (Thread thread : minusThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("所有线程结束后的 num 值为：" + plusMinus.printNum());
    }
}
