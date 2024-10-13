package HW3.HW3_1;

public class TestStopWatch {
    public static void main(String[] args) {
        StopWatch sw=new StopWatch();
        //start counting
        sw.start();

        //thread sleep
        try {
            Thread.sleep(3500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        //stop counting
        sw.stop();
        System.out.println("The lapse time recorded by stopwatch is: "+sw.getElapsedTime());

    }
}
