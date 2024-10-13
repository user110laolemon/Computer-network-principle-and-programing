package HW5.hw07;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        //直接使用构造方法
        PlusMinus02 plusMinus = new PlusMinus02(1000);

        Thread th1 = new Thread(() -> {
            while (true) {
                //因为PM02类中，在函数里就已经有判断，这里可以省略
                /*if (plusMinus.num == 1) {
                    continue;
                }*/
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

