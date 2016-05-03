package com.izipoker.game;

/**
 * Created by Jorge on 03/05/2016.
 */
public class Round {
    public enum roundState{
        PRE-FLOP,
        FLOP,
        TURN,
        RIVER,
        SHOWOFF,
    }
    Map<Player, Integer> bets;
    Card[] flop;
    Card turn;
    Card river;
    int pot;
    Player[] currentPlayers;


}
