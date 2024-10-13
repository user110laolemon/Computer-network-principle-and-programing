package HW3.HW3_2;

import java.util.Random;

class Fish implements Comparable<Fish> {
    int size;

    public Fish() {
        Random r = new Random();
        this.size = r.nextInt(100);
    }

    void print() {
        System.out.print(this.size + "<");
    }

    @Override
    public int compareTo(Fish other) {
        return this.size - other.size;
    }
}
