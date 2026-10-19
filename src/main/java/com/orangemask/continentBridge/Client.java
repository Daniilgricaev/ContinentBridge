package com.orangemask.continentBridge;

import java.net.Socket;

public class Client {
    private static final int portForConnect = 8080;

    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost",portForConnect)){
            System.out.println("Client connected to socket.\n");
        }

    }
}
