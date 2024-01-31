package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.Instant;
import java.util.function.Supplier;

public class ProcessorOddMinuteOnly implements Processor {
    private Supplier<Instant> timeProvider;

    public ProcessorOddMinuteOnly(Supplier<Instant> timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public Message process(Message message) {
        if (timeProvider.get().getEpochSecond() % 2 == 0) {
            throw new UnsupportedOperationException("Event second is unsupported!");
        } else {
            return message;
        }
    }
}