package com.izipoker.interfaces;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface ServerInterface {
    public boolean join(String name, ClientCallbackInterface client);
    public void tell(String name, String message);
    public void tellAll(ClientCallbackInterface client, String message);
    public void leave(ClientCallbackInterface c);
    public String getName();
}
