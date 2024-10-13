package HW3.HW3_2;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Fish[] fishArr = new Fish[10];
        for (int i = 0; i < 10; i++) {
            fishArr[i] = new Fish();
        }
        Arrays.sort(fishArr);
        for (int i = 0; i < fishArr.length; i++) {
            if (i == fishArr.length - 1) {
                System.out.print(fishArr[i].size);
            } else {
                fishArr[i].print();
            }
        }

    }
}
