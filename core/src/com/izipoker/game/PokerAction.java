package com.izipoker.game;

import java.io.Serializable;

/**
 * Represents a action in poker
 */
public class PokerAction implements Serializable {

    /**
     * Type of action in poker
     */
    public enum actionType {
        /**
         * Fold action
         * Give up current hand/round
         */
        FOLD,
        /**
         * Check action
         * Pass turn by betting 0
         */
        CHECK,
        /**
         * Call action
         * Bet the highest amount bet in the table
         */
        CALL,
        /**
         * Raise action
         * Bet a specific amount greater than the highest on table
         */
        RAISE
    }

    /**
     * Gets the bet amount in a raise
     *
     * @return Bet amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Gets the type of the action
     *
     * @return Type of the action
     */
    public actionType getType() {
        return type;
    }
    // need to indicate the this on both sides (server and client) to make sure objects are compatible.
    private static final long serialVersionUID = 1L;
    /**
     * Type of action
     *
     * @see com.izipoker.game.PokerAction.actionType
     */
    private actionType type;
    /**
     * Amount of the bet if it is a raise
     */
    private int amount;

    /**
     * Constructor for fold, check and call actions
     *
     * @param type Type of action
     * @see PokerAction#PokerAction(actionType, int) for Raise actions
     */
    public PokerAction(actionType type) {
        if (type != actionType.RAISE) {
            this.type = type;
            this.amount = 0;
        } else {
            throw new IllegalArgumentException("Raise action requires bet amount");
        }
    }

    /**
     * Constructor for raise actions
     *
     * @param type   Type of action
     * @param amount Bet amount
     * @see PokerAction#PokerAction(actionType) for fold, check and call actions
     */
    public PokerAction(actionType type, int amount) {
        if (type == actionType.RAISE) {
            this.type = type;
            this.amount = amount;
        } else {
            throw new IllegalArgumentException("Fold, check and call actions don't require bet amount");
        }
    }

}
