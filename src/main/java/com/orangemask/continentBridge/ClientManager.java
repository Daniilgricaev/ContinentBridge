package com.orangemask.continentBridge;

import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;


public class ClientManager {
    private static final Queue<SocketChannel> clients = new ConcurrentLinkedDeque<>();

    public static void remove(SocketChannel channel){
        clients.remove(channel);
    }

    public static void addUSER(SocketChannel client) {

        clients.add(client);
    }
    public static Queue<SocketChannel>getUSER() {
        return clients;
    }
}
