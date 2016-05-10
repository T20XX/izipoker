package com.izipoker.game;

import com.badlogic.gdx.Game;
import com.izipoker.game.screens.MainMenuAndroid;

/**
 * Created by Telmo on 10/05/2016.
 */
public class IZIPokerAndroid extends Game {
    private static IZIPokerAndroid ourInstance = new IZIPokerAndroid();

    public static IZIPokerAndroid getInstance() {
        return ourInstance;
    }

    private IZIPokerAndroid() {
    }

    @Override
    public void create() {
        this.screen = new MainMenuAndroid();
    }
}
