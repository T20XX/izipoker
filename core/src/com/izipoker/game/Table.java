package com.izipoker.game;

import java.util.ArrayList;

/**
 * Created by Jorge on 03/05/2016.
 */
public class Table {

    private final int MAX_PLAYER;
    private Player[] seats;
    //private Card[] cardsOnTable;
    private ArrayList<Round> rounds;
    private Dealer dealer;

    /**
     * Creates a table given number of players
     * @param maxPlayers Maximum number of players
     */
    public Table(int maxPlayers){
        MAX_PLAYER = maxPlayers;
        seats = new Player[maxPlayers];
        dealer = new Dealer(this);
    }

    /**
     * Adds a player to the table if there is an empty seat
     * @param p Player to be added to table
     * @return True if the player is added successfully or false if the table is full
     */
    public boolean addPlayer(Player p){
        for(int i = 0; i < seats .length; i++){
            if (seats[i] != null){
                seats[i] = p;
                return true;
            }
        }
        return false;
    }

    public boolean removePlayer(Player p){
        for(int i = 0; i < seats .length; i++){
            if (seats[i] == p){
                seats[i] = null;
                return true;
            }
        }
        return false;
    }
}
