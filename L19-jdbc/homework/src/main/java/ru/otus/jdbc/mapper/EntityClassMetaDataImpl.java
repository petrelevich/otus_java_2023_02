package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public class EntityClassMetaDataImpl implements EntityClassMetaData{

    @Override
    public String getName() {
        return getName();
    }

    @Override
    public Constructor getConstructor() {
        return getConstructor();
    }

    @Override
    public Field getIdField() {
        return getIdField();
    }

    @Override
    public List<Field> getAllFields() {
        return getAllFields();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getFieldsWithoutId();
    }
}
