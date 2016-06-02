package com.izipoker.game;

import java.io.Serializable;

public class PokerAction implements Serializable{

    // need to indicate the this on both sides (server and client) to make sure objects are compatible.
    private static final long serialVersionUID = 1L;

    public enum actionType{
        FOLD,
        CHECK,
        CALL,
        RAISE
    }

    private actionType type;
    private int amount;

    public PokerAction(actionType type){
        if (type != actionType.RAISE){
            this.type = type;
            this.amount = 0;
        }else{
            throw new IllegalArgumentException("Raise action requires bet amount");
        }
    }

    public PokerAction(actionType type, int amount){
        if (type == actionType.RAISE){
            this.type = type;
            this.amount = amount;
        }else{
            throw new IllegalArgumentException("Fold, check and call actions don't require bet amount");
        }
    }

}
