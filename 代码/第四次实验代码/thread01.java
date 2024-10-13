package HW4;

public class thread01 extends Thread {
    public void run() {
        System.out.println("The name of this thread is: " + Thread.currentThread().getName());
        System.out.println("The id of " + Thread.currentThread().getName() + " is: " + Thread.currentThread().threadId());
    }

    public static void main(String[] args) {
        thread01 th1 = new thread01();
        thread01 th2 = new thread01();
        thread01 th3 = new thread01();
        th1.start();
        th2.start();
        th3.start();
    }
}
