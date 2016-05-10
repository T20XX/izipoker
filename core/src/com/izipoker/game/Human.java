package com.izipoker.game;

/**
 * Created by Telmo on 26/04/2016.
 */
public class Human extends Player{

    /**
     * Human constructor (sets difficulty to MEDIUM(default))
     * @param id    Human ID
     * @param name  Human Name
     */
    public Human(int id, String name, int money){
        super(id,name, money);
    }
}
