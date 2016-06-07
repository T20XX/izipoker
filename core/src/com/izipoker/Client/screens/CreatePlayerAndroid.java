package com.izipoker.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.izipoker.network.ClientCallbackInterface;
import com.izipoker.network.PokerClient;
import com.izipoker.network.ServerInterface;

import lipermi.handler.CallHandler;


public class CreatePlayerAndroid implements Screen {
    private Stage stage;

    private TextField nameTF;
    private TextureRegion avatarTR;
    private int avatarID = 0;
    private Image avatarImg;

    private TextButton createBtn;
    private TextButton cancelBtn;

    private ServerInterface proxyTable;
    private CallHandler callHandler;

    public CreatePlayerAndroid(ServerInterface proxyTable, CallHandler callHandler) {
        create();

        avatarTR = new TextureRegion();
        avatarTR.setRegion(TexturesLoad.avatarTex[0][avatarID]);
        this.proxyTable = proxyTable;
        this.callHandler = callHandler;


        buildStage();

    }

    public void buildStage() {
        //Actors
        Image tmp1 = new Image(TexturesLoad.backgroundTex);
        tmp1.setSize(stage.getWidth() * 2, stage.getHeight());
        tmp1.setPosition(0, 0);
        stage.addActor(tmp1);


        avatarImg = new Image(avatarTR);
        avatarImg.setPosition(stage.getWidth() / 2, 3 * stage.getHeight() / 4, Align.center);
        stage.addActor(avatarImg);

        createBtn = new TextButton("CREATE", TexturesLoad.skin);
        createBtn.setPosition(stage.getWidth() / 2, stage.getHeight() / 4 + createBtn.getHeight(), Align.center);
        stage.addActor(createBtn);

        cancelBtn = new TextButton("CANCEL", TexturesLoad.skin);
        cancelBtn.setPosition(stage.getWidth() / 2, stage.getHeight() / 4 - cancelBtn.getHeight(), Align.center);
        stage.addActor(cancelBtn);

        nameTF = new TextField("", TexturesLoad.skin);
        nameTF.setMessageText("Username");
        nameTF.setPosition(stage.getWidth() / 2, 2 * stage.getHeight() / 4, Align.center);
        stage.addActor(nameTF);

        //Listeners
        createBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {

                    System.out.println("Mesa " + proxyTable.getName() + "\n");


                    // now do conversation
                    if (proxyTable.isLobbyState()) {
                        // create and expose remote listener
                        PokerClient listener = new PokerClient(nameTF.getText(), avatarID);
                        callHandler.exportObject(ClientCallbackInterface.class, listener);

                        if (!proxyTable.join(nameTF.getText(), avatarID, listener)) {
                            Dialog resultDialog = new Dialog("Error", TexturesLoad.skin);
                            resultDialog.text("Nickname is already in use\nOr table is full");
                            resultDialog.button("BACK");
                            resultDialog.show(stage);
                            System.out.println("Sorry, nickname is already in use.");
                            return;
                        } else {
                            IZIPokerClient.getInstance().setScreen(new GameAndroid(nameTF.getText(), proxyTable, listener));
                        }
                    } else {
                        Dialog resultDialog = new Dialog("Error", TexturesLoad.skin);
                        resultDialog.text("Game has already started");
                        resultDialog.button("BACK");
                        resultDialog.show(stage);
                    }
                } catch (Exception e) {
                    System.err.println("Client exception: " + e.toString());
                    e.printStackTrace();
                }
            }

        });

        cancelBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                IZIPokerClient.getInstance().setScreen(new SearchTablesAndroid());
            }
        });

        avatarImg.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                avatarID++;
                if (avatarID >= TexturesLoad.MAX_AVATAR)
                    avatarID = 0;
                System.out.println(avatarID);
                avatarTR.setRegion(TexturesLoad.avatarTex[0][avatarID]);
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
