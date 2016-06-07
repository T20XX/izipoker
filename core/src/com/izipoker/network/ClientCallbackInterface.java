package com.izipoker.network;

import com.izipoker.cardGame.Card;
import com.izipoker.game.Hand;

/**
 * Represent the callback methods that the server can call in a client
 */
public interface ClientCallbackInterface {
    /**
     * Notifies the client with a message
     *
     * @param message Message to be notified
     */
    void notify(String message);

    /**
     * Receive a hand and set it to its new hand
     *
     * @param hand Hand to be set
     */
    void receiveHand(Hand hand);

    /**
     * Receive a card
     *
     * @param card Card received
     */
    void receiveCard(Card card);

    /**
     * Receive possible actions
     *
     * @param possibleActions Array of 4 booleans, one for each action in the following order: FOLD, CHECK, CALL, RAISE
     */
    void receivePossibleActions(boolean possibleActions[]);

    /**
     * Receive money from server
     *
     * @param money Amount to be set on client side
     */
    void receiveMoney(int money);

    /**
     * Receive highest bet in round
     *
     * @param highestbet Highest bet in current round
     */
    void receiveHighestBet(int highestbet);

    /**
     * Ends the client by setting its state either WIN or LOSE
     *
     * @param win True if the player wo and false if lost
     */
    void setEndState(boolean win);

}
