package ru.otus.processor;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProcessorFailOnEvenSecondsTest {
    @Test
    void failExactlyOnEvenSecondsTest() {
        Processor processor = new ProcessorFailOnEvenSeconds(() -> 2);
        Message message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .build();

        Exception thrown = assertThrows(IllegalStateException.class, () -> processor.process(message));
        assertEquals("Seconds must be only odd integers!", thrown.getMessage());
    }

    @Test
    void doesNotFailOnOddSecondsTest() {
        Processor processor = new ProcessorFailOnEvenSeconds(() -> 1);
        Message message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .build();

        Message result = processor.process(message);
        assertEquals(message, result);
    }
}