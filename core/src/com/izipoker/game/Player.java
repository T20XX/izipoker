package com.izipoker.game;

/**
 * Created by Telmo on 26/04/2016.
 */
public abstract class Player {
    private int id;
    private String name;
    private int money;

    private boolean active;
    private boolean playing;

    private Hand hand;

    Player(int id, String name, int money) {
        this.id = id;
        this.name = name;
        this.money = money;
        hand = null;
        active = true;
        playing = false;
    }

    void bet(int n, Round r){
        //add to
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

}
