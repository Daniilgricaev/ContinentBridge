package com.orangemask.continentBridge;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.output.StringOutput;
import gg.jte.resolve.DirectoryCodeResolver;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


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
                    DirectoryCodeResolver codeResolver = new DirectoryCodeResolver(Paths.get("src/main/resources/jte"));
                    TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);

                    Map<String , Object>stringObjectMap = new HashMap<>();

                    StringOutput output = new StringOutput();
                    stringObjectMap.put(message, ClientManager.getUSER());
                    templateEngine.render("index.jte",stringObjectMap,output);
                    String httpGET = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/html; charset=UTF-8\r\n" +
                            "Content-Length: " + output.toString().getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                            "\r\n" +
                            output;
                    channel.write(ByteBuffer.wrap(httpGET.getBytes(StandardCharsets.UTF_8)));
                }
                if(res.equals("POST")){
                    int index = message.indexOf("\r\n\r\n");
                    if(index != -1){
                        String body = message.substring(index + 4);
                        String start= "";
                        for(int i = 0;i <= 7;i++){
                            char a = body.charAt(i);
                            start += a;
                        }
                        if(start.equals("message=")){
                            String cleanMessage = body.substring(8);
                            ClientManager.addUSER(channel,cleanMessage);
                        }
                    }

                    String httpPOST = "HTTP/1.1 200 OK\r\n" +
                            "Location: /\r\n" +
                            "Content-Length: 0\r\n" +
                            "\r\n";
                    channel.write(ByteBuffer.wrap(httpPOST.getBytes(StandardCharsets.UTF_8)));
                }
            }
        }


    }
}
