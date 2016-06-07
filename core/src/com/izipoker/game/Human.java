package com.izipoker.game;

/**
 * Represents a human controlled poker player
 */
public class Human extends Player {

    /**
     * {@inheritDoc}
     */
    public Human(int id, String name, int money) {
        super(id, name, money);
    }

    /**
     * {@inheritDoc}
     */
    public Human(int id, String name, int money, int avatarID) {
        super(id, name, money, avatarID);
    }
}
