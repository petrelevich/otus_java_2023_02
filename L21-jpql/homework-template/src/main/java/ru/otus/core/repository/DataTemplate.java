package ru.otus.core.repository;

import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public interface DataTemplate<T> {
    Optional<T> findById(Session session, long id);
    List<T> findByEntityField(Session session, String entityFieldName, Object entityFieldValue);

    List<T> findAll(Session session);

    T insert(Session session, T object);

    T update(Session session, T object);
}
