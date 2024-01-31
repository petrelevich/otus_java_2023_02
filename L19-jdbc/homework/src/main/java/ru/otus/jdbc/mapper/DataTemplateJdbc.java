package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DataTemplateJdbc<T> implements DataTemplate<T> {
    DbExecutor dbExecutor;
    EntitySQLMetaData<T> entitySQLMetaData;
    EntityClassMetaData<T> entityClassMetaData;
    Map<String, Method> settersOfAllFields;
    Map<String, Method> gettersOfAllFields;
    List<String> allColumnNames;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData<T> entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entitySQLMetaData.getEntityClassMetaData();
        this.allColumnNames = entitySQLMetaData.getAllColumnNames();
        this.settersOfAllFields = entityClassMetaData.getSettersOfAllFields(allColumnNames);
        this.gettersOfAllFields = entityClassMetaData.getGettersOfAllFields(allColumnNames);
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), Collections.singletonList(id), (rs) -> {
            T result = null;
            try {
                if (rs.next()) {
                    result = initializeNewInstance(rs);
                }
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
            return result;
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var result = new ArrayList<T>();
            try {
                while (rs.next()) {
                    result.add(initializeNewInstance(rs));
                }
                return result;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T entity) {
        List<Object> params = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> extractFieldValue(entity, field))
                .toList();
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T entity) {
        List<Field> fieldsWithIdLast = new ArrayList<>(entityClassMetaData.getFieldsWithoutId());
        fieldsWithIdLast.add(entityClassMetaData.getIdField());

        List<Object> params = fieldsWithIdLast.stream().map(field -> extractFieldValue(entity, field)).toList();
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private Object extractFieldValue(T entity, Field field) {
        try {
            return gettersOfAllFields.get(field.getName()).invoke(entity, null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private T initializeNewInstance(ResultSet rs) {
        try {
            T newInstance = entityClassMetaData.getConstructor().newInstance();

            for (int i = 0; i < allColumnNames.size(); i++) {
                String columnName = allColumnNames.get(i);
                Method setter = settersOfAllFields.get(columnName);
                Class<?> parameterType = setter.getParameterTypes()[0];


                if (parameterType.getSimpleName().equals("Integer")) {
                    setter.invoke(newInstance, rs.getInt(i + 1));
                    continue;
                }

                if (parameterType.getSimpleName().equals("String")) {
                    setter.invoke(newInstance, rs.getString(i + 1));
                    continue;
                }

                if (parameterType.getSimpleName().equals("Long")) {
                    setter.invoke(newInstance, rs.getLong(i + 1));
                }

            }
            return newInstance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}