package com.izipoker.game;

import com.izipoker.cardGame.Card;
import com.izipoker.cardGame.Deck;

import java.util.ArrayList;

import javafx.util.Pair;

/**
 * Represents the dealer of a poker game, person who deal cards, add bets from the players to the pot and check winner
 */
public class Dealer implements Runnable {
    private Table table;
    private Deck deck;

    /**
     * Creates a poker dealer
     *
     * @param table Table where dealer is dealing
     */
    public Dealer(Table table) {

        this.table = table;
        this.deck = new Deck();
    }

    /**
     * Creates a new round and add it to the table he is dealing
     *
     * @return True if there are at least two players in the table, and false otherwise
     */
    public boolean createRound() {
        if (table.getActivePlayers().length >= 2) {
            Round round = new Round(table.getActivePlayers(), table.getJoker());
            //Round round = new Round(table.getActivePlayers(), table.getActivePlayers()[0]);
            table.addRounds(round);
            round.addBet(round.getFirstPlayer(), table.getSmallBlind());
            round.addBet(round.getFirstPlayer(), table.getBigBlind());
            return true;
        } else return false;
    }

    /**
     * Sets the new player to start playing in next round, finds next starting player
     */
    public void setNewJoker() {
        table.nextJoker();
    }

    /**
     * Give two cards to all the players in the table
     */
    public void giveHands() {
        Player[] players_in_round =
                table.getTopRound().getCurrentPlayers().toArray(new Player[table.getTopRound().getCurrentPlayers().size()]);
        for (int i = 0; i < players_in_round.length; i++) {
            players_in_round[i].setHand(new Hand(deck.getTopCard(), deck.getTopCard()));
        }
    }

    /**
     * Sets the flop (first three cards) in the current round
     */
    public void showFlop() {
        ArrayList<Card> tempFlop = new ArrayList<Card>();
        for (int i = 0; i < 3; i++) {
            Card temp = deck.getTopCard();
            temp.setFlipped(true);
            tempFlop.add(temp);
        }
        table.getTopRound().setFlop(tempFlop.toArray(new Card[tempFlop.size()]));
    }

    /**
     * Sets the turn (fourth card) in the current round
     */
    public void showTurn() {
        Card temp = deck.getTopCard();
        temp.setFlipped(true);
        table.getTopRound().setTurn(temp);
    }

    /**
     * Sets the river (fifth card) in the current round
     */
    public void showRiver() {
        Card temp = deck.getTopCard();
        temp.setFlipped(true);
        table.getTopRound().setRiver(temp);
    }

