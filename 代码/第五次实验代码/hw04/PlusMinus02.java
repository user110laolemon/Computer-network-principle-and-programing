package HW5.hw04;

public class PlusMinus02 {
    volatile int num;
    //int num;

    public void plusOne() {
        synchronized (this) {
            this.num = this.num + 1;
            printNum();
        }
    }

    public void minusOne() {
        synchronized (this) {
            /*this.num = this.num - 1;
            printNum();*/
            if (this.num > 1) { // 添加判断，当num已经为1时，停止减1操作
                this.num = this.num - 1;
                printNum();
            }
        }
    }

    public void printNum() {
        System.out.println("num = " + this.num);
    }
}
