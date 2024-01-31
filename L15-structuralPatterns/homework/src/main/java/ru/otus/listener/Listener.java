package ru.otus.listener;

import ru.otus.model.Message;

public interface Listener {

    void onUpdated(Message msg);

}
