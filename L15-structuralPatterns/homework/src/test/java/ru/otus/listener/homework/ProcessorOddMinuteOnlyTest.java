package ru.otus.listener.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.ProcessorOddMinuteOnly;
import ru.otus.processor.homework.TimeProvider;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.when;

public class ProcessorOddMinuteOnlyTest {
    private ProcessorOddMinuteOnly processor;
    private TimeProvider tp;

    @BeforeEach
    public void init() {
        tp = org.mockito.Mockito.mock(TimeProvider.class);
        processor = new ProcessorOddMinuteOnly(tp);
    }

    @Test
    public void simpleExceptionSuccess() {
        var message = new Message.Builder(1L).field7("field7").build();
        when(tp.get()).thenReturn(
                Instant.ofEpochSecond(2));
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> processor.process(message));
    }

    @Test
    public void simpleNoExceptionSuccess() {
        var message = new Message.Builder(1L).field7("field7").build();
        when(tp.get()).thenReturn(
                Instant.ofEpochSecond(1));
        assertThatNoException().isThrownBy(() -> processor.process(message));
        ;
    }
}
