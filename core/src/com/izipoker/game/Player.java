package com.izipoker.game;

/**
 * Created by Telmo on 26/04/2016.
 */
public abstract class Player {
    int id;
    String name;

    Player(int id, String name) {
        this.id = id;
        this.name = name;
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


}
