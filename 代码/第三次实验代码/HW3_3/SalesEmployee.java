package HW3.HW3_3;

class SalesEmployee extends Employee {
    double saleAmount;
    double rate;
    double basicSalary;

    public SalesEmployee(String n, int bm, double sa, double r, double bs) {
        super(n, bm);
        this.saleAmount = sa;
        this.rate = r;
        this.basicSalary = bs;
    }

    @Override
    public double getSalary(int mouth) {
        return this.basicSalary + this.rate * this.saleAmount;
    }
}
