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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.izipoker.cardGame.Card;
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
public class CreatePlayerAndroid implements Screen{
    private Stage stage;

    private Texture backgroundTex;
    private Texture createtTexUp, createTexDown;
    private Texture cancelTexUp, cancelTexDown;
    private Skin skin;
    private TextField nameTF;
    private TextureRegion avatarTR;
    private Image avatarImg;
    private Texture avatarTxt;

    TextButton createBtn;
    TextButton cancelBtn;

    public CreatePlayerAndroid () {
        //super( new StretchViewport(320.0f, 240.0f, new OrthographicCamera()) );
        create();
        //backgroundText = new Texture

        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));
        backgroundTex = new Texture("background.png");
        avatarTxt = new Texture("avatar.jpg");
        avatarTR = new TextureRegion(avatarTxt, 0, 0, avatarTxt.getWidth()/7, avatarTxt.getHeight());

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
        Image tmp1 = new Image(backgroundTex);
        stage.addActor(tmp1);


        avatarImg = new Image(avatarTR);
        avatarImg.setPosition(stage.getWidth()/2, 3*stage.getHeight()/4, Align.center);
        stage.addActor(avatarImg);

        createBtn = new TextButton("CREATE", skin);
        createBtn.setPosition( stage.getWidth() / 2, stage.getHeight()/4 + createBtn.getHeight() , Align.center);
        stage.addActor(createBtn);

        cancelBtn = new TextButton("CANCEL", skin);
        cancelBtn.setPosition( stage.getWidth() / 2, stage.getHeight()/4 - cancelBtn.getHeight(), Align.center);
        //exitBtn.setBounds(exitBtn.getX(), exitBtn.getY(), 100, 10);
        stage.addActor(cancelBtn);

        nameTF = new TextField("", skin);
        nameTF.setMessageText("Username");
        nameTF.setPosition(stage.getWidth()/2, 2*stage.getHeight()/4, Align.center);
        stage.addActor(nameTF);

        //Listeners
        createBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                /*Game g = IZIPokerAndroid.getInstance();
                g.setScreen(new SearchTablesAndroid());*/
               /* Human p = new Human(0, nameTF.getText(), 0, avatarTR);
                p.setBounds(100,100,100,100);
                stage.addActor(p);*/
                try {
                        System.out.println("teste");
                    // get proxy for remote chat server
                    CallHandler callHandler = new CallHandler();
                    String remoteHost = "172.30.17.202";
                    int portWasBinded = 4455;
                    Client client = new Client(remoteHost, portWasBinded, callHandler);
                    ServerInterface proxy = (ServerInterface)client.getGlobal(ServerInterface.class);

                    System.out.println("Mesa " + proxy.getName() + "\n");

                    // create and expose remote listener
                    Player listener = new Human(0, nameTF.getText(), 0, avatarTR);
                    callHandler.exportObject(ClientCallbackInterface.class, listener);

                    // now do conversation
                    if (proxy.join(listener) ) {
                        System.out.println("Sorry, nickname is already in use.");
                        return;
                    }
                } catch (Exception e) {
                    System.err.println("Client exception: " + e.toString());
                    e.printStackTrace();
                }
            }

            ;
        });

        cancelBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                IZIPokerAndroid.getInstance().setScreen(new SearchTablesAndroid());
            };
        });

        avatarImg.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int xregion = avatarTR.getRegionX() + avatarTR.getRegionWidth();

                if(xregion >= avatarTxt.getWidth()-avatarTxt.getWidth()/7){
                    xregion = 0;
                }
                avatarTR.setRegion(xregion, 0,avatarTxt.getWidth()/7,avatarTxt.getHeight());
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        backgroundTex.dispose();

       // startTexUp.dispose();
        //startTexDown.dispose();
         //exitTexUp.dispose();
          //exitTexDown.dispose();
    }
}