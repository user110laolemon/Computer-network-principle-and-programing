package HW3.HW3_3;

public class CountSalary {
    public static void main(String[] args) {
        int month = 2;
        Employee salesEmployee = new SalesEmployee("Xiao Hong", 3, 5500.0, 0.1, 2000.0);
        Employee salariedEmployee = new SalariedEmployee("Xiao Ming", 2, 3000.0);
        Employee hourlyEmployee = new HourlyEmployee("Xiao Gang", 12, 24.0, 100.0);
        System.out.println(salesEmployee.name + "'s salary is:" + salesEmployee.getSalary(month));
        System.out.println(salariedEmployee.name + "'s salary is:" + salariedEmployee.getSalary(month));
        System.out.println(hourlyEmployee.name + "'s salary is:" + hourlyEmployee.getSalary(month));
    }
}
