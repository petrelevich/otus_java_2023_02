package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.lang.reflect.Method;
import java.util.Map;



public interface EntityClassMetaData<T> {
    String getName();

    Constructor<T> getConstructor();


    Field getIdField();

    List<Field> getAllFields();

    List<Field> getFieldsWithoutId();

    Map<String,Method> getSettersOfAllFields(List<String> fieldNames);

    Map<String,Method>  getGettersOfAllFields(List<String> fieldNames);
}
