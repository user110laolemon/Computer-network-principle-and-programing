package HW2;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class PrintTask7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a[] = new int[10];
        int i = 0;

        /*while (i<10) {    //判断条件用sc.hasNext()会死循环
            a[i++] = sc.nextInt();
        }*/

        int temp;
        while (sc.hasNext()) {    //判断条件用sc.hasNext()会死循环
            temp = sc.nextInt();
            a[i++] = temp;
            System.out.print( "A");
        }

        Arrays.sort(a);
        PrintWriter pw = new PrintWriter(System.out);
        pw.println(Arrays.toString(a));
        pw.flush();   //注释掉该句无法正常输出
    }
}