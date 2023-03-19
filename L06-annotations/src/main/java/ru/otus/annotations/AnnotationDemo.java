package ru.otus.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationDemo {
    public static void main(String[] args) {
        AnyClass anyClass = new AnyClass(1, 2);

        for (Field field : anyClass.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(AnnotationField.class)) {
                System.out.printf("field \"%s\" has annotation%n", field.getName());
            }
        }

        for (Method method : anyClass.getClass().getDeclaredMethods()) {
            Annotation[] annotations = method.getAnnotations();
            if (annotations.length > 0) {
                System.out.printf("method \"%s\", has annotations:%n", method.getName());
            }
            for(Annotation annotation: annotations) {
                System.out.print("\t");
                System.out.println(annotation.annotationType().getName());
                if (annotation instanceof AnnotationMethod annotationMethod) {
                    System.out.print("\t\t");
                    System.out.printf("AnnotationMethod runLevel = %d", annotationMethod.runLevel());
                }
            }
        }
    }
}
