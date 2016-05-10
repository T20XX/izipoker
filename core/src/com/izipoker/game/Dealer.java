package com.izipoker.game;

import com.izipoker.cardGame.Card;
import com.izipoker.cardGame.Deck;

import java.util.ArrayList;

/**
 * Created by Telmo on 26/04/2016.
 */
public class Dealer {
    private Table table;
    private Deck deck;

    /**
     * Creates a poker dealer
     * @param table Table where dealer is dealing
     */
    public Dealer(Table table){
        this.table = table;
    }

    public void createRound(){

        Round round =  new Round(table.getActivePlayers(), table.getJoker());
        table.addRounds(round);
        round.getFirstPlayer().bet(table.getSmallBlind(), round);
        round.getFirstPlayer().bet(table.getBigBlind(), round);

    }
    public void setNewJoker(){
        table.nextJoker();
    }

    public void giveHands(){
        for(int i = 0; i < table.getActivePlayers().length; i++){
            table.getActivePlayers()[i].setHand(Deck.getTopCard(), Deck.getTopCard());
        }
    }
    public void showFlop(){
        deck.getTopCard();
        ArrayList<Card> tempFlop = new ArrayList<Card>();
        deck.getTopCard().flip();
        tempFlop.add(deck.getTopCard());
        deck.getTopCard().flip();
        tempFlop.add(deck.getTopCard());
        deck.getTopCard().flip();
        tempFlop.add(deck.getTopCard());
        table.getTopRound().setFlop(tempFlop.toArray(new Card[tempFlop.size()]));
    }
    public void showTurn(){
        deck.getTopCard();
        deck.getTopCard().flip();
        table.getTopRound().setTurn(deck.getTopCard());
    }
    public void showRiver(){
        deck.getTopCard();
        deck.getTopCard().flip();
        table.getTopRound().setRiver(deck.getTopCard());
    }
}