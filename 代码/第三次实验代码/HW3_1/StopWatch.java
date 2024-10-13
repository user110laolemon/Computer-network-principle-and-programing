package HW3.HW3_1;

public class StopWatch extends Watch{

    public long getElapsedTime(){
        if(getStartTime()==null||getEndTime()==null){
            System.out.print("Please start counting!");
            return 0;
        }

        return getEndTime().toEpochMilli()-getStartTime().toEpochMilli();

    }
}
