package ru.otus.l12.encapsulation.demo1;

public class Employee {
    Department department = new Department();

    public Department getDepartment() {
        return this.department;
    }

    public String getCompanyName() {
        return this.department.getCompany().getName();
        // а еще лучше так - return this.department.getCompanyName();
    }
}
