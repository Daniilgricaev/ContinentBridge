package com.orangemask.continentBridge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final int portForConnect = 8080;

    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost",portForConnect)){
            System.out.println("Client connected to chat.\n");
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);
            Thread reader = new Thread(() ->{
                try{
                    String line;
                    while((line = serverIn.readLine()) != null){
                        System.out.println("[CHAT]" + line);
                    }
                }catch(Exception e){
                    System.out.println("Disconnected from server");
                }
            });
            reader.start();
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String input;
            while((input = keyboard.readLine()) != null){
                serverOut.println(input);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
