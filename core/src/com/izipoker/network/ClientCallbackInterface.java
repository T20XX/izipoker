package com.izipoker.network;

import com.izipoker.cardGame.Card;
import com.izipoker.game.Hand;

public interface ClientCallbackInterface {
    void notify(String message);

    void receiveHand(Hand hand);

    void receiveCard(Card card);

    void receivePossibleActions(boolean possibleActions[]);

    void receiveMoney(int money);

    void receiveHighestBet(int highestbet);

}
