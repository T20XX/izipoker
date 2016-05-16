package com.izipoker.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.izipoker.cardGame.Card;
import com.izipoker.game.ChatClient;
import com.izipoker.game.ChatClientCallbackInterface;
import com.izipoker.game.ChatServerInterface;

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

    TextButton createBtn;
    TextButton cancelBtn;

    public CreatePlayerAndroid () {
        //super( new StretchViewport(320.0f, 240.0f, new OrthographicCamera()) );
        create();
        //backgroundText = new Texture

        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));
        backgroundTex = new Texture("background.png");
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

        createBtn = new TextButton("CREATE", skin);
        createBtn.setPosition( stage.getWidth() / 2, 300f, Align.center);
        stage.addActor(createBtn);

        cancelBtn = new TextButton("CANCEL", skin);
        cancelBtn.setPosition( stage.getWidth() / 2, 150f, Align.center);
        //exitBtn.setBounds(exitBtn.getX(), exitBtn.getY(), 100, 10);
        stage.addActor(cancelBtn);

        Card c =new Card(13, Card.suitType.DIAMONDS);
        c.setBounds(100,100,100,150);
        stage.addActor(c);


        //Listeners
        createBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                /*Game g = IZIPokerAndroid.getInstance();
                g.setScreen(new SearchTablesAndroid());*/
                try {
                        System.out.println("teste");
                    // get proxy for remote chat server
                    CallHandler callHandler = new CallHandler();
                    String remoteHost = "localhost";
                    int portWasBinded = 4455;
                    Client client = new Client(remoteHost, portWasBinded, callHandler);
                    ChatServerInterface proxy = (ChatServerInterface)client.getGlobal(ChatServerInterface.class);

                    // create and expose remote listener
                    ChatClient listener = new ChatClient();
                    callHandler.exportObject(ChatClientCallbackInterface.class, listener);

                    cancelBtn.setText("Worked");

                    // now do conversation
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("What's your nickname? ");
                    String name = scanner.nextLine();
                    if ( ! proxy.join(name, listener) ) {
                        System.out.println("Sorry, nickname is already in use.");
                        return;
                    }
                    String message = scanner.nextLine();
                    while(! message.equals("exit")) {
                        if (!message.equals(""))
                            proxy.tell(name, message);
                        message = scanner.nextLine();
                    }
                    proxy.leave(name);
                    System.exit(0);
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
                Gdx.app.exit();
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
