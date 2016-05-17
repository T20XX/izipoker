package com.izipoker.interfaces;


public interface ServerInterface {
    public boolean join(ClientCallbackInterface c);
    public void tell(ClientCallbackInterface c, String message);
    public void tellAll(ClientCallbackInterface client, String message);
    public void leave(ClientCallbackInterface c);
}
