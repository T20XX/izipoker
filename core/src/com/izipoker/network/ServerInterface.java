package com.izipoker.network;

import com.izipoker.game.PokerAction;

public interface ServerInterface {
    boolean join(String name, int avatarID, ClientCallbackInterface client);

    void tell(String name, String message);

    void leave(ClientCallbackInterface c);

    void sendHand(String name);

    void sendCard(String name);

    void sendPossibleActions(String name, boolean possibleActions[]);

    void sendMoney(String name);

    void sendPokerAction(String name, PokerAction action);

    void sendHighestBet(String name);

    String getName();

    boolean isLobbyState();
}
