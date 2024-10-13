package HW5.hw01_02;

public class PlusMinus {
    public volatile int num;

    public synchronized void plusOne() {
        num = num + 1;
    }

    public synchronized void minusOne() {
        num = num - 1;
    }

    public synchronized int printNum() {
        return num;
    }

    /*public void plusOne() {
        num = num + 1;
    }

    public void minusOne() {
        num = num - 1;
    }

    public int printNum() {
        return num;
    }*/

}
