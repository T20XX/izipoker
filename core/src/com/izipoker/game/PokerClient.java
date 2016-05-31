package com.izipoker.game;

import com.izipoker.cardGame.Card;
import com.izipoker.interfaces.ClientCallbackInterface;

/**
 * Created by Telmo on 24/05/2016.
 */
public class PokerClient implements ClientCallbackInterface {

    String name = "";
    int avatarID = 0;
    Hand hand = null;
    int money = 0;
    boolean changed = true;

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

    @Override
    public void receiveCard(Card card) {
        System.out.println(card);
    }

    public Hand getHand() {
        return hand;
    }
}
