package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private Map<Long, Message> messages;

    public HistoryListener() {
        messages = new HashMap<>();
    }

    @Override
    public void onUpdated(Message msg) {
        var v = msg.toBuilder().build();
        messages.put(v.getId(), v);

    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(messages.getOrDefault(id, null));
    }
}
