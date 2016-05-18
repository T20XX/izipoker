package com.izipoker.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.izipoker.cardGame.Card;
import com.izipoker.game.Hand;
import com.izipoker.game.Human;
import com.izipoker.game.IZIPoker;
import com.izipoker.game.IZIPokerAndroid;
import com.izipoker.game.Player;
import com.izipoker.interfaces.ClientCallbackInterface;
import com.izipoker.interfaces.ServerInterface;

import java.util.Scanner;

import lipermi.handler.CallHandler;
import lipermi.net.Client;

/**
 * Created by Telmo on 03/05/2016.
 */
public class GameAndroid implements Screen{
    private Stage stage;

    private TextButton foldBtn, callBtn, raiseBtn, checkBtn;
    private Skin skin;
    private Slider betSlider;
    private Player player;
    private Card c_1,c_2;
    private TextButton halfPot, maxPot, allIn;

    public GameAndroid (Player p) {
        //super( new StretchViewport(320.0f, 240.0f, new OrthographicCamera()) );
        create();
        //backgroundText = new Texture

        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));
        player = p;
        p.setHand(new Hand(new Card(Card.rankType.ACE, Card.suitType.CLUBS), new Card(Card.rankType.TWO, Card.suitType.SPADES)));

        //  startTexUp = new Texture("startBtnUp.png");
        // startTexDown = new Texture("startBtnDown.png");
        //exitTexUp = new Texture("exitBtnUp.png");
        //exitTexDown = new Texture("exitBtnDown.png");

        buildStage();

        /*Deck d = new Deck();
        System.out.println(d);
        d.shuffle(3);
        System.out.println(d);*/
    }

    public void buildStage() {
        //Actors

        betSlider = new Slider(50, 500, 10, true, skin);
        betSlider.setWidth(stage.getWidth()/6);
        betSlider.setPosition(stage.getWidth()- betSlider.getWidth(), 0, Align.center);
        stage.addActor(betSlider);

        foldBtn = new TextButton("FOLD", skin);
        foldBtn.setWidth(stage.getWidth()/3);
        foldBtn.setPosition( stage.getWidth() / 4, stage.getHeight()/4 - 2*foldBtn.getHeight() , Align.center);
        stage.addActor(foldBtn);

        callBtn = new TextButton("CALL", skin);
        callBtn.setWidth(stage.getWidth()/3);
        callBtn.setPosition(stage.getWidth() / 4, stage.getHeight()/4 + (2)*callBtn.getHeight() , Align.center);
        stage.addActor(callBtn);

        checkBtn = new TextButton("CHECK", skin);
        checkBtn.setWidth(stage.getWidth()/3);
        checkBtn.setPosition(stage.getWidth() / 4, stage.getHeight()/4 , Align.center);
        stage.addActor(checkBtn);


        raiseBtn = new TextButton("RAISE", skin);
        raiseBtn.setWidth(stage.getWidth()/3);
        raiseBtn.setPosition(3* stage.getWidth() / 5, stage.getHeight()/4 + 2*raiseBtn.getHeight() , Align.center);
        stage.addActor(raiseBtn);


        halfPot = new TextButton("1/2", skin);
        halfPot.setWidth(stage.getWidth()/6);
        halfPot.setPosition(7* stage.getWidth() / 10, stage.getHeight()/4 + halfPot.getHeight()/2 , Align.center);
        stage.addActor(halfPot);

        maxPot = new TextButton("POT", skin);
        maxPot.setWidth(stage.getWidth()/6);
        maxPot.setPosition(7* stage.getWidth() / 10, halfPot.getY() - halfPot.getHeight() , Align.center);
        stage.addActor(maxPot);

        allIn = new TextButton("MAX", skin);
        allIn.setWidth(stage.getWidth()/6);
        allIn.setPosition(7* stage.getWidth() / 10,maxPot.getY() - maxPot.getHeight() , Align.center);
        stage.addActor(allIn);

        c_1 = player.getHand().getCards()[0];
        c_2 = player.getHand().getCards()[1];
        c_1.setBounds(stage.getWidth()/4,stage.getHeight()-stage.getHeight()/3,stage.getWidth()/4,stage.getHeight()/5);
        stage.addActor(c_1);
        c_2.setBounds(stage.getWidth()/2,stage.getHeight()-stage.getHeight()/3,stage.getWidth()/4,stage.getHeight()/5);
        stage.addActor(c_2);

        //Listeners
        c_1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                c_1.flip();
            };
        });
        c_2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                c_2.flip();
            };
        });
        foldBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            };
        });

        callBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            };
        });

        raiseBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            };
        });
        checkBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            };
        });
    }

    public void create() {
        //stage = new Stage(new ScreenViewport());
        stage = new Stage( new StretchViewport(200.0f, 400.0f, new OrthographicCamera()));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
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

        // startTexUp.dispose();
        //startTexDown.dispose();
        //exitTexUp.dispose();
        //exitTexDown.dispose();
    }
}
