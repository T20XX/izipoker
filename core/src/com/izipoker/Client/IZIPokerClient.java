package com.izipoker.client;

import com.badlogic.gdx.Game;
import com.izipoker.client.screens.MainMenuAndroid;

/**
 * Created by Telmo on 03/05/2016.
 */
public class IZIPokerClient  extends Game {

    private static IZIPokerClient instance = new IZIPokerClient();

    public static IZIPokerClient getInstance() {
        return instance;
    }

    private IZIPokerClient() {
    }

    @Override
    public void create() {
        this.screen = new MainMenuAndroid();
    }
}