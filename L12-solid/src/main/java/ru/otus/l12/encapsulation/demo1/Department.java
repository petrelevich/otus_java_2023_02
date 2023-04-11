package ru.otus.l12.encapsulation.demo1;

public class Department {
    Company company = new Company(); // Композиция

    public Company getCompany() {
        return this.company;
    }
}
