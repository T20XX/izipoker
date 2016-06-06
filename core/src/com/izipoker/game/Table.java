package com.izipoker.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.izipoker.cardGame.Card;
import com.izipoker.network.ClientCallbackInterface;
import com.izipoker.network.ServerInterface;

import java.awt.Point;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


public class Table extends Actor implements ServerInterface {

    public enum tableState {
        LOBBY,
        PLAYING,
        CLOSED
    }

    private static Texture tableTex = new Texture("table.png");

    private String name;
    private final int MAX_PLAYER;
    private Player[] seats;
    //private Card[] cardsOnTable;
    private ArrayList<Round> rounds;
    private Dealer dealer;
    private Player joker;
    private int SMALL_BLIND = 30;
    private final int initMoney = 1000; //MUDAR PARA CONSTRUCTOR
    private final int playingTime = 30; //MUDAR PARA CONSTRUCTOR
    private tableState state = tableState.LOBBY;
    private ArrayList<String> chatHistory = new ArrayList<String>();
    private Point[] position;

    //clients map to players
    private HashMap<String, ClientCallbackInterface> clients = new HashMap<String, ClientCallbackInterface>();
    private HashMap<String, Player> players = new HashMap<String, Player>();

    /**
     * Creates a table given number of players
     *
     * @param maxPlayers Maximum number of players
     */
    public Table(String name, int maxPlayers) {
        MAX_PLAYER = maxPlayers;
        seats = new Player[maxPlayers];
        dealer = new Dealer(this);
        rounds = new ArrayList<Round>();
        this.name = name;
        //position[0].setLocation(100,100);

    }

