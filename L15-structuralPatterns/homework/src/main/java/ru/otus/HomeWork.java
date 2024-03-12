package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.processor.*;

import java.time.LocalDateTime;
import java.util.List;

public class HomeWork {
    public static void main(String[] args) {
        var processors = List.of(new ProcessorFailsOnEvenSecond(LocalDateTime.now().getSecond()),
                new ProcessorSwapFields());

        var complexProcessor = new ComplexProcessor(processors, System.out::println);
        var historylistner = new HistoryListener();
        complexProcessor.addListener(historylistner);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(historylistner);
    }
}
