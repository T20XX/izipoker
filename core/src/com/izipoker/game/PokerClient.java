package com.izipoker.game;

import com.izipoker.interfaces.ClientCallbackInterface;

/**
 * Created by Telmo on 24/05/2016.
 */
public class PokerClient implements ClientCallbackInterface {


    @Override
    public void notify(String message) {
        System.out.println(message);
    }
}
