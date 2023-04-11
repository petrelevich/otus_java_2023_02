package ru.otus.l12.encapsulation.demo2;

import ru.otus.l12.encapsulation.demo1.Department;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Неизменяемые коллекции
public class CompanyUnmodifiableDepartments {
    private List<Department> departments = new ArrayList<>();

    public List<Department> getDepartments() {
        return Collections.unmodifiableList(departments);
    }
}
