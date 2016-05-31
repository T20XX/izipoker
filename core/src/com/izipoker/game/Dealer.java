package com.izipoker.game;

import com.izipoker.cardGame.Card;
import com.izipoker.cardGame.Deck;

import java.util.ArrayList;

/**
 * Created by Telmo on 26/04/2016.
 */
public class Dealer implements Runnable{
    private Table table;
    private Deck deck;

    /**
     * Creates a poker dealer
     * @param table Table where dealer is dealing
     */
    public Dealer(Table table){

        this.table = table;
        this.deck = new Deck();
    }

    public boolean createRound(){
        if(table.getActivePlayers().length >= 2) {
            Round round = new Round(table.getActivePlayers(), table.getJoker());
            table.addRounds(round);
            round.getFirstPlayer().bet(table.getSmallBlind(), round);
            round.getFirstPlayer().bet(table.getBigBlind(), round);
            return true;
        }
        else return false;
    }
    public void setNewJoker(){
        table.nextJoker();
    }

    public void giveHands(){
        Player[] players_in_round =
                table.getTopRound().getCurrentPlayers().toArray(new Player[table.getTopRound().getCurrentPlayers().size()]);
        for(int i = 0; i < players_in_round.length; i++){
            players_in_round[i].setHand(new Hand(deck.getTopCard(), deck.getTopCard()));
        }
    }
    public void showFlop(){
        deck.getTopCard();
        ArrayList<Card> tempFlop = new ArrayList<Card>();
        Card temp =  deck.getTopCard();
        temp.flip();
        tempFlop.add(temp);
        temp =  deck.getTopCard();
        temp.flip();
        tempFlop.add(temp);
        temp =  deck.getTopCard();
        temp.flip();
        tempFlop.add(temp);
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

    @Override
    public void run() {
        System.out.println("Vou enviar as cartas!");
        while (table.getActivePlayers().length > 1){
            createRound();
            giveHands();
            for(Player p:table.getActivePlayers()) {
                System.out.println(p.getHand().getCards()[0]);
                System.out.println(p.getHand().getCards()[1]);
                table.sendHand(p.getName());
            }
            return;
        }
        System.out.println("Algo deu merda!");
    }
}