package com.izipoker.game;

import java.io.Serializable;
import java.util.Scanner;

import lipermi.handler.CallHandler;
import lipermi.net.Client;



public class ChatClient implements ChatClientCallbackInterface {

		
	public void notify(String message) {
		System.out.println(message);
	}
	
	
    public static void main(String[] args) {
        try {
        	// get proxy for remote chat server
        	CallHandler callHandler = new CallHandler();
        	String remoteHost = "localhost";
        	int portWasBinded = 4455;
        	Client client = new Client(remoteHost, portWasBinded, callHandler);
            ChatServerInterface proxy = (ChatServerInterface)client.getGlobal(ChatServerInterface.class);
            
            // create and expose remote listener
            ChatClient listener = new ChatClient();
            callHandler.exportObject(ChatClientCallbackInterface.class, listener);

            
            // now do conversation
            Scanner scanner = new Scanner(System.in);
            System.out.print("What's your nickname? ");
            String name = scanner.nextLine();
             if ( ! proxy.join(name, listener) ) {
            	System.out.println("Sorry, nickname is already in use.");
            	return;
            }
            String message = scanner.nextLine();
            while(! message.equals("exit")) {
            	if (!message.equals(""))
            			proxy.tell(name, message);
            	message = scanner.nextLine();
            }
        	proxy.leave(name);
        	System.exit(0);        	
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}