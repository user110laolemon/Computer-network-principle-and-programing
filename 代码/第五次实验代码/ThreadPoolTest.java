package HW5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
    public static void main(String[] args) {
        //自己创建线程对象
        /*new Thread(()->{
            System.out.println("This is a thread!");
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("Thread name: "+Thread.currentThread().getName());
            System.out.println("Thread go back to the pool");
        }).start();*/
        //通过线程池获取线程对象
        //创建线程池对象
        ExecutorService service= Executors.newFixedThreadPool(2);
        Runnable r =()->{
            System.out.println("This is a thread!");
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("Thread name: "+Thread.currentThread().getName());
            System.out.println("Thread go back to the pool");
        };
        service.submit(r); //获取第一个
        service.submit(r); //获取第二个
        service.submit(r);
        //submit方法调用结束后，程序并不终止，是因为线程池控制了线程的关闭。将使用完的线程又归还到了线程池中

        // 关闭线程池
        service.shutdown();
    }
}
