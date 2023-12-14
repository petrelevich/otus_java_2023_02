package ru.otus.jdbc.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
//@RequiredArgsConstructor
public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {

    EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public EntityClassMetaData<T> getEntityClassMetaData() {
        return entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select %s from %s", getAllColumnNamesWithDelimeter(), getTableName());
    }


    @Override
    public String getSelectByIdSql() {
        return String.format("select %s from %s where %s=?", getAllColumnNamesWithDelimeter(), getTableName(), getIdFieldName());
    }

    @Override
    public String getInsertSql() {
        return String.format("insert into %s(%s) values (%s)", getTableName(), getFieldNamesWithoutId(), getValuesForInsert());
    }

    @Override
    public String getUpdateSql() {
        return String.format("update %s set %s where %s=?", getTableName(), getValuesForUpdateSet(),getIdFieldName());
    }

    private String getIdFieldName() {
        return entityClassMetaData.getIdField().getName();
    }

    private String getTableName() {
        return entityClassMetaData.getName().toLowerCase();
    }

    public String getAllColumnNamesWithDelimeter() {
        return String.join(",", getAllColumnNames());
    }

    public List<String> getAllColumnNames(){
        return entityClassMetaData.getAllFields().stream().map(Field::getName).toList();
    }

    private String getFieldNamesWithoutId() {
        return entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).collect(Collectors.joining(","));
    }

    private String getValuesForInsert() {
        return Stream.generate(() -> "?").limit(entityClassMetaData.getFieldsWithoutId().size()).collect(Collectors.joining(","));
    }

    private String getValuesForUpdateSet() {
        return entityClassMetaData.getFieldsWithoutId().stream().map(field -> field.getName() + "=?").collect(Collectors.joining(","));
    }
}