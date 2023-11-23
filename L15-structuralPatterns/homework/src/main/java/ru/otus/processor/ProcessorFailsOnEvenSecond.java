package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorFailsOnEvenSecond implements Processor{
    private final int currentSecond;

    public ProcessorFailsOnEvenSecond(int currentSecond) {
        this.currentSecond = currentSecond;
    }

    @Override
    public Message process(Message message) {
        if (currentSecond % 2 == 0){
            throw new IllegalStateException("Second must be odd");
        }
        return message;
    }
}