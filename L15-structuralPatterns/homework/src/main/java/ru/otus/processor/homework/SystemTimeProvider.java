package ru.otus.processor.homework;

public class SystemTimeProvider implements TimeProvider {
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

}