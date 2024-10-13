package HW4;

public class niming {
    public static void main(String[] args) {
        /*//format1
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println("thread:" + i);
                }
            }
        }).start();*/
        //format2
        // Runnable r = new Runnable(){
        //    public void run(){
        //        for (int i = 0; i < 20; i++) {
        //            System.out.println("thread:"+i);
        //        }
        //    }
        //}; //‐‐‐这个整体 相当于new MyRunnable()
        //new Thread(r).start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                System.out.println("thread:" + i);
            }
        }).start();

        for (int i = 0; i < 20; i++) {
            System.out.println("main:" + i);
        }
    }
}