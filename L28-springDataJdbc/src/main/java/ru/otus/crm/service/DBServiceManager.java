package ru.otus.crm.service;

import ru.otus.crm.model.Manager;

import java.util.List;
import java.util.Optional;

public interface DBServiceManager {

    Manager saveManager(Manager client);

    Optional<Manager> getManager(String no);

    List<Manager> findAll();

    List<Manager> findByLabel(String label);
}
