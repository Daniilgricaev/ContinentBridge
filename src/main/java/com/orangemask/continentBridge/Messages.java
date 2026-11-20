package com.orangemask.continentBridge;

import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;

public class Messages {
    private static final Queue<Message>messages = new ConcurrentLinkedDeque<>();
    private static final AtomicLong id = new AtomicLong(0);
    private static Message userMessage;
    private static long messageID;
    public static void setMessages(SocketChannel sender, String message){
        messageID = id.incrementAndGet();
        messages.add(new Message(messageID,message,sender));
    }
    public static long actualID(){
        return userMessage.getId();
    }
    public static int queueSize(){
        return messages.size();
    }
    public static Queue<Message> getMessages() {
        return messages;
    }
}