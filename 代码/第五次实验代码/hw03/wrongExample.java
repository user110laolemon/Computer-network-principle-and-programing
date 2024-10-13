package HW5.hw03;

public class wrongExample {
    public static void main(String[] args) {
        PlusMinus pm1 = new PlusMinus();
        pm1.num = 1000;
        PlusMinus pm2 = new PlusMinus();
        pm2.num = 1000;
        PlusMinus pm3 = new PlusMinus();
        pm3.num = 1000;

        new Thread(() -> {
            synchronized (pm1) {
                System.out.println("Thread1 正在占用pm1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread1 试图继续占用pm2");
                System.out.println("Thread1 等待中...");
                synchronized (pm2) {
                    System.out.println("Thread1 成功占用了pm2");
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (pm2) {
                System.out.println("Thread2 正在占用pm2");
                System.out.println("Thread2 试图继续占用pm3");
                System.out.println("Thread2 等待中...");
                synchronized (pm3) {
                    System.out.println("Thread2 成功占用了pm3");
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (pm3) {
                System.out.println("Thread3 正在占用pm3");
                System.out.println("Thread3 试图继续占用pm1");
                System.out.println("Thread3 等待中...");
                synchronized (pm1) {
                    System.out.println("Thread3 成功占用了pm1");
                }
            }
        }).start();
    }
}
