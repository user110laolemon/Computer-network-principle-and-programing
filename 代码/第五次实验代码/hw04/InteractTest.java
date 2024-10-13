package HW5.hw04;

import static java.lang.Thread.sleep;

public class InteractTest {
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
    }
}
