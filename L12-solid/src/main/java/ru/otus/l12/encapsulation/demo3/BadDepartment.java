package ru.otus.l12.encapsulation.demo3;

import ru.otus.l12.encapsulation.demo1.Company;

public class BadDepartment {
    private Company company;

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