    /**
     * Plays the game while there are sufficient players(two)
     */
    @Override
    public void run() {
        table.setState(Table.tableState.PLAYING);
        Round r;

        while (table.getActivePlayers().length > 1) {
            createRound();
            r = table.getTopRound();

            deck.shuffle(1);
            giveHands();

            for (Player p : table.getActivePlayers()) {
                // System.out.println(p.getHand().getCards()[0]);
                // System.out.println(p.getHand().getCards()[1]);
                table.sendHand(p.getName());
                table.sendMoney(p.getName());
            }

            System.out.println("Joker: " + table.getJoker().getName());


            System.out.println("PRE-FLOP");
            handleTableActions();
            System.out.println(r.getPot());
            System.out.println("SAIMOS PRE-FLOP");

            if (r.getCurrentPlayers().size() != 1) {
                System.out.println("FLOP");
                r.updateState();
                showFlop();
                handleTableActions();
                System.out.println(r.getPot());
                System.out.println("SAIMOS FLOP");

                if (r.getCurrentPlayers().size() != 1) {
                    System.out.println("TURN");
                    r.updateState();
                    showTurn();
                    handleTableActions();
                    System.out.println(r.getPot());
                    System.out.println("SAIMOS TURN");

                    if (r.getCurrentPlayers().size() != 1) {
                        System.out.println("RIVER");
                        r.updateState();
                        showRiver();
                        handleTableActions();
                        System.out.println(r.getPot());
                        System.out.println("SAIMOS RIVER");
                        if (r.getCurrentPlayers().size() != 1) {
                            r.updateState();
                            //check hands!
                        }
                    }
                }
            }


            ArrayList<Player> winners = new ArrayList<Player>(){};
            Player tempwinner = r.getCurrentPlayers().get(0);
            winners.add(tempwinner);
            Pair<Hand.handRank, Card.rankType> winnerHandRank = null;
            if(r.getCurrentPlayers().size() > 1) {
                Card[] cardsOnTable = new Card[5];
                System.arraycopy(r.getFlop(), 0, cardsOnTable, 0, 3);
                cardsOnTable[3] = r.getTurn();
                cardsOnTable[4] = r.getRiver();
                winnerHandRank = tempwinner.getHand().checkHandRank(cardsOnTable);
                for(int i = 1; i < r.getCurrentPlayers().size(); i++){
                    Pair<Hand.handRank, Card.rankType> tempHandRank = r.getCurrentPlayers().get(i).getHand().checkHandRank(cardsOnTable);
                    if (Hand.handRank.valueOf(tempHandRank.getKey().toString()).ordinal() > Hand.handRank.valueOf(winnerHandRank.getKey().toString()).ordinal()) {
                        winners.clear();
                        tempwinner = r.getCurrentPlayers().get(i);
                        winnerHandRank = tempHandRank;
                        winners.add(tempwinner);
                    } else if (Hand.handRank.valueOf(tempHandRank.getKey().toString()).ordinal() == Hand.handRank.valueOf(winnerHandRank.getKey().toString()).ordinal()) {
                        //same handrank will test high card of rank
                        if (Card.rankType.valueOf(tempHandRank.getValue().toString()).ordinal() > Card.rankType.valueOf(winnerHandRank.getValue().toString()).ordinal()) {
                            winners.clear();
                            tempwinner = r.getCurrentPlayers().get(i);
                            winnerHandRank = tempHandRank;
                            winners.add(tempwinner);
                        } else if (Card.rankType.valueOf(tempHandRank.getValue().toString()).ordinal() == Card.rankType.valueOf(winnerHandRank.getValue().toString()).ordinal()){
                            winners.add(r.getCurrentPlayers().get(i));
                        }
                    }
                }
            }
            for(int i = 0; i < winners.size();i++) {
                winners.get(i).setMoney(winners.get(i).getMoney() + (r.getPot() / winners.size()));
                if(r.getCurrentPlayers().size() > 1) {
                    table.tell("Dealer", winners.get(i).getName() + " won " + (r.getPot() / winners.size()) + " with a " + winnerHandRank.toString());
                } else {
                    table.tell("Dealer", winners.get(i).getName() + " won " + (r.getPot()));
                }
            }
            for(Player p: r.getCurrentPlayers()){
                p.getHand().getCards()[0].setFlipped(true);
                p.getHand().getCards()[1].setFlipped(true);
            }
            
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            removeLoserPlayers();
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
    private void handleTableActions() {
        Round r = table.getTopRound();
        Player p;
        boolean atLeastOnePlayed = false;
        while ((r.getCurrentPlayers().peek() != r.getHighestPlayer() || !atLeastOnePlayed) && r.getCurrentPlayers().size() > 1) {
            p = r.getCurrentPlayers().peek();
            System.out.println(p.getName() + " Turn");
            if(p.getMoney()> 0) {
                table.sendHighestBet(p.getName());
                table.sendPossibleActions(p.getName(), checkPossibleActions(p));
                CheckPlayerAction checker = new CheckPlayerAction(p);
                Thread t = new Thread(checker);
                t.start();
                try {
                    t.join(table.getPlayingTime() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (t.isAlive()) {
                    table.sendPossibleActions(p.getName(), new boolean[]{false, false, false, false});
                    checker.kill();
                }
                handlePlayerAction(p);
            }
            atLeastOnePlayed = true;
        }
    }

    /**
     * Handles player action
     *
     * @param p Player to act
     */
    private void handlePlayerAction(Player p) {
        Round r = table.getTopRound();
        if (!p.hasActed()) {
            r.foldPlayer(p);
            table.tell("Dealer", p.getName() + " Folded by timeout");
        } else {
            switch (p.getLastAction().getType()) {
                case FOLD:
                    r.foldPlayer(p);
                    table.tell("Dealer", p.getName() + " folded");
                    break;
                case CHECK:
                    r.addBet(p, 0);
                    table.tell("Dealer", p.getName() + " checked");
                    break;
                case CALL:
                    r.addCall(p);
                    table.sendMoney(p.getName());
                    table.tell("Dealer", p.getName() + " ralled");
                    break;
                case RAISE:
                    r.addBet(p, p.getLastAction().getAmount());
                    table.sendMoney(p.getName());
                    table.tell("Dealer",p.getName() + " raised " + p.getLastAction().getAmount());
                    break;
            }
            p.setActed(false);
        }
    }

    private boolean[] checkPossibleActions(Player p) {
        boolean[] possibleActions = new boolean[]{true, false, false, false};
        Round r = table.getTopRound();

        if (r.getHighestBet() == 0) {
            possibleActions[1] = true;
            possibleActions[3] = true;
        } else if (p.getMoney() > r.getHighestBet()) {
            possibleActions[2] = true;
            possibleActions[3] = true;
        } else if (p.getMoney() == r.getHighestBet()) {
            possibleActions[2] = true;
        } else if (p.getMoney() < r.getHighestBet()) {
            possibleActions[3] = true;
        }

        return possibleActions;
    }

    public void removeLoserPlayers() {

        for (int i = 0; i < table.getSeats().length; i++) {
            if (table.getSeats()[i].getMoney() <= 0) {
                table.removePlayer(table.getSeats()[i]);
                i--;
            }
        }
    }
}