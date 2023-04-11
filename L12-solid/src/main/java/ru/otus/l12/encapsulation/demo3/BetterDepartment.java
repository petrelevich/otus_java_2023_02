package ru.otus.l12.encapsulation.demo3;

import ru.otus.l12.encapsulation.demo1.Company;

// Связь Организация -> Подразделение - композиция
public class BetterDepartment {
    private final Company company; // Что еще можно добавить, улучшить?

    BetterDepartment(Company company) {
        if (company == null)
            throw new IllegalArgumentException("company cannot be null");

        this.company = company;
    }

    public Company getCompany() {
        return this.company;
    }

    // сеттера нет специально, объект иммутабельный
}
