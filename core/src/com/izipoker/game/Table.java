package com.izipoker.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.izipoker.cardGame.Card;
import com.izipoker.graphics.TexturesLoad;
import com.izipoker.network.ClientCallbackInterface;
import com.izipoker.network.ServerInterface;

import java.awt.Point;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


public class Table extends Actor implements ServerInterface {
    /**
     * Represents the multiples states in a poker table
     */
    public enum tableState {
        /**
         * Accept Players to join the table
         */
        LOBBY,
        /**
         * Poker Game is going
         */
        PLAYING,
        /**
         * Poker Game has ended
         */
        CLOSED
    }

    private final int MAX_PLAYER;
    private final int initMoney; //MUDAR PARA CONSTRUCTOR
    private final int playingTime; //MUDAR PARA CONSTRUCTOR
    private ArrayList<Player> seats;
    private String name;
    private ArrayList<Round> rounds;
    private Dealer dealer;
    private Player joker;
    private int SMALL_BLIND;
    private tableState state = tableState.LOBBY;
    private ArrayList<String> chatHistory = new ArrayList<String>();
    private Point[] position;

    //clients map to players
    private HashMap<String, ClientCallbackInterface> clients = new HashMap<String, ClientCallbackInterface>();
    private HashMap<String, Player> players = new HashMap<String, Player>();

    /**
     * Creates a table given number of players
     * @param maxPlayers Maximum number of players
     */
    public Table(String name, int maxPlayers, int timeAFK, int startMoney) {
        MAX_PLAYER = maxPlayers;
        seats = new ArrayList<Player>();
        dealer = new Dealer(this);
        rounds = new ArrayList<Round>();
        this.name = name;
        initMoney = startMoney;
        playingTime = timeAFK;
        SMALL_BLIND = startMoney*3/100;
    }

    /**
     * Adds a player to the table if there is an empty seat
     * @param p Player to be added to table
     * @return True if the player is added successfully or false if the table is full
     */
    public boolean addPlayer(Player p) {
        if (seats.size() < MAX_PLAYER) {
            seats.add(p);
            if (joker == null)
                joker = p;
            return true;
        }
        return false;
    }

    /**
     * remove player from the table
     * @param p Player to be removed from table
     * @return True if the player is removed successfully or false if the player p does not not exists.
     */
    public boolean removePlayer(Player p) {
        return seats.remove(p);
    }

    /**
     *  get next joker
     * @return next joker on table
     */
    public Player nextJoker() {
        int i;
        for (i = 0; i < seats.size(); i++) {
            if (seats.get(i) == joker) {
                break;
            }
        }
        i = (i + 1) % seats.size();
        while (!seats.get(i).isActive()) {
            i = (i + 1) % seats.size();
        }
        joker = seats.get(i);
        return joker;
    }

    /**
     * add new round to the table
     * @param r Round to add on table
     */
    public void addRounds(Round r) {
        rounds.add(r);
    }

    /**
    *{@inheritDoc}
    */
     @Override
    public boolean join(String name, int avatarID, ClientCallbackInterface client) {
        if (clients.containsKey(name))
            return false;

        Player p = new Human(0, name, initMoney, avatarID);
        if (addPlayer(p)) {
            // tells this user is logged in
            clients.put(name, client);
            players.put(name, p);
            client.notify("Congratulations " + name + ", you have joined the table " + this.name);
            return true;

        }
        return false;
    }


    /**
     *{@inheritDoc}
     */
    @Override
    public void tell(String name, String message) {
        System.out.println(name + ": " + message);
        chatHistory.add("(" + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ") " +
                name + ": " +
                message);
    }


    /**
     *{@inheritDoc}
     */
    @Override
    public void leave(String name, boolean win) {
        clients.get(name).setEndState(win);
        clients.remove(name);
        players.remove(name);
    }


    /**
     *{@inheritDoc}
     */
    @Override
    public void sendHand(String name) {
        (clients.get(name)).receiveHand(players.get(name).getHand());
    }


    /**
     *{@inheritDoc}
     */
    @Override
    public void sendCard(String name) {
        (clients.get(name)).receiveCard(new Card(10, Card.suitType.CLUBS));
    }


