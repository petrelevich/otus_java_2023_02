package ru.otus.annotations;


public class AnyClass {
    @AnnotationField
    private final int fieldAnnotated;

    private final int field;

    public AnyClass(int fieldAnnotated, int field) {
        this.fieldAnnotated = fieldAnnotated;
        this.field = field;
    }

    @AnnotationMethod(runLevel = 45)
    public int getFieldAnnotated() {
        return fieldAnnotated;
    }

    public int getField() {
        return field;
    }
}
