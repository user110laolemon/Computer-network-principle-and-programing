package HW2;

import java.util.Arrays;

public class PrintTask4 {
    public static void main(String[] args) {
        int a0[] = new int[]{18, 62, 68, 82, 65, 9};
        Arrays.sort(a0);

        int result;
        result = Arrays.binarySearch(a0,68);
        System.out.println(result);
    }
}
