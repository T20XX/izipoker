package com.izipoker.game;

import com.izipoker.interfaces.ClientCallbackInterface;

/**
 * Created by Telmo on 24/05/2016.
 */
public class PokerClient implements ClientCallbackInterface {

    Hand hand;

    @Override
    public void notify(String message) {
        System.out.println(message);
    }

    @Override
    public void receiveHand(Hand hand) {
        System.out.println(hand.getCards()[0]);
        System.out.println(hand.getCards()[1]);
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }
}
