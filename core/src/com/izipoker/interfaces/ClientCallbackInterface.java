package com.izipoker.interfaces;

import com.izipoker.cardGame.Card;
import com.izipoker.game.Hand;

public interface ClientCallbackInterface  {
    public void notify(String message);
    public void receiveHand(Hand hand);
    public void receiveCard(Card card);
    public void receivePossibleActions(boolean possibleActions[]);
}
