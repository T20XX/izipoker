package com.izipoker.network;

import com.izipoker.cardGame.Card;
import com.izipoker.game.Hand;

/**
 * Represents a poker client with all local variables to store server info
 */
public class PokerClient implements ClientCallbackInterface {
    private String name = "";
    private int avatarID = 0;
    private Hand hand = null;
    private int money;
    private boolean changed = true;
    private boolean possibleActions[] = {false, false, false, false};

    /**
     * Client constructor
     * @param name Name of the client
     * @param avatarID Number of the avatar
     */
    public PokerClient(String name, int avatarID) {
        this.name = name;
        this.avatarID = avatarID;
    }

    @Override
    public void notify(String message) {
        System.out.println(message);
    }

    @Override
    public void receiveHand(Hand hand) {
        System.out.println(hand.getCards()[0]);
        System.out.println(hand.getCards()[1]);
        this.hand = hand;
        this.changed = true;
    }

    @Override
    public void receiveCard(Card card) {
        System.out.println(card);
    }

    @Override
    public void receivePossibleActions(boolean[] possibleActions) {
        this.possibleActions = possibleActions;
        this.changed = true;
    }

    @Override
    public void receiveMoney(int money) {
        this.money = money;
        this.changed = true;
    }

    /**
     * Gets hand
     * @return Hand with two cards
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Gets array of booleans indicating possible actions
     * @return
     */
    public boolean[] getPossibleActions() {
        return possibleActions;
    }

    /**
     * Gets changed state
     * @return True if something changed, false otherwise
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * Sets client to changed state
     * @param changed Boolean to set changed state
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    /**
     * Gets name
     * @return Name of the player (client side)
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of the player (client side)
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public int getAvatarID() {
        return avatarID;
    }

    public void setAvatarID(int avatarID) {
        this.avatarID = avatarID;
    }

    public int getMoney() {
        return money;
    }
}
