package com.izipoker.game.desktop.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.izipoker.game.Player;
import com.izipoker.game.Table;
import com.izipoker.game.desktop.IZIPokerDesktop;
import com.izipoker.network.NetworkUtils;

import java.io.IOException;

/**
 * Created by Telmo on 03/05/2016.
 */
public class LobbyDesktop implements Screen{
    //GUI Variables
    private Stage stage;
    private Skin skin;

    private TextButton startBtn;
    private Label ipLbl;
    private Player seats[];

    //Game variables
    private Table table;
    //private int lastPlayersSize = 0;


    public LobbyDesktop(Table table) {
        //Game variables initialization
        this.table = table;
        this.seats = table.getSeats();
        //super( new StretchViewport(320.0f, 240.0f, new OrthographicCamera()) );
        create();
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));


        buildStage();

        /*Deck d = new Deck();
        System.out.println(d);
        d.shuffle(3);
        System.out.println(d);*/
    }

    public void buildStage(){
        //Actors

        table.setBounds(0,0,stage.getWidth(), stage.getHeight());
        stage.addActor(table);

        startBtn = new TextButton("START GAME",skin);
        startBtn.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
        stage.addActor(startBtn);

        try {
            ipLbl = new Label(NetworkUtils.getNetworkAddress().toString(),skin);
        } catch (IOException e) {
            e.printStackTrace();
            ipLbl = new Label("IP not found!", skin);
        }
        ipLbl.setPosition(stage.getWidth()/2,stage.getHeight()/2+startBtn.getHeight(), Align.center);
        stage.addActor(ipLbl);

        //Listeners
        startBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Thread t = new Thread(table.getDealer());
                //t.start();
                Game g = IZIPokerDesktop.getInstance();
                g.setScreen(new GameDesktop(table));
            }

            ;
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
        Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*if(table.getActivePlayers().length != lastPlayersSize){
            System.out.println(lastPlayersSize);
            lastPlayersSize = table.getActivePlayers().length;
            for(Player p : table.getActivePlayers()){
                if(!stage.getActors().contains(p,true)){
                    System.out.println(lastPlayersSize);
                    p.setBounds(100,100,100,100);
                    stage.addActor(p);
                }
            }
        }*/

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