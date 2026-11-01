package com.orangemask.continentBridge;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Messages {
    private static final Queue<String>messages = new ConcurrentLinkedDeque<>();
    public Queue<String>getMessages(){
        return messages;
    }
    public void setMessages(String message){
        messages.add(message);
    }
    public void delMessage(String message){
        messages.remove(message);
    }
}
