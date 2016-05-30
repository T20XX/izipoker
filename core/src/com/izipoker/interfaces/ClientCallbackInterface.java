package com.izipoker.interfaces;

import com.izipoker.game.Hand;

public interface ClientCallbackInterface  {
    public void notify(String message);
    public void setHand(Hand h);
}
