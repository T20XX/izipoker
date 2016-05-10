package com.izipoker.game;

/**
 * Created by Telmo on 26/04/2016.
 */
public class Dealer {
    private Table table;

    /**
     * Creates a poker dealer
     * @param table Table where dealer is dealing
     */
    public Dealer(Table table){
        this.table = table;
    }

    public void createRound(){
        Round round =  new Round(table.getSeats(), table.getJoker());
        table.addRounds(round);
        Player player = round.getCurrentPlayers().removeFirst();
        player.bet(table.getSmallBlind(), round);
        round.getCurrentPlayers().addLast(player);
        player = round.getCurrentPlayers().removeFirst();
        player.bet(table.getBigBlind(), round);
        round.getCurrentPlayers().addLast(player);
    }

    public void setNewJoker(){
        table.nextJoker();
    }
}