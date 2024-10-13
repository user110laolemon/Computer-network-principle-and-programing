package HW4;

public class joinMain {
    public static void main(String[] args) throws InterruptedException {
        var t = new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                System.out.println(i);
            }
        });
        t.start();
        //t.join(); // 试试看把这行注释掉
        for (int i = -100; i <= -1; i++) {
            System.out.println(i);
        }
    }
}
