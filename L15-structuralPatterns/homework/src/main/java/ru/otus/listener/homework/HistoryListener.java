package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    private final Map<Long, Message> messageMap = new  HashMap<>();
    @Override
    public void onUpdated(Message msg) {
        messageMap.put(msg.getId(), msg.toBuilder().build());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryListener that = (HistoryListener) o;
        return Objects.equals(messageMap, that.messageMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageMap);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(messageMap.get(id));
    }
}
