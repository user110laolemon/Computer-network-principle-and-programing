package HW2;

public class PrintTask3 {
    public static void main(String[] args){
        String init_out = "*";
        for (int i = 0; i < 5; i++) {
            System.out.println(init_out);
            init_out = init_out + "*";
        }
    }
}
