package com.izipoker.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.izipoker.game.desktop.screens.MainMenuDesktop;

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
