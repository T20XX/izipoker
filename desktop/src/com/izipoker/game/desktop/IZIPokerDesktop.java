package com.izipoker.game.desktop;

import com.badlogic.gdx.Game;
import com.izipoker.game.desktop.screens.MainMenuDesktop;

/**
 * Created by Telmo on 03/05/2016.
 */
public class IZIPokerDesktop  extends Game{
    private static IZIPokerDesktop instance = new IZIPokerDesktop();

    public static IZIPokerDesktop getInstance() {
        return instance;
    }

    private IZIPokerDesktop() {
    }

    @Override
    public void create() {
        this.screen = new MainMenuDesktop();
    }
}
