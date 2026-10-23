package com.orangemask.continentBridge;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class ClientManager {
    private static final List<String> messages = new CopyOnWriteArrayList<>();

    public static void addMessage(String message){
        messages.add(message);
    }

    public static List<String> getMessage() {
        return messages;
    }
}
