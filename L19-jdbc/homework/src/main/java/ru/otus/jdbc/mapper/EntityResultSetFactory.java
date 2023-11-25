package ru.otus.jdbc.mapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;


public interface EntityResultSetFactory<T> {
    T create(ResultSet rs) throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException, NoSuchMethodException;
}
