package ru.otus.listener.homework;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class HistoryListenerTest {

    @Test
     //надо удалить
    void listenerTest() {
        //given
        var historyListener = new HistoryListener();

        var id = 100L;
        var data = "33";
        var field13 = new ObjectForMessage();
        var field13Data = new ArrayList<String>();
        field13Data.add(data);
        field13.setData(field13Data);

        var message = new Message.Builder(id, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13)
                .field10("field10")
               .field13(field13)
                .build();

        //when
        historyListener.onUpdated(message);
       message.getField13().setData(new ArrayList<>()); //меняем исходное сообщение
        field13Data.clear(); //меняем исходный список

        //then
        var messageFromHistory = historyListener.findMessageById(id);
        assertThat(messageFromHistory).isPresent();
       assertThat(messageFromHistory.get().getField13().getData()).containsExactly(data);
    }
}