package com.orangemask.continentBridge;

import java.nio.channels.SocketChannel;

public class Message {
    private String message;
    private SocketChannel sender;
    private Long id;

    public Message(){}
    public Message(Long id, String message, SocketChannel sender){
        this.id = id;
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public SocketChannel getSender() {
        return sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSender(SocketChannel sender) {
        this.sender = sender;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
}
