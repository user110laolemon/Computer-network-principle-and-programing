package HW1;

import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
/*        String str1 = "the light";
        String str2 = str1;
        String str3 = new String(str1);
        String str4 = "the light";
        String str5 = "the " + "light";
        System.out.println(str1 == str2);
        System.out.println(str1 == str3);
        System.out.println(str1 == str4);
        System.out.println(str1 == str5);
        System.out.println(str1.equals(str4));*/

        //.next()一次只接收一个不包含空格的字符串，空格出现就会再进行下次读取
        Scanner sc1 =new Scanner(System.in);
        while(sc1.hasNext()){   //判断是否有输入
            String line = sc1.next();   //把输入赋值给字符串line
            System.out.println("next()接收到字符串："+line);
        }

        //.nextLine()一次接收一整行包含空格的字符串
        /*Scanner sc2 =new Scanner(System.in);
        while(sc2.hasNext()){   //判断是否有输入
            String line = sc2.nextLine();   //把输入赋值给字符串line
            System.out.println("nextLine()接收到字符串："+line);
        }*/


    }
}
