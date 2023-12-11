package ru.otus.jdbc.mapper;

import java.beans.FeatureDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T>{
    Class<T> t;

    Field idField;

    List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> t) {
        this.t = t;
//        getAllFields().forEach(field -> field.setAccessible(true));
    }

    @Override
    public String getName() {
        return t.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return t.getDeclaredConstructor(null);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        if (idField == null) {
            idField = getAllFields().stream()
                    .filter(field -> field.isAnnotationPresent(Id.class))
                    .findFirst()
                    .orElse(null);
        }
        return idField;
    }

    public Map<String,Method> getSettersOfAllFields(List<String> fieldNames){
        List<PropertyDescriptor> pds = new ArrayList<>();
        try {
            for (PropertyDescriptor pd : Introspector.getBeanInfo(t).getPropertyDescriptors()) {
                if (pd.getWriteMethod() != null && !"class".equals(pd.getName()) && fieldNames.contains(pd.getName())){
                    pds.add(pd);
                }
            }
            return pds.stream().collect(Collectors.toMap(PropertyDescriptor::getName,PropertyDescriptor::getWriteMethod));
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String,Method> getGettersOfAllFields(List<String> fieldNames){
        List<PropertyDescriptor> pds = new ArrayList<>();
        try {
            for (PropertyDescriptor pd : Introspector.getBeanInfo(t).getPropertyDescriptors()) {
                if (pd.getReadMethod() != null && !"class".equals(pd.getName()) && fieldNames.contains(pd.getName())){
                    pds.add(pd);
                }
            }
            return pds.stream().collect(Collectors.toMap(PropertyDescriptor::getName,PropertyDescriptor::getReadMethod));
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(t.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        if (fieldsWithoutId == null) {
            fieldsWithoutId = getAllFields().stream()
                    .filter(field -> !field.isAnnotationPresent(Id.class))
                    .toList();
        }
        return fieldsWithoutId;
    }
}
