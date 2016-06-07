package com.izipoker.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.izipoker.client.IZIPokerClient;
import com.izipoker.graphics.TexturesLoad;
import com.izipoker.network.ClientConnection;
import com.izipoker.network.ServerInterface;

import lipermi.handler.CallHandler;


public class SearchTablesAndroid implements Screen {
    private Stage stage;

    private TextField ipTF;
    private TextButton searchTableBtn;
    private TextButton cancelBtn;

    private TextButton continueDialogBtn;

    private ServerInterface proxyTable;
    private CallHandler callHandler;
    private Dialog resultDialog;

    public SearchTablesAndroid() {
        create();

        proxyTable = null;
        callHandler = null;

        buildStage();
    }


    public void buildStage() {
        //Actors
        Image tmp1 = new Image(TexturesLoad.backgroundTex);
        tmp1.setSize(stage.getWidth() * 2, stage.getHeight());
        tmp1.setPosition(0, 0);
        stage.addActor(tmp1);

        ipTF = new TextField("", TexturesLoad.skin);
        ipTF.setMessageText("IP(faster) EMPTY(slower)");
        //ipTF.setText("172.30.4.199"); //DEBUGING
        ipTF.setAlignment(Align.center);
        ipTF.setSize(
                7 * stage.getWidth() / 8,
                stage.getHeight() / 8);
        ipTF.setPosition(stage.getWidth() / 2, 5 * stage.getHeight() / 6, Align.center);
        stage.addActor(ipTF);

        searchTableBtn = new TextButton("SEARCH TABLE", TexturesLoad.skin);
        searchTableBtn.setSize(ipTF.getWidth(), ipTF.getHeight());
        searchTableBtn.setPosition(stage.getWidth() / 2, 3 * stage.getHeight() / 6, Align.center);
        stage.addActor(searchTableBtn);

        cancelBtn = new TextButton("CANCEL", TexturesLoad.skin);
        cancelBtn.setSize(ipTF.getWidth(), ipTF.getHeight());
        cancelBtn.setPosition(
                stage.getWidth() / 2,
                stage.getHeight() / 6,
                Align.center);
        stage.addActor(cancelBtn);

        //Dialog
        continueDialogBtn = new TextButton("CONTINUE", TexturesLoad.skin);


        //Listeners
        searchTableBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setOnscreenKeyboardVisible(false);

                ClientConnection connection = new ClientConnection();
                //to emulator works
                connection.connectToServer(ipTF.getText(), 4455);
                if (connection.getProxyTable() == null) {
                    connection.findService();
                    connection.connectToServer();
                }
                proxyTable = connection.getProxyTable();
                callHandler = connection.getCallHandler();
                if (proxyTable != null) {
                    resultDialog = new Dialog("Table Found", TexturesLoad.skin);
                    resultDialog.text(proxyTable.getName());
                    resultDialog.button(continueDialogBtn);
                    resultDialog.button("CANCEL");
                    resultDialog.show(stage);
                } else {
                    resultDialog = new Dialog("Error", TexturesLoad.skin);
                    resultDialog.text("Connection failed");
                    resultDialog.button("BACK");
                    resultDialog.show(stage);
                }
            }

        });

        cancelBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                IZIPokerClient.getInstance().setScreen(new MainMenuAndroid());
            }
        });

        continueDialogBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                IZIPokerClient.getInstance().setScreen(new CreatePlayerAndroid(proxyTable, callHandler));
            }
        });
    }

    public void create() {
        stage = new Stage(new StretchViewport(200.0f, 400.0f, new OrthographicCamera()));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
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
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
