package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.listener.MessagesBase;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    MessagesBase messagesBase = new MessagesBase();

    private Message copy (Message msg){
        ObjectForMessage objectForMessage = new ObjectForMessage();
        List<String> listCopy = new ArrayList<>(msg.getField13().getData());
        objectForMessage.setData(listCopy);
        Message messageCopy = new Message.Builder(msg.getId())
                .field1(msg.getField1())
                .field2(msg.getField2())
                .field3(msg.getField3())
                .field4(msg.getField4())
                .field5(msg.getField5())
                .field6(msg.getField6())
                .field7(msg.getField7())
                .field8(msg.getField8())
                .field9(msg.getField9())
                .field10(msg.getField10())
                .field11(msg.getField11())
                .field12(msg.getField12())
                .field13(objectForMessage)
                .build();
        return messageCopy;
    }
    @Override
    public void onUpdated(Message msg) {
        messagesBase.putData(msg.getId(), copy(msg));
    }

    @Override
    public Optional<Message> findMessageById(long id) {
            return Optional.of(messagesBase.getData().get(id));
    }
}
