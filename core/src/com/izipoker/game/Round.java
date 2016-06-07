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
    public enum roundState {
        PREFLOP,
        FLOP,
        TURN,
        RIVER,
        SHOWDOWN,
    }

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
    private Player smallBlinder;
    private Player bigBlinder;

    /**
     *
     * @param players players on the round
     * @param p small Blinder on the round
     */
    public Round(Player[] players, Player p) {
        this.pot = 0;
        this.flop = null;
        this.turn = null;
        this.river = null;
        this.highestBet = 0;
        this.highestPlayer = null;
        this.joker = p;
        currentPlayers = new LinkedList<Player>();
        for (int i = 0; i < players.length; i++) {
            currentPlayers.add(players[i]);
        }

        while (currentPlayers.peek() != p) {
            Player player = currentPlayers.removeFirst();
            currentPlayers.addLast(player);
        }
        /*Player player = currentPlayers.removeFirst();
        currentPlayers.addLast(player);*/
        bets = new HashMap<Player, Integer>();
        for (int i = 0; i < currentPlayers.size(); i++) {
            bets.put(currentPlayers.get(i), 0);
        }
        smallBlinder = joker;
        bigBlinder = currentPlayers.get(1);
    }

    /**
     *
     * @param p Player to add the call
     * @return True if the player can call
     */
    public boolean addCall(Player p) {
        return addBet(p, highestBet - bets.get(p));
    }

    /**
     *
     * @param p Player to add the bet
     * @param amount int of the bet
     * @return True if the player can make the bet
     */
    public boolean addBet(Player p, int amount) {
        if (currentPlayers.peek() == p) {
            if (bets.containsKey(p)) {
                bets.put(p, bets.get(p) + amount);
            }
            if (bets.get(p) > highestBet) {
                highestBet = bets.get(p);
                highestPlayer = p;
            }
            Player player = currentPlayers.removeFirst();
            currentPlayers.addLast(player);
            p.setMoney(p.getMoney() - amount);
            pot += amount;
            return true;
        }
        return false;
    }


    /**
     * Updates round state to the next state
     */
    public void updateState() {
        this.state = roundState.values()[roundState.valueOf(this.state.toString()).ordinal() + 1];
        highestBet = 0;
        for (int i = 0; i < currentPlayers.size(); i++) {
            bets.put(currentPlayers.get(i), 0);
        }
        highestPlayer = joker;
        while (currentPlayers.peek() != joker) {
            Player player = currentPlayers.removeFirst();
            currentPlayers.addLast(player);
        }
    }

    /**
     *
     * @param p Player to fold the round
     * @return True if the player exists and it is his time to play
     */
    public boolean foldPlayer(Player p) {
        if (currentPlayers.peek() == p) {
            currentPlayers.remove(0);
            if (p == joker) {
                joker = currentPlayers.peek();
                highestPlayer = currentPlayers.peek();
            }
            return true;
        }
        return false;
    }

    /**
     *
     * @return current Players on the round
     */
    public LinkedList<Player> getCurrentPlayers() {
        return currentPlayers;
    }

    /**
     *
     * @return First player of the round
     */
    public Player getFirstPlayer() {
        return currentPlayers.peek();
    }

    /**
     *
     * @return Array of card of the flop
     */
    public Card[] getFlop() {
        return flop;
    }

    /**
     *
     * @param flop set new Array of Card of flop
     */
    public void setFlop(Card[] flop) {
        this.flop = flop;
    }

    /**
     *
     * @return highest bet on the round
     */
    public int getHighestBet() {
        return highestBet;
    }

    /**
     *
     * @return player that has the highest bet
     */
    public Player getHighestPlayer() {
        return highestPlayer;
    }

    /**
     *
     * @return actual joker of the round
     */
    public Player getJoker() {
        return joker;
    }

    /**
     *
     * @return total pot of the round
     */
    public int getPot() {
        return pot;
    }

    /**
     *
     * @return Card river
     */
    public Card getRiver() {
        return river;
    }

    /**
     *
     * @param river new Card of river
     */
    public void setRiver(Card river) {
        this.river = river;
    }

    /**
     *
     * @return roundState with the state of the round
     */
    public roundState getState() {
        return state;
    }

    /**
     *
     * @param state new roundState of the round
     */
    public void setState(roundState state) {
        this.state = state;
    }

    /**
     *
     * @return new Card of turn
     */
    public Card getTurn() {
        return turn;
    }

    /**
     *
     * @param turn set new Card of turn
     */
    public void setTurn(Card turn) {
        this.turn = turn;
    }

    /**
     *
     * @return Map of Player and Integer, corresponding to the current Players and their bet
     */
    public Map<Player,Integer> getBets() {
        return bets;
    }

    /**
     *
     * @return Player that is the small blinder
     */
    public Player getSmallBlinder(){
        return smallBlinder;
    }

    /**
     *
     * @return Player that is the big blinder
     */
    public Player getBigBlinder(){
        return bigBlinder;
    }
}