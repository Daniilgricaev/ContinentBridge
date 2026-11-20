package com.orangemask.continentBridge;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PollingManager {
    private Map<SocketChannel,Long> waiters = new ConcurrentHashMap<>();
    public void waitMessage(SocketChannel channel, long lastID){
        if(Messages.actualID() > lastID){
            String json = "{\"hasNew\": true}";
            String httpSend ="HTTP/1.1 200 OK\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: " + json.toString().getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                    "\r\n" + json;
            try{
                channel.write(ByteBuffer.wrap(httpSend.getBytes(StandardCharsets.UTF_8)));
                channel.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            waiters.put(channel, lastID);
        }
    }
    public void notifyMessage(Message newMessage){
        try {
            Iterator<Map.Entry<SocketChannel,Long>>mapIterator = waiters.entrySet().iterator();
            while (mapIterator.hasNext()){
                Map.Entry<SocketChannel,Long>entry = mapIterator.next();
                if(entry.getValue() < newMessage.getId()){
                    String json = "{\"hasNew\": true}";
                    String httpResponse ="HTTP/1.1 200 OK\r\n" +
                            "Content-Type: application/json\r\n" +
                            "Content-Length: " + json.toString().getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                            "\r\n" + json;
                    entry.getKey().write(ByteBuffer.wrap(httpResponse.getBytes(StandardCharsets.UTF_8)));
                    mapIterator.remove();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

}
