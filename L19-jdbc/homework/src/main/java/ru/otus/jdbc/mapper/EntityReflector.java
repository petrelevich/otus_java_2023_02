package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EntityReflector<T> {

    public Object getFieldValue(Field fld, T obj) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var getter = "get" + fld.getName().substring(0, 1).toUpperCase() + fld.getName().substring(1);
        Method fieldGetter = obj.getClass().getMethod(getter);
        var res = fieldGetter.invoke(obj);
        return res;
    }

    public List<Object> getFieldsValue(List<Field> flds, T obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        var res = new ArrayList<>();
        for (var fld : flds) {
            res.add(getFieldValue(fld,obj));
        }
        return res;
    }
}
