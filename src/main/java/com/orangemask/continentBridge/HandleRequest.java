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
            key.cancel();
            channel.close();
            return;
        }
        buf.flip();
        String request = new String(buf.array(), 0, byteRead);
        System.out.println("Request : " + request);
        String response = "HTTP/1.1 200 OK\r\nContent-Length: 13\r\n\r\nHello";
        ByteBuffer respBuf = ByteBuffer.wrap(response.getBytes());
        channel.write(respBuf);
        channel.close();
    }
}
