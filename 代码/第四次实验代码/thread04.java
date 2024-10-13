package HW4;

public class thread04 implements Runnable {
    @Override
    public void run() {
        int worktime = 0;
        while (true) {
            System.out.println("助教在教室的第" + worktime + "秒");
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            worktime++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        thread04 inClassroom = new thread04();
        Thread thread = new Thread(inClassroom, "助教");
        //thread.setDaemon(true);
        thread.start();

        for (int i = 0; i < 10; i++) {
            thread.sleep(1000);
            System.out.println("同学们正在上课");
            if (i == 9) {
                System.out.println("同学们下课了");
            }
        }
        System.out.println("助教离开教室");
    }
}
