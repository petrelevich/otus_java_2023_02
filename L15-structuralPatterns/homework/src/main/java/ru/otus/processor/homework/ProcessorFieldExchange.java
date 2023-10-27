package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorFieldExchange implements Processor {
    @Override
    public Message process(Message message) {
        var old11 = message.getField11();
        var old12 = message.getField11();
        return message.toBuilder().field11(old12).field12(old11).build();
    }
}
