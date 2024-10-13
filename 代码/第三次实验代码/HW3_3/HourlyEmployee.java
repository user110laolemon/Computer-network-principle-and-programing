package HW3.HW3_3;

class HourlyEmployee extends Employee {
    double hourlySalary;
    double hour;

    public HourlyEmployee(String n, int bm, double hs, double h) {
        super(n, bm);
        this.hourlySalary = hs;
        this.hour = h;
    }

    @Override
    public double getSalary(int month) {
        return hourlySalary * hour;
    }
}
