package com.izipoker.game.desktop.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.izipoker.game.Table;
import com.izipoker.game.desktop.IZIPokerDesktop;
import com.izipoker.graphics.TexturesLoad;
import com.izipoker.network.NetworkUtils;

import java.io.IOException;


public class LobbyDesktop implements Screen{
    //GUI Variables
    private Stage stage;

    private TextButton startBtn;
    private Label ipLbl;

    //Game variables
    private Table table;


    public LobbyDesktop(Table table) {
        //Game variables initialization
        this.table = table;
        create();


        buildStage();
    }

    public void buildStage(){
        //Actors

        table.setBounds(0,0,stage.getWidth(), stage.getHeight());
        stage.addActor(table);

        startBtn = new TextButton("START GAME", TexturesLoad.skin);
        startBtn.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
        stage.addActor(startBtn);

        try {
            ipLbl = new Label(NetworkUtils.getNetworkAddress().toString(), TexturesLoad.skin);
        } catch (IOException e) {
            e.printStackTrace();
            ipLbl = new Label("IP not found!", TexturesLoad.skin);
        }
        ipLbl.setPosition(stage.getWidth()/2,stage.getHeight()/2+startBtn.getHeight(), Align.center);
        stage.addActor(ipLbl);

        //Listeners
        startBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game g = IZIPokerDesktop.getInstance();
                g.setScreen(new GameDesktop(table));
            }

        });

    }

    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show(){
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 0.89f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}