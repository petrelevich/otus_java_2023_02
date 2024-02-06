package ru.otus.jdbc.mapper;

import ru.otus.crm.model.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData <T>{
    Class <T> classObject;

    public EntityClassMetaDataImpl(Class <T> classObject) {
        this.classObject = classObject;
    }

    @Override
    public String getName(){
        return classObject.getSimpleName();
    };

    public Constructor<T> getConstructor(){
        try { return classObject.getConstructor();
    } catch (Exception e){
            System.out.println("No such method");
        };
        return null;
    }

    //Поле Id должно определять по наличию аннотации Id
    //Аннотацию @Id надо сделать самостоятельно
    public Field getIdField(){
        List<Field> fields = List.of(classObject.getDeclaredFields());
        for (Field field : fields){
            if (field.isAnnotationPresent(Id.class)){
            return field;}
        }
        return null;
    };

    public List<Field> getAllFields(){
        return Arrays.stream(classObject.getDeclaredFields()).toList();
    };

    public List<Field> getFieldsWithoutId(){
        List<Field> fields = List.of(classObject.getDeclaredFields());
        List<Field> fieldsWithoutId = new ArrayList<>();
        for (Field field : fields){
            if (!field.isAnnotationPresent(Id.class)){
                fieldsWithoutId.add(field);}
        }
        return fieldsWithoutId;
    };
}
