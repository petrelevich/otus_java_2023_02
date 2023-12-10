package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.homework.ProcessorChangeFields;
import ru.otus.processor.homework.ProcessorTimeException;
import ru.otus.processor.homework.SystemTimeProvider;

import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    public static void main(String[] args) {
        var processors = List.of(new ProcessorTimeException(new SystemTimeProvider()),
                new ProcessorChangeFields());

        var complexProcessor = new ComplexProcessor(processors, ex -> {});
        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(new ArrayList<>());


        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field5("field5")
                .field9("field9")
                .field13(objectForMessage)
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(historyListener);
    }
}
