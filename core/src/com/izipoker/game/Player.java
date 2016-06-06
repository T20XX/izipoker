package com.izipoker.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.izipoker.graphics.TexturesLoad;

public abstract class Player extends Actor {
    private int id;
    private String name;
    private int money;
    private int avatarID;
    private boolean active;
    private boolean playing;

    private Hand hand;
    private PokerAction lastAction;

    private boolean acted = false;

    /**
     * Player constructor
     *
     * @param id    Player ID
     * @param name  Player Name
     * @param money Player Money
     */
    Player(int id, String name, int money) {
        this.id = id;
        this.name = name;
        this.money = money;
        hand = null;
        active = true;
        playing = false;
        this.avatarID = 0;
    }

    /**
     * Player constructor (sets difficulty to MEDIUM(default))
     *
     * @param id       Player ID
     * @param name     Player Name
     * @param money    Player Money
     * @param avatarID Player Avatar ID
     */
    Player(int id, String name, int money, int avatarID) {
        this.id = id;
        this.name = name;
        this.money = money;
        hand = null;
        active = true;
        playing = false;
        this.avatarID = avatarID;
    }

    /**
     * {@inheritDoc}
     * Draws the player according to its orientation
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.isActive()) {

            TexturesLoad.font.draw(batch, name, super.getX(), super.getY());
            TexturesLoad.font.draw(batch, money + "", super.getX(), super.getY() - 10);
            batch.draw(TexturesLoad.avatarTex[0][avatarID], super.getX(), super.getY(), super.getWidth(), super.getHeight());
        }

    }

    /**
     * @return Player name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name new name of player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return True if player has acted
     */
    public boolean hasActed() {
        return acted;
    }

    /**
     * @return hand of player
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * @param hand new hand of player
     */
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    /**
     * @return ID of player
     */
    public int getId() {
        return id;
    }

    /**
     * @param id new ID of player
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return last action of the player
     */
    public PokerAction getLastAction() {
        return lastAction;
    }

    /**
     * @param lastAction check last action
     */
    public void setLastAction(PokerAction lastAction) {
        this.lastAction = lastAction;
    }

    /**
     * @return money of Player
     */
    public int getMoney() {
        return money;
    }

    /**
     * @param money new Money of player
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * @return True if Player is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active set if player is active or not
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return True if player is playing
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * @param playing set True if player is playing
     */
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    /**
     * @param acted set True is player acted
     */
    public void setActed(boolean acted) {
        this.acted = acted;
    }

}
