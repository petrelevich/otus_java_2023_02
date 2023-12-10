package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorTimeException implements Processor {
    private final TimeProvider timeProvider;

    public ProcessorTimeException(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public Message process(Message message) {
        long time = timeProvider.getCurrentTimeMillis();
        if (time%2==0) {
            throw new RuntimeException("Выпала четная секунда");
        }
        return message;
    }
}

