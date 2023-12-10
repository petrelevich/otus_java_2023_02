package ru.otus.jdbc.mapper;

import java.util.List;

/**
 * Создает SQL - запросы
 */
public interface  EntitySQLMetaData<T> {
    EntityClassMetaData<T> getEntityClassMetaData();
    List<String> getAllColumnNames();

    String getSelectAllSql();

    String getSelectByIdSql();

    String getInsertSql();

    String getUpdateSql();
}
