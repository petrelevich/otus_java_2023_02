package ru.otus.processor;

import ru.otus.model.Message;

import java.util.function.Supplier;

public class ProcessorFailOnEvenSeconds implements Processor {
    private final Supplier<Integer> currentSecondsProvider;

    public ProcessorFailOnEvenSeconds(Supplier<Integer> currentSecondsProvider) {
        this.currentSecondsProvider = currentSecondsProvider;
    }

    @Override
    public Message process(Message message) {
        if (currentSecondsProvider.get() % 2 == 0) {
            throw new IllegalStateException("Seconds must be only odd integers!");
        }

        return message;
    }
}
