package ru.otus.l12.encapsulation.demo3;

import ru.otus.l12.encapsulation.demo1.Company;

public class BadDepartmentDemo {

    public static void main(String[] args) {
        // Какие здесь проблемы?
        Company company = new Company();
        BadDepartment department = new BadDepartment();
        department.setCompany(company);
    }
}
