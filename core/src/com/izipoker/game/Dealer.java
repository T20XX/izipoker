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
            //Round round = new Round(table.getActivePlayers(), table.getJoker());
            Round round = new Round(table.getActivePlayers(), table.getActivePlayers()[0]);
            table.addRounds(round);
            //round.getFirstPlayer().bet(table.getSmallBlind(), round);
            //round.getFirstPlayer().bet(table.getBigBlind(), round);
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
    public void showRiver() {
        deck.getTopCard();
        deck.getTopCard().flip();
        table.getTopRound().setRiver(deck.getTopCard());
    }

    @Override
    public void run() {
        table.setState(Table.tableState.PLAYING);
        System.out.println("Vou enviar as cartas!");
        while (table.getActivePlayers().length > 1){
            createRound();
            Round r = table.getTopRound();
            giveHands();
            for(Player p:table.getActivePlayers()) {
                System.out.println(p.getHand().getCards()[0]);
                System.out.println(p.getHand().getCards()[1]);
                table.sendHand(p.getName());
                table.sendMoney(p.getName());
            }
            if(r.getCurrentPlayers().size() == 1)
                return;
            for(Player p:r.getCurrentPlayers()){
                table.sendPossibleActions(p.getName(), new boolean[]{true, false, true, false});
                Thread t = new Thread(new CheckPlayerAction(p));
                /*Timer timer = new Timer(table.getPlayingTime(), new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        t.stop();
                    }
                });*/
                t.start();
                try {
                    t.join(table.getPlayingTime()*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(t.isAlive()){
                    t.interrupt();
                }
                System.out.println("Passaram "+ table.getPlayingTime() + " segundos.");
            }

            showFlop();

            if(r.getCurrentPlayers().size() == 1)
                return;


            for(Player p:r.getCurrentPlayers()){

            }

            showTurn();

            if(r.getCurrentPlayers().size() == 1) {
                return;
            }

            for(Player p:r.getCurrentPlayers()){

            }

            showRiver();

            if(r.getCurrentPlayers().size() == 1)
                return;
            for(Player p:r.getCurrentPlayers()){

            }

            return;
        }

        table.setState(Table.tableState.CLOSED);
    }
}