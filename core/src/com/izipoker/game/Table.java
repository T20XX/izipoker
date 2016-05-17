package com.izipoker.game;

import com.izipoker.interfaces.ClientCallbackInterface;
import com.izipoker.interfaces.ServerInterface;

import java.util.ArrayList;

import lipermi.handler.CallHandler;
import lipermi.net.Server;

/**
 * Created by Jorge on 03/05/2016.
 */
public class Table implements ServerInterface {

    private String name;
    private final int MAX_PLAYER;
    private Player[] seats;
    //private Card[] cardsOnTable;
    private ArrayList<Round> rounds;
    private Dealer dealer;
    private Player joker;
    private int SMALL_BLIND = 30;

    /**
     * Creates a table given number of players
     * @param maxPlayers Maximum number of players
     */
    public Table(String name, int maxPlayers){
        MAX_PLAYER = maxPlayers;
        seats = new Player[maxPlayers];
        dealer = new Dealer(this);
        rounds = new ArrayList<Round>();
        this.name = name;
    }

    /**
     * Adds a player to the table if there is an empty seat
     * @param p Player to be added to table
     * @return True if the player is added successfully or false if the table is full
     */
    public boolean addPlayer(Player p){
        for(int i = 0; i < seats .length; i++){
            if (seats[i] == null){
                seats[i] = p;
                if(joker == null)
                    joker = p;
                return true;
            }
        }
        return false;
    }

    public boolean removePlayer(Player p) {
        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == p) {
                seats[i] = null;
                return true;
            }
        }
        return false;
    }

    public Player nextJoker(){
        int j, k = 0;
        for(int i = 0; i < seats.length; i++){
            if(seats[i] == joker){
                if(i == seats.length-1)
                    j = 0;
                else j = i+1;
                while(k != seats.length){
                    if(i == seats.length-1)
                        j = 0;
                    if(seats[j].isActive()) {
                        joker = seats[j];
                        return joker;
                    }
                    k++;
                    j++;
                }
            }
        }
        return null;
    }

    public Player[] getSeats(){
        return seats;
    }
    public void addRounds(Round r){
        rounds.add(r);
    }

    public Player getJoker() {
        return joker;
    }

    public int getSmallBlind() {
        return SMALL_BLIND;
    }

    public int getBigBlind() {
        return 2 * SMALL_BLIND;
    }

    public Player[] getActivePlayers(){
        ArrayList<Player> activePlayers = new ArrayList<Player>();
        for(int i = 0; i< seats.length; i++){
            if(seats[i] != null) {
                if (seats[i].isActive())
                    activePlayers.add(seats[i]);
            }
        }
        return activePlayers.toArray(new Player[activePlayers.size()]);
    }

    public Round getTopRound(){
        return rounds.get(rounds.size()-1);
    }
    public Dealer getDealer() {
        return dealer;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }


    /*public String getName() {
        return name;
    }*/

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean join(ClientCallbackInterface client)  {
       // ((Player)client).notify("You have entered");
        //System.out.println("Entrou o " + client.getName());
        client.notify("parabens, juntou-se");
       // return addPlayer((Player)client);
    return true;
        // tell other users that there is a new user
        //notifyOthers(user, user + " joined");

        // tells this user who is logged in
        /*for (String name : clients.keySet())
            if (!name.equals(user))
                client.notify(name + " is logged in");*/
    }

    @Override
    public void tell(ClientCallbackInterface client, String message)  {
        notifyOthers((Player)client, ((Player) client).getName() + ": " + message);
    }

    @Override
    public void tellAll(ClientCallbackInterface client, String message)  {
        notifyOthers((Player)client, ((Player) client).getName() + " : " + message);
    }

    @Override
    public void leave(ClientCallbackInterface client)  {
        removePlayer((Player)client);
    }

    @Override
    public String getName() {
        return name;
    }

    private void notifyOthers(ClientCallbackInterface client, String message){
        for (Player p:seats){
            p.notify(message);
        }
    }

    private void notifyAll(ClientCallbackInterface client, String message){
        for (Player p:seats){
            p.notify(message);
        }
    }
}