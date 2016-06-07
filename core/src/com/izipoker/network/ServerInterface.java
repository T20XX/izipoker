package com.izipoker.network;

import com.izipoker.game.PokerAction;

/**
 * Represents the methods that the clients and the dealer can call to make the interaction between server and clients
 */
public interface ServerInterface {
    /**
     * Join the table
     * @param name Name of the player to join
     * @param avatarID Number of the avatar of the player to join
     * @param client CallBackInterface associated to server use
     * @return True if the player was able to join the table and false otherwise
     */
    boolean join(String name, int avatarID, ClientCallbackInterface client);

    /**
     * Send a message to the chat
     * @param name Name of the player that send the message
     * @param message Messade to deliver
     */
    void tell(String name, String message);

    /**
     * Leave the table
     * @param name Name of the player to leave
     * @param win True if the player leaving won, and false if lost
     */
    void leave(String name, boolean win);

    /**
     * Send hand to a client
     * @param name Name of the player to receive the hand
     */
    void sendHand(String name);

    /**
     * Send card to a client
     * @param name Name of the player to receive the card
     */
    void sendCard(String name);

    /**
     * Send the possible actions a player can do
     * @param name Name of the player to send the possible actions
     * @param possibleActions Array of 4 booleans, one for each action in the following order: FOLD, CHECK, CALL, RAISE
     */
    void sendPossibleActions(String name, boolean possibleActions[]);

    /**
     * Send current money to a client
     * @param name Name of the player to receive the money
     */
    void sendMoney(String name);

    /**
     * Send poker action
     * @param name Name of the player sending the action
     * @param action Poker action that can be either FOLD, CHECK, CALL or RAISE
     */
    void sendPokerAction(String name, PokerAction action);

    /**
     * Send highest bet in the round
     * @param name Name of the player to send the highest bet
     */
    void sendHighestBet(String name);

    /**
     * Gets the name of the table
     * @return Name of the table
     */
    String getName();

    /**
     * Gets if the table is in lobby state accepting more players to join
     * @return True if the table is in lobby (accepting players), or false otherwise
     */
    boolean isLobbyState();
}
