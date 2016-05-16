package com.izipoker.game;



public interface ChatServerInterface  {
	public boolean join(String name, ChatClientCallbackInterface c);
	public void tell(String name, String message);
	public void leave(String name);
}
