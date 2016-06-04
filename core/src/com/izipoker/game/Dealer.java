package com.izipoker.game;

import com.izipoker.cardGame.Card;
import com.izipoker.cardGame.Deck;

import java.util.ArrayList;

/**
 * Represents the dealer of a poker game, person who deal cards, add bets from the players to the pot and check winner
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

    /**
     * Creates a new round and add it to the table he is dealing
     * @return True if there are at least two players in the table, and false otherwise
     */
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

    /**
     * Sets the new player to start playing in next round, finds next starting player
     */
    public void setNewJoker(){
        table.nextJoker();
    }

    /**
     * Give two cards to all the players in the table
     */
    public void giveHands(){
        Player[] players_in_round =
                table.getTopRound().getCurrentPlayers().toArray(new Player[table.getTopRound().getCurrentPlayers().size()]);
        for(int i = 0; i < players_in_round.length; i++){
            players_in_round[i].setHand(new Hand(deck.getTopCard(), deck.getTopCard()));
        }
    }

    /**
     * Sets the flop (first three cards) in the current round
     */
    public void showFlop(){
        deck.getTopCard();
        ArrayList<Card> tempFlop = new ArrayList<Card>();
        for(int i= 0; i < 3; i++) {
            Card temp = deck.getTopCard();
            temp.flip();
            tempFlop.add(temp);
        }
        table.getTopRound().setFlop(tempFlop.toArray(new Card[tempFlop.size()]));
    }

    /**
     * Sets the turn (fourth card) in the current round
     */
    public void showTurn(){
        deck.getTopCard();
        deck.getTopCard().flip();
        table.getTopRound().setTurn(deck.getTopCard());
    }

    /**
     * Sets the river (fifth card) in the current round
     */
    public void showRiver() {
        deck.getTopCard();
        deck.getTopCard().flip();
        table.getTopRound().setRiver(deck.getTopCard());
    }

    /**
     * Plays the game while there are sufficient players(two)
     */
    @Override
    public void run() {
        table.setState(Table.tableState.PLAYING);
        System.out.println("Vou enviar as cartas!");
        while (table.getActivePlayers().length > 1){
            Round r = table.getTopRound();

            createRound();
            deck.shuffle(1);
            giveHands();

            for(Player p:table.getActivePlayers()) {
                System.out.println(p.getHand().getCards()[0]);
                System.out.println(p.getHand().getCards()[1]);
                table.sendHand(p.getName());
                table.sendMoney(p.getName());
            }

            handleTableActions();

            if(r.getCurrentPlayers().size() == 1){
                r.updateState();
                showFlop();
                handleTableActions();

                if(r.getCurrentPlayers().size() == 1){
                    r.updateState();
                    showTurn();
                    handleTableActions();

                    if(r.getCurrentPlayers().size() == 1) {
                        r.updateState();
                        showRiver();
                        handleTableActions();

                        if(r.getCurrentPlayers().size() == 1) {
                            r.updateState();

                            //check hands!
                        }
                    }
                }
            }
            setNewJoker();
        }
        table.setState(Table.tableState.CLOSED);
    }

    /**
     * Handle all actions from the table between table cards showing
     * Waits the time specified by table
     * If during this time player acts action is handled
     * If not the player is considered folded
     */
    private void handleTableActions(){
        Round r = table.getTopRound();
        for(Player p:r.getCurrentPlayers()){
            table.sendPossibleActions(p.getName(), new boolean[]{true, false, true, false});
            Thread t = new Thread(new CheckPlayerAction(p));
            t.start();
            try {
                t.join(table.getPlayingTime()*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(t.isAlive()){
                t.interrupt();
            }
            handlePlayerAction(p);
        }
    }

    /**
     * Handles player action
     * @param p Player to act
     */
    private void handlePlayerAction(Player p){
        Round r = table.getTopRound();
        if(!p.hasActed()){
            //r.removePlayer();
        } else {
            switch(p.getLastAction().getType()){
                case FOLD:
                    //r.removePlayer();
                    break;
                case CHECK:
                    //r.removePlayer();
                    r.addBet(p,0);
                    break;
                case CALL:
                    //r.addBet(p,r.highestBet);
                    break;
                case RAISE:
                    r.addBet(p,p.getLastAction().getAmount());
            }
            p.setActed(false);
        }
    }
}