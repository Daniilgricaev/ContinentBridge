package com.orangemask.continentBridge;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

import static com.orangemask.continentBridge.Accept.accept;
import static com.orangemask.continentBridge.HandleRequest.handleRequest;

public class ServerSocket {
    private static final int PORT = 8080;
    public static void main(String[] args)throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(PORT));
        serverSocket.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server started on port " + PORT);
        while(true){
            selector.select();
            for(SelectionKey key : selector.selectedKeys()){
                if (key.isAcceptable()) accept(selector, serverSocket);
                if (key.isReadable()) handleRequest(key);
            }
            selector.selectedKeys().clear();
        }
    }
}
