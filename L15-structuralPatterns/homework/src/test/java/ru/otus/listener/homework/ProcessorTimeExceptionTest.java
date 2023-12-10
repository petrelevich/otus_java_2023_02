package ru.otus.listener.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.ProcessorTimeException;
import ru.otus.processor.homework.SystemTimeProvider;
import ru.otus.processor.homework.TimeProvider;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProcessorTimeExceptionTest {
    @Test
    void CheckTimeException () {
        TimeProvider timeProvider = mock (SystemTimeProvider.class);
        long testTime = 2;
        when(timeProvider.getCurrentTimeMillis()).thenReturn(testTime);
        Processor processor1 = new ProcessorTimeException(timeProvider);
        Message mockMessage = mock(Message.class);
        Assertions.assertThrows(Exception.class, () -> processor1.process(mockMessage));
    }
}
