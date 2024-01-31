package ru.otus.jdbc.mapper;

import ru.otus.crm.model.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private Class<T> cl;
    private Constructor<T> constructor;

    public EntityClassMetaDataImpl(Class<T> cls) throws NoSuchMethodException {
        cl = cls;
        constructor = cl.getConstructor();
    }

    @Override
    public String getName() {
        var res = cl.getName().substring(Math.min(cl.getName().lastIndexOf('.') + 1, cl.getName().length() - 1));
        return res;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        var fields = cl.getDeclaredFields();
        var res = Arrays
                .stream(fields)
                .filter(f -> {
                    return f.getAnnotation(Id.class) != null;
                })
                .collect(Collectors.toList());
        if (res.size() == 0) {
            throw new ClassCastException(
                    String.format("no id found in class=%s", cl.getName())
            );
        } else if (res.size() > 1) {
            throw new ClassCastException(
                    String.format("too may id in class=%s", cl.getName())
            );
        } else {
            return res.get(0);
        }
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(cl.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays
                .stream(cl.getDeclaredFields())
                .filter(f -> f.getAnnotation(Id.class) == null)
                .collect(Collectors.toList());
    }
}
