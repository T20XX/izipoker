package com.izipoker.game;

import com.izipoker.cardGame.Card;
import com.izipoker.network.ClientCallbackInterface;

public class PokerClient implements ClientCallbackInterface {
    private String name = "";
    private int avatarID = 0;
    private Hand hand = null;
    private int money;
    private boolean changed = true;
    private boolean possibleActions[] = {false, false, false, false};

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


    public Hand getHand() {
        return hand;
    }

    public boolean[] getPossibleActions() {
        return possibleActions;
    }

    public void setPossibleActions(boolean[] possibleActions) {
        this.possibleActions = possibleActions;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

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
