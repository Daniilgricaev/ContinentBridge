package com.orangemask.continentBridge;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;


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
        String[] lines = message.split("\r\n");
        if(lines.length > 0){
            String firstLine = lines[0];
            String[] method = firstLine.split(" ");
            if(method.length > 0){
                String res = method[0];
                System.out.println("Method :" + res);
                if(res.equals("GET")){
                    String html = "<!DOCTYPE html>\n" +
                            "<html lang=\"ru\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "    <title>Hello World</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <h1>Hello, World!</h1>\n" +
                            "</body>\n" +
                            "</html>";
                    String httpResponse = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/html; charset=UTF-8\r\n" +
                            "Content-Length: " + html.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                            "\r\n" +
                            html;
                    channel.write(ByteBuffer.wrap(httpResponse.getBytes()));
                }
            }
        }


    }
}
