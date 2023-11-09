package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityResultSetFactory<T> factory;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityResultSetFactory<T> factory) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.factory = factory;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    T r = factory.create(rs);
                    return r;
                }
                return null;
            } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), Collections.emptyList(), rs -> {
            var res = new ArrayList<T>();
            try {
                while (rs.next()) {
                    T r = factory.create(rs);
                    res.add(r);
                }
                return res;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (InvocationTargetException e) {
                throw new DataTemplateException(e);
            } catch (InstantiationException e) {
                throw new DataTemplateException(e);
            } catch (IllegalAccessException e) {
                throw new DataTemplateException(e);
            } catch (NoSuchMethodException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T entity) {
        try {
            var meta = new EntityClassMetaDataImpl<>(entity.getClass());
            EntityReflector<T> refl = new EntityReflector<T>();
            var params = refl.getFieldsValue(meta.getFieldsWithoutId(), entity);
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T entity) {
        try {
            var meta = new EntityClassMetaDataImpl<>(entity.getClass());
            EntityReflector<T> refl = new EntityReflector<T>();
            var params = refl.getFieldsValue(meta.getFieldsWithoutId(), entity);
            params.add(refl.getFieldValue(meta.getIdField(), entity));
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                    params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