    /**
     * Adds a player to the table if there is an empty seat
     *
     * @param p Player to be added to table
     * @return True if the player is added successfully or false if the table is full
     */
    public boolean addPlayer(Player p) {
        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == null) {
                seats[i] = p;
                if (joker == null)
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

    public Player nextJoker() {
        /*int j, k = 0;
        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == joker) {
                if (i == seats.length - 1)
                    j = 0;
                else j = i + 1;
                while (k != seats.length) {
                    if (i == seats.length - 1)
                        j = 0;
                    if (seats[j].isActive()) {
                        joker = seats[j];
                        return joker;
                    }
                    k++;
                    j++;
                }
            }
        }
        return null;*/
        int i;
        for (i = 0; i < seats.length; i++) {
            if (seats[i] == joker) {
                break;
            }
        }
        i = (i+1)%seats.length;
        while(!seats[i].isActive()){
            i = (i+1)%seats.length;
        }
        joker = seats[i];
        return joker;
    }

    public Player[] getSeats() {
        return seats;
    }

    public void addRounds(Round r) {
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

    public Player[] getActivePlayers() {
        ArrayList<Player> activePlayers = new ArrayList<Player>();
        for (int i = 0; i < seats.length; i++) {
            if (seats[i] != null) {
                if (seats[i].isActive())
                    activePlayers.add(seats[i]);
            }
        }
        return activePlayers.toArray(new Player[activePlayers.size()]);
    }

    public Round getTopRound() {
        return rounds.get(rounds.size() - 1);
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
    public boolean join(String name, int avatarID, ClientCallbackInterface client) {
        if (clients.containsKey(name))
            return false;

        Player p = new Human(0, name, initMoney,avatarID);
        if (addPlayer(p)) {
            // tells this user is logged in
            clients.put(name, client);
            players.put(name, p);
            client.notify("Congratulations " + name + ", you have joined the table " + this.name);
            return true;

        }
        return false;
    }

    @Override
    public void tell(String name, String message) {
        //notifyOthers((Player)client, ((Player) client).getName() + ": " + message);
        System.out.println(name + ": " + message);
        chatHistory.add("(" + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ") " +
                name + ": " +
                message);
    }

    @Override
    public void tellAll(ClientCallbackInterface client, String message) {
        //notifyOthers((Player)client, ((Player) client).getName() + " : " + message);
    }

    @Override
    public void leave(ClientCallbackInterface client) {
        removePlayer((Player) client);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void sendHand(String name) {
        //System.out.println(name);
        //System.out.println(players.get(name).getHand().getCards()[0]);
        //System.out.println(players.get(name).getHand().getCards()[1]);
        //(clients.get(name)).notify(players.get(name).getHand().getCards()[1].toString());
        (clients.get(name)).receiveHand(players.get(name).getHand());
    }

    @Override
    public void sendCard(String name) {
        (clients.get(name)).receiveCard(new Card(10, Card.suitType.CLUBS));
    }

    @Override
    public boolean isLobbyState() {
        return (state == tableState.LOBBY);
    }

    @Override
    public void sendPossibleActions(String name, boolean[] possibleActions) {
        (clients.get(name)).receivePossibleActions(possibleActions);
    }

    private void notifyOthers(ClientCallbackInterface client, String message) {
        /*for (Player p:seats){
            p.notify(message);
        }*/
    }

    private void notifyAll(ClientCallbackInterface client, String message) {
        /*for (Player p:seats){
            p.notify(message);
        }*/
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.setColor(1, 1, 1, 1);
        batch.draw(tableTex, super.getX(), super.getY(), super.getWidth(), super.getHeight());
        for (int i = 0; i < seats.length; i++) {
            if (seats[i] != null)
                if (seats[i].isActive()) {
                    if(i < 4)
                    seats[i].setPosition(i%4*super.getWidth()/4,4*super.getHeight()/6);
                    else if(i == 4)
                        seats[i].setPosition(4*super.getWidth()/4,4*super.getHeight()/6);
                    else if(i >4 )
                        seats[i].setPosition(i%4*super.getWidth()/4,2*super.getHeight()/6);
                    seats[i].draw(batch, parentAlpha);
                }
        }
        BitmapFont font = new BitmapFont();
        if (!rounds.isEmpty()) {
            if (getTopRound().getFlop() != null) {
                getTopRound().getFlop()[0].setBounds(2*super.getWidth() / 7, super.getHeight() / 2, 100, 100);
                getTopRound().getFlop()[0].draw(batch, parentAlpha);
                getTopRound().getFlop()[1].setBounds(3 * super.getWidth() / 7, super.getHeight() / 2,100,100);
                getTopRound().getFlop()[1].draw(batch, parentAlpha);
                getTopRound().getFlop()[2].setBounds(4 * super.getWidth() / 7, super.getHeight() / 2, 100, 100);
                getTopRound().getFlop()[2].draw(batch, parentAlpha);
            }

            if (getTopRound().getTurn() != null) {
                getTopRound().getTurn().setBounds(5 * super.getWidth() / 7, super.getHeight() / 2, 100, 100);
                getTopRound().getTurn().draw(batch, parentAlpha);
            }

            if (getTopRound().getRiver() != null) {
                getTopRound().getRiver().setBounds(6 * super.getWidth() / 7, super.getHeight() / 2,100,100);
                getTopRound().getRiver().draw(batch, parentAlpha);
            }
            font.setColor(1.0f, 1.0f, 1.0f,1.0f);
            font.draw(batch,getTopRound().getPot()+"", super.getX()+100, super.getY()+100);

        }

    }

    public ArrayList<String> getChatHistory() {
        return chatHistory;
    }


    public void setState(tableState state) {
        this.state = state;
    }


    public int getPlayingTime() {
        return playingTime;
    }


    @Override
    public void sendMoney(String name) {
        (clients.get(name)).receiveMoney(players.get(name).getMoney());
    }

    @Override
    public void sendPokerAction(String name, PokerAction action) {
        players.get(name).setLastAction(action);
        players.get(name).setActed(true);
    }

    @Override
    public void sendHighestBet(String name) {
        (clients.get(name)).receiveHighestBet(getTopRound().getHighestBet());
    }
}
