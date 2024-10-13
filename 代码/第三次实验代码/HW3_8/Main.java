package HW3.HW3_8;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int month, year;
        Scanner sc = new Scanner(System.in);

        System.out.println("输入年份：");
        year = sc.nextInt();

        System.out.println("输入月份：");
        month = sc.nextInt();

        int daysOfMonth=switch (month){
            case 1,3,5,7,8,10,12 ->31;
            case 4,6,9,11->30;
            case 2->{
                if((year%4==0&&year%100!=0)||(year%400==0)){
                    yield  29;
                }else{
                    yield  28;
                }
            }
            default -> throw new RuntimeException("输入月份错误！");
        };
        System.out.println("在"+year+"的"+month+"月份有："+daysOfMonth+"天");
        sc.close();
    }
}
