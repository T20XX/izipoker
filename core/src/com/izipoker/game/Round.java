package com.izipoker.game;

import com.izipoker.cardGame.Card;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Jorge on 03/05/2016.
 */
public class Round {
    public enum roundState{
        PREFLOP,
        FLOP,
        TURN,
        RIVER,
        SHOWDOWN,
    };
    private Map<Player, Integer> bets;
    private Card[] flop;
    private Card turn;
    private Card river;
    private int pot;
    private LinkedList<Player> currentPlayers;

    public Round(Player[] players, Player p){
        this.pot = 0;
        this.flop = null;
        this.turn = null;
        this.river = null;
        currentPlayers = new LinkedList<Player>();
        for(int i = 0; i< players.length; i++){
            if(players[i].isActive())
                currentPlayers.add(players[i]);
        }

        while(currentPlayers.peek() != p)
        {
            Player player = currentPlayers.removeFirst();
            currentPlayers.addLast(player);
        }
        Player player = currentPlayers.removeFirst();
        currentPlayers.addLast(player);
        bets = new HashMap<Player, Integer>();
        for(int i = 0; i < currentPlayers.size(); i++){
            bets.put(currentPlayers.get(i), 0);
        }
    }

    public boolean addBet(Player p,int ammount){
        if(currentPlayers.peek() == p) {
            if (bets.containsKey(p)) {
                ammount = ammount + bets.get(p);
                bets.put(p, ammount);
            }
            Player player = currentPlayers.removeFirst();
            currentPlayers.addLast(player);
            return true;
        }
        return false;
    }

    public void addToPot(){
        int ammount = 0;
        for(int i = 0; i < currentPlayers.size(); i++){
            ammount += bets.get(currentPlayers.get(i));
        }
        pot = ammount;
    }
    public LinkedList<Player> getCurrentPlayers() {
        return currentPlayers;
    }
}