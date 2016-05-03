package com.izipoker.game;

import com.izipoker.cardGame.Card;

import java.util.Map;

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
    Map<Player, Integer> bets;
    Card[] flop;
    Card turn;
    Card river;
    int pot;
    Player[] currentPlayers;


}
