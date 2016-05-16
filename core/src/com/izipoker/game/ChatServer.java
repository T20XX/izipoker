package com.izipoker.game;

import java.util.HashMap;

import lipermi.handler.CallHandler;
import lipermi.net.Server;

public class ChatServer implements ChatServerInterface {

	private HashMap<String, ChatClientCallbackInterface> clients = new HashMap<String, ChatClientCallbackInterface>();

	public ChatServer()  {
	}

	@Override
	public boolean join(String user, ChatClientCallbackInterface client)  {
		if (clients.containsKey(user))
			return false;
		clients.put(user, client);
		
		// tell other users that there is a new user
		notifyOthers(user, user + " joined");
		
		// tells this user who is logged in
		for (String name : clients.keySet())
			if (!name.equals(user))
				client.notify(name + " is logged in");
		return true;
	}

	@Override
	public void tell(String source, String message)  {
		notifyOthers(source, source + " said: " + message);
	}

	@Override
	public void leave(String name)  {
		clients.remove(name);
		notifyOthers(name, name + " leaved.");
	}

	private void notifyOthers(String source, String message){
		for (String name : clients.keySet())
			if (!name.equals(source))
				clients.get(name).notify(message);
	}

	public static void main(String args[]) {
		try {			
			ChatServer chatServer = new ChatServer();			
			CallHandler callHandler = new CallHandler();
			callHandler.registerGlobal(ChatServerInterface.class, chatServer);
			Server server = new Server();
			int thePortIWantToBind = 4455;
			server.bind(thePortIWantToBind, callHandler);
			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
