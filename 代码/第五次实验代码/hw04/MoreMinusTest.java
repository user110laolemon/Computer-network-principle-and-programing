package HW5.hw04;

import static java.lang.Thread.sleep;

public class MoreMinusTest {
    public static void main(String[] args) {
        PlusMinus02 plusMinus = new PlusMinus02();
        plusMinus.num = 1000;

        Thread th1 = new Thread(() -> {
            while (true) {
                if (plusMinus.num == 1) {
                    continue;
                }
                plusMinus.minusOne();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th1.start();

        Thread th2 = new Thread(() -> {
            while (true) {
                plusMinus.plusOne();
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th2.start();

        // 增加减 1 操作线程
        Thread th3 = new Thread(() -> {
            while (true) {
                if (plusMinus.num == 1) {
                    continue;
                }
                plusMinus.minusOne();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th3.start();

        // 增加减 1 操作线程
        Thread th4 = new Thread(() -> {
            while (true) {
                if (plusMinus.num == 1) {
                    continue;
                }
                plusMinus.minusOne();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th4.start();

        // 增加减 1 操作线程
        Thread th5 = new Thread(() -> {
            while (true) {
                if (plusMinus.num == 1) {
                    continue;
                }
                plusMinus.minusOne();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th5.start();

        // 增加减 1 操作线程
        Thread th6 = new Thread(() -> {
            while (true) {
                if (plusMinus.num == 1) {
                    continue;
                }
                plusMinus.minusOne();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th6.start();

        // 增加减 1 操作线程
        Thread th7 = new Thread(() -> {
            while (true) {
                if (plusMinus.num == 1) {
                    continue;
                }
                plusMinus.minusOne();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th7.start();
        // 增加减 1 操作线程
        Thread th8 = new Thread(() -> {
            while (true) {
                if (plusMinus.num == 1) {
                    continue;
                }
                plusMinus.minusOne();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th8.start();
    }
}
