package com.orangemask.continentBridge;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


public class ClientManager {
    private static final Map<SocketChannel,String >clients = HashMap.newHashMap(2);

    public static void remove(SocketChannel channel){
        clients.remove(channel);
    }

    public static void addUSER(SocketChannel client, String message) {
        clients.put(client, message);
    }
    public static Map<SocketChannel , String> getUSER() {
        return clients;
    }
}
