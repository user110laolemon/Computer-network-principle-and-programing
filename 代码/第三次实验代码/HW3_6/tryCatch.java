package HW3.HW3_6;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class tryCatch {
    public static void main(String[] args) {
        //1.
        int[] arr1 = new int[2];
        try {
            System.out.println(arr1[3]);
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.out.println("ArrayIndexOutOfBoundsException:" + e1.getMessage());
        }

        //2.
        int month = -10;
        try {
            if (month < 0) {
                throw new IllegalArgumentException("Months shouldn't be negative");
            }
        } catch (IllegalArgumentException e2) {
            System.out.println("IllegalArgumentException:" + e2.getMessage());
        }

        //3.
        try {
            int res = 1 / 0;
            System.out.println(res);
        } catch (ArithmeticException e3) {
            System.out.println("ArithmeticException:" + e3.getMessage());
        }

        //4.
        try {
            int[] arr2 = new int[-1];
        } catch (NegativeArraySizeException e4) {
            System.out.println("NegativeArraySizeException:" + e4.getMessage());
        }

        //5.
        List<Integer> l1=new ArrayList<>(List.of(1,2,3));
        try {
            for(Integer i:l1){
                l1.remove(i);
            }
        } catch (ConcurrentModificationException e5) {
            System.out.println("ConcurrentModificationException:" + e5.getMessage());
        }
    }
}
