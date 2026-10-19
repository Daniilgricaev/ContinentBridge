package com.orangemask.continentBridge;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class HandleRequest {
    public static void handleRequest(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        int byteRead = channel.read(buf);

        if(byteRead == -1){
            ClientManager.remove(channel);
            key.cancel();
            channel.close();
            System.out.println("Client disconnected ");
            return;
        }
        buf.flip();
        String message = new String(buf.array(),0,byteRead).trim();
        System.out.println(message);
        ClientManager.broadcast(channel,message);
    }
}
