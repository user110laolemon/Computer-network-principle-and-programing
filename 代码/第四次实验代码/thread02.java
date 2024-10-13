package HW4;

public class thread02 implements Runnable{
    public void run(){
        int count =0;
        int i=1;
        while (count<2){
            System.out.print(i);
            if(i==9){
                count+=1;
                i=0;
            }
            i++;
        }
    }

    public static void main(String[] args) {
        thread02 myRun = new thread02();
        Thread th = new Thread(myRun);
        th.start();
        for(char c ='a';c<='z';c++){
            System.out.print(c);
        }
    }
}
