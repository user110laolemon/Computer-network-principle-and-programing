package HW3.HW3_7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {
    enum Color {
        RED(1),
        GREEN(2),
        BLUE(3);

        int type;

        Color(int _type) {
            this.type = _type;
        }
    }
    public static void main(String[] args) {
        ArrayList<Color> list=new ArrayList<>();
        for (int i = 1; i <=3 ; i++) {
            Collections.addAll(list,Color.values());
        }
        Random r=new Random(1234567);
        Collections.shuffle(list,r);
        for (Color c : list) {
            switch (c) {
                case RED, BLUE, GREEN:
                    System.out.print(c.type);
                    break;
                default:
                    throw new RuntimeException("Wrong!");
            }
        }
    }
}
