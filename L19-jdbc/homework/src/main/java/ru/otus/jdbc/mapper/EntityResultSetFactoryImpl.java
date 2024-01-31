package ru.otus.jdbc.mapper;

import java.lang.reflect.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class EntityResultSetFactoryImpl<T> implements EntityResultSetFactory<T> {
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public EntityResultSetFactoryImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        var ta =pt.getActualTypeArguments()[0];
        type = (Class)ta;
    }

    public T create(ResultSet rs) throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException, NoSuchMethodException {
        T instance = type.getDeclaredConstructor().newInstance();
        var flds = type.getDeclaredFields();
        for (var fld : flds) {
            setFieldValue(fld,instance, rs.getObject(fld.getName()));
        }
        return instance;
    }

    private void setFieldValue(Field fld, T obj, Object val) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var setter = "set" + fld.getName().substring(0, 1).toUpperCase() + fld.getName().substring(1);
        Class<?>[] parameterType = new Class<?>[] {fld.getType()};
        Method fieldSetter = obj.getClass().getMethod(setter, parameterType);
        fieldSetter.invoke(obj, new Object[]{val});
    }
}