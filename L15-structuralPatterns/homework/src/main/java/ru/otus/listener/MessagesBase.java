package ru.otus.listener;

import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;

public class MessagesBase {
Map <Long, Message> messageBase = new HashMap<>();

public void putData (long id, Message message){
    if (messageBase.containsKey(id)){
        throw new RuntimeException("Данный ID уже существует в системе");
    } else {
        messageBase.put(id, message);
    }
}

public Map<Long,Message> getData (){
    return messageBase;
}
}
