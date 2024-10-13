package HW5.hw07;

public class PlusMinus02 {
    private int num;
    //volatile int num;
    private final Object lock =new Object(); //加锁

    public PlusMinus02(int n){
        //添加构造方法，方便设置num
        this.num=n;
    }

    public void plusOne() {
        synchronized (lock) {
            this.num = this.num + 1;
            printNum();
            //通知在lock等待的线程
            lock.notify();
        }
    }

    public void minusOne() {
        synchronized (lock) {
            if(this.num==1){
                try{
                    lock.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            this.num = this.num - 1;
            printNum();
        }
    }

    public void printNum() {
        System.out.println("num = " + this.num);
    }
}
