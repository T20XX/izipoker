package com.izipoker.game;

import com.izipoker.cardGame.Card;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Represents a round in a poker game
 */
public class Round {

    /**
     * Represents the multiples states in a poker round
     */
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
    private roundState state = roundState.PREFLOP;
    private int highestBet;
    private Player highestPlayer;
    private Player joker;


    public Round(Player[] players, Player p){
        this.pot = 0;
        this.flop = null;
        this.turn = null;
        this.river = null;
        this.highestBet = 0;
        this.highestPlayer = null;
        this.joker = p;
        currentPlayers = new LinkedList<Player>();
        for(int i = 0; i< players.length; i++){
                currentPlayers.add(players[i]);
        }

        while(currentPlayers.peek() != p)
        {
            Player player = currentPlayers.removeFirst();
            currentPlayers.addLast(player);
        }
        /*Player player = currentPlayers.removeFirst();
        currentPlayers.addLast(player);*/
        bets = new HashMap<Player, Integer>();
        for(int i = 0; i < currentPlayers.size(); i++){
            bets.put(currentPlayers.get(i), 0);
        }
    }

    public boolean addCall(Player p){
        return addBet(p, highestBet-bets.get(p));
    }

    public boolean addBet(Player p,int amount){
        if(currentPlayers.peek() == p) {
            if (bets.containsKey(p)) {
                bets.put(p, bets.get(p) + amount);
            }
            if(amount > highestBet) {
                highestBet = amount;
                highestPlayer = p;
            }
            Player player = currentPlayers.removeFirst();
            currentPlayers.addLast(player);
            p.setMoney(p.getMoney()-amount);
            pot += amount;
            return true;
        }
        return false;
    }

    public void addToPot(){
        int total = 0;
        for(int bet:bets.values()){
            total += bet;
        }
        pot = total;
    }
    public LinkedList<Player> getCurrentPlayers() {
        return currentPlayers;
    }
    public Player getFirstPlayer(){
        return currentPlayers.peek();
    }
    public void setRiver(Card river) {
        this.river = river;
    }

    public void setTurn(Card turn) {
        this.turn = turn;
    }

    public void setFlop(Card[] flop) {
        this.flop = flop;
    }
    public roundState getState() {
        return state;
    }
    public void setState(roundState state) {
        this.state = state;
    }
    /**
     * Updates round state to the next state
     */
    public void updateState(){
        this.state = roundState.values()[roundState.valueOf(this.state.toString()).ordinal() + 1];
        highestBet = 0;
        for(int i = 0; i < currentPlayers.size();i++){
            bets.put(currentPlayers.get(i),0);
        }
        highestPlayer = joker;
        while(currentPlayers.peek() != joker)
        {
            Player player = currentPlayers.removeFirst();
            currentPlayers.addLast(player);
        }
        addToPot();
    }

    public Card[] getFlop() {
        return flop;
    }

    public Card getTurn() {
        return turn;
    }

    public Card getRiver() {
        return river;
    }

    public int getHighestBet() {
        return highestBet;
    }


    public Player getJoker() {
        return joker;
    }

    public boolean foldPlayer(Player p){
        if(currentPlayers.peek() == p){
            currentPlayers.remove(0);
            if (p == joker){
                joker = currentPlayers.peek();
            }
            return true;
        }
        return false;
    }
    public int getPot(){
        return pot;
    }


    public Player getHighestPlayer() {
        return highestPlayer;
    }
}