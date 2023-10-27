package ru.otus.processor.homework;

import java.time.Instant;
import java.util.function.Supplier;


public class TimeProvider implements Supplier<Instant> {
    @Override
    public Instant get() {
        return Instant.now();
    }
}
