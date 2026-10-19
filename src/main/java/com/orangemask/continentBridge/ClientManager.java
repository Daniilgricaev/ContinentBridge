package com.orangemask.continentBridge;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Set;

public class ClientManager {
    private static final Set<SocketChannel> clients = HashSet.newHashSet(2);
    public static void add(SocketChannel client){
        clients.add(client);
    }
    public static void remove(SocketChannel client){
        clients.remove(client);
    }
    public static void broadcast(SocketChannel sender, String message){
        for(SocketChannel iterator : clients){
            if(iterator == sender){
                continue;
            }
            try {
                iterator.write(ByteBuffer.wrap((message + "\n").getBytes()));
            }catch(Exception e){
                remove(iterator);
            }
        }
    }
}
