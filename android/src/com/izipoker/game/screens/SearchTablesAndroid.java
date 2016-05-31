package com.izipoker.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.izipoker.game.IZIPokerAndroid;
import com.izipoker.interfaces.ServerInterface;

import lipermi.handler.CallHandler;
import lipermi.net.Client;

/**
 * Created by Telmo on 03/05/2016.
 */
public class SearchTablesAndroid implements Screen{
    private Stage stage;
    private Skin skin;


    private Texture backgroundTex;

    private TextField ipTF;
    private TextButton searchTableBtn;
    private TextButton cancelBtn;

    private Dialog resultDialog;
    private TextButton continueDialogBtn;
    private TextButton cancelDialogBtn;

    private ServerInterface proxyTable;
    private CallHandler callHandler;

    public SearchTablesAndroid() {
        create();
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));

        backgroundTex = new Texture("background.png");
        proxyTable = null;
        callHandler = null;

        buildStage();
    }


    public void buildStage() {
        //Actors
        Image tmp1 = new Image(backgroundTex);
        tmp1.setSize(stage.getWidth() * 2, stage.getHeight());
        tmp1.setPosition(0, 0);
        stage.addActor(tmp1);

        ipTF = new TextField("", skin);
        ipTF.setMessageText("Ex: 127.xxx.xxx.xxx");
        ipTF.setText("172.30.4.199"); //DEBUGING
        ipTF.setAlignment(Align.center);
        ipTF.setSize(
                7 * stage.getWidth() / 8,
                stage.getHeight() / 8);
        ipTF.setPosition(stage.getWidth() / 2, 5 * stage.getHeight() / 6, Align.center);
        stage.addActor(ipTF);

        searchTableBtn = new TextButton("SEARCH TABLE", skin);
        searchTableBtn.setSize(ipTF.getWidth(), ipTF.getHeight());
        searchTableBtn.setPosition(stage.getWidth() / 2, 3 * stage.getHeight() / 6, Align.center);
        stage.addActor(searchTableBtn);

        cancelBtn = new TextButton("CANCEL", skin);
        cancelBtn.setSize(ipTF.getWidth(), ipTF.getHeight());
        cancelBtn.setPosition(
                stage.getWidth() / 2,
                stage.getHeight() / 6,
                Align.center);
        stage.addActor(cancelBtn);

        //Dialog
        continueDialogBtn = new TextButton("CONTINUE", skin);
        //cancelDialogBtn = new TextButton("CANCEL", skin);


        //Listeners
        searchTableBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setOnscreenKeyboardVisible(false);
                try {
                    // get proxy for remote chat server
                    callHandler = new CallHandler();
                    String remoteHost = ipTF.getText();
                    int portWasBinded = 4455;
                    Client client = new Client(remoteHost, portWasBinded, callHandler);
                    proxyTable = (ServerInterface)client.getGlobal(ServerInterface.class);

                    //System.out.println("Mesa " + proxyTable.getName() + "\n");

                    resultDialog = new Dialog("Table Found", skin);
                    resultDialog.setWidth(7*stage.getWidth()/8);
                    resultDialog.text(proxyTable.getName());
                    resultDialog.button(continueDialogBtn);
                    resultDialog.button("CANCEL");
                    resultDialog.show(stage);

                } catch (Exception e) {
                    resultDialog = new Dialog("Error", skin);
                    resultDialog.text("Connection failed");
                    resultDialog.button("BACK");
                    resultDialog.show(stage);
                    System.out.println("Client exception: " + e.toString());
                    e.printStackTrace();
                }
            }

            ;
        });

        cancelBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                IZIPokerAndroid.getInstance().setScreen(new MainMenuAndroid());
            };
        });

        continueDialogBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                IZIPokerAndroid.getInstance().setScreen(new CreatePlayerAndroid(proxyTable, callHandler));            };
        });
    }

    public void create() {
        stage = new Stage( new StretchViewport(200.0f, 400.0f, new OrthographicCamera()));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show(){
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
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
    }
}