    /**
     *{@inheritDoc}
     */
    @Override
    public void sendPossibleActions(String name, boolean[] possibleActions) {
        (clients.get(name)).receivePossibleActions(possibleActions);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void sendMoney(String name) {
        (clients.get(name)).receiveMoney(players.get(name).getMoney());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void sendPokerAction(String name, PokerAction action) {
        players.get(name).setLastAction(action);
        players.get(name).setActed(true);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void sendHighestBet(String name) {
        (clients.get(name)).receiveHighestBet(getTopRound().getHighestBet() - getTopRound().getBets().get(players.get(name)));
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean isLobbyState() {
        return (state == tableState.LOBBY);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.setColor(1, 1, 1, 1);

        batch.draw(TexturesLoad.tableTex, super.getX() + super.getWidth()/ 24, super.getY()+ super.getHeight()/16, 22*super.getWidth()/ 24, 14*super.getHeight()/16);
        for (int i = 0; i < seats.size(); i++) {
            //if (seats.get(i) != null) {
                if (seats.get(i).isActive()) {
                    seats.get(i).setSize(super.getWidth() / 8, super.getHeight() / 6);
                    if (i < 4) {
                        seats.get(i).setPosition((i+1) * (super.getWidth() / 5), super.getHeight(), Align.top);
                        if(!rounds.isEmpty()) {
                            TexturesLoad.font.draw(batch, getTopRound().getBets().get(seats.get(i)) + "", (i+1) * (super.getWidth() / 5), 13 * super.getHeight() / 16);
                            if(seats.get(i) == getTopRound().getSmallBlinder())
                                TexturesLoad.font.draw(batch, "SB",(i+1) * (super.getWidth() / 5), 6 * super.getHeight() / 8);
                            if(seats.get(i) == getTopRound().getBigBlinder())
                                TexturesLoad.font.draw(batch, "BB", (i+1) * (super.getWidth() / 5), 6 * super.getHeight() / 8);
                        }
                    } else if (i >= 4) {
                        seats.get(i).setPosition(super.getWidth() - ((i % 4)+1) *   super.getWidth() / 5, 0, Align.bottom);
                        if(!rounds.isEmpty()) {
                            TexturesLoad.font.draw(batch, getTopRound().getBets().get(seats.get(i)) + "", super.getWidth() - ((i % 4)+1) *   super.getWidth() / 5, 3 * super.getHeight() / 16);
                            if(seats.get(i) == getTopRound().getSmallBlinder())
                                TexturesLoad.font.draw(batch, "SB", super.getWidth() - ((i % 4)+1) *   super.getWidth() / 5, 2 * super.getHeight() / 8);
                            if(seats.get(i) == getTopRound().getBigBlinder())
                                TexturesLoad.font.draw(batch, "BB", super.getWidth() - ((i % 4)+1) *   super.getWidth() / 5, 2 * super.getHeight() / 8);
                        }
                    }
                    seats.get(i).draw(batch, parentAlpha);
                   if(!rounds.isEmpty()) {
                       if(getTopRound().getCurrentPlayers().contains(seats.get(i))){
                           if (seats.get(i).getHand() != null) {
                               seats.get(i).getHand().getCards()[0].setSize(super.getWidth() / 16, super.getHeight() / 12);
                               seats.get(i).getHand().getCards()[1].setSize(super.getWidth() / 16, super.getHeight() / 12);
                               seats.get(i).getHand().getCards()[0].setPosition(seats.get(i).getX() + seats.get(i).getWidth(), seats.get(i).getY(), Align.bottomLeft);
                               seats.get(i).getHand().getCards()[1].setPosition(seats.get(i).getX() + seats.get(i).getWidth(), seats.get(i).getY() + seats.get(i).getHand().getCards()[0].getHeight(), Align.bottomLeft);
                               seats.get(i).getHand().getCards()[0].draw(batch, parentAlpha);
                               seats.get(i).getHand().getCards()[1].draw(batch, parentAlpha);
                           }
                       }
                        if (seats.get(i) == getTopRound().getCurrentPlayers().peek()) {
                            batch.end();
                            TexturesLoad.selectRectangle.begin(ShapeRenderer.ShapeType.Line);
                            TexturesLoad.selectRectangle.rect(seats.get(i).getX(), seats.get(i).getY(), seats.get(i).getWidth(), seats.get(i).getHeight());
                            TexturesLoad.selectRectangle.end();
                            batch.begin();
                        }
                    }


                }
            //}
        }

        if (!rounds.isEmpty()) {
            if (getTopRound().getFlop() != null)  {
                getTopRound().getFlop()[0].setSize(super.getWidth()/10, super.getHeight()/6);
                getTopRound().getFlop()[0].setPosition(2*super.getWidth()/8, super.getHeight() / 2, Align.center);
                getTopRound().getFlop()[0].draw(batch, parentAlpha);
                getTopRound().getFlop()[1].setSize(super.getWidth()/10, super.getHeight()/6);
                getTopRound().getFlop()[1].setPosition(3*super.getWidth()/8, super.getHeight() / 2, Align.center);
                getTopRound().getFlop()[1].draw(batch, parentAlpha);
                getTopRound().getFlop()[2].setSize(super.getWidth() / 10, super.getHeight() / 6);
                getTopRound().getFlop()[2].setPosition(4 * super.getWidth() / 8, super.getHeight() / 2, Align.center);
                getTopRound().getFlop()[2].draw(batch, parentAlpha);
            }

            if (getTopRound().getTurn() != null) {
                getTopRound().getTurn().setSize(super.getWidth()/10, super.getHeight()/6);
                getTopRound().getTurn().setPosition(5*super.getWidth()/8, super.getHeight() / 2, Align.center);
                getTopRound().getTurn().draw(batch, parentAlpha);
            }

            if (getTopRound().getRiver() != null) {
                getTopRound().getRiver().setSize(super.getWidth() / 10, super.getHeight() / 6);
                getTopRound().getRiver().setPosition(6 * super.getWidth() / 8, super.getHeight() / 2, Align.center);
                getTopRound().getRiver().draw(batch, parentAlpha);
            }
            TexturesLoad.font.draw(batch, getTopRound().getPot()+"", super.getWidth()/2, super.getHeight()/3);

        }

    }

    /**
     *{@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * set new name to the table
     * @param name new name of the table
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get active players
     * @return Array of Players that are playing
     */
    public Player[] getActivePlayers() {
        ArrayList<Player> activePlayers = new ArrayList<Player>();
        for (int i = 0; i < seats.size(); i++) {
            if (seats.get(i) != null) {
                if (seats.get(i).isActive())
                    activePlayers.add(seats.get(i));
            }
        }
        return activePlayers.toArray(new Player[activePlayers.size()]);
    }

    /**
     * get big blind amount
     * @return big bling, two times the small blind
     */
    public int getBigBlind() {
        return 2 * SMALL_BLIND;

    }

    /**
     * get chat history of the table
     * @return ArrayList of String with chat history
     */
    public ArrayList<String> getChatHistory() {
        return chatHistory;
    }

    /**
     * get dealer
     * @return Dealer of the table
     */
    public Dealer getDealer() {
        return dealer;
    }

    /**
     * get joker
     * @return actual joker of the table
     */
    public Player getJoker() {
        return joker;
    }

    /**
     * get playing time of players
     * @return Playing Time of each player on the table
     */
    public int getPlayingTime() {
        return playingTime;
    }

    /**
     * get rounds from the table
     * @return ArrayLisy of rounds on the table
     */
    public ArrayList<Round> getRounds() {
        return rounds;
    }

    /**
     * get seats on the table
     * @return Array of Player with the seats of the table
     */
    public Player[] getSeats() {
        return seats.toArray(new Player[seats.size()]);
    }

    /**
     * get small blind
     * @return small blind on the table
     */
    public int getSmallBlind() {
        return SMALL_BLIND;
    }

    /**
     * get top round
     * @return most recent round of the table
     */
    public Round getTopRound() {
        return rounds.get(rounds.size() - 1);
    }

    /**
     * set new state
     * @param state new state of the table
     */
    public void setState(tableState state) {
        this.state = state;
    }

}
