package HW3.HW3_3;

abstract class Employee {
    String name;
    int birthMonth;

    public Employee(String n, int bm) {
        this.name = n;
        this.birthMonth = bm;
    }

    public abstract double getSalary(int month);

}
