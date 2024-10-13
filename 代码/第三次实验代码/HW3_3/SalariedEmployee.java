package HW3.HW3_3;

class SalariedEmployee extends Employee {
    double monthlySalary;

    public SalariedEmployee(String n, int bm, double ms) {
        super(n, bm);
        this.monthlySalary = ms;
    }

    @Override
    public double getSalary(int month) {
        if (month == this.birthMonth) {
            return monthlySalary + 100.0;
        } else {
            return monthlySalary;
        }
    }
}
