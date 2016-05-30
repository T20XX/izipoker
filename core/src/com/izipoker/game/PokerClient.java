package com.izipoker.game;

import com.izipoker.interfaces.ClientCallbackInterface;


public class PokerClient implements ClientCallbackInterface {
    private Hand hand;

    @Override
    public void notify(String message) {
        System.out.println(message);
    }

    @Override
    public void setHand(Hand h) {
         this.hand = h;
    }

    public Hand getHand(){
        return hand;
    }
}
