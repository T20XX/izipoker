package com.izipoker.game.desktop.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.izipoker.game.Table;
import com.izipoker.game.desktop.IZIPokerDesktop;
import com.izipoker.graphics.TexturesLoad;
import com.izipoker.network.NetworkUtils;
import com.izipoker.network.ServerInterface;

import java.io.IOException;
import java.net.InetAddress;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import lipermi.handler.CallHandler;
import lipermi.net.Server;


public class CreateTableDesktop implements Screen {
    private Stage stage;
    private Label tableNameLbl, startMoneyLbl;
    private TextField tableNameTF;
    private Label tableSizeLbl, AFKValueLbl;
    private Label seatsValueLbl, AFKTimeLbl;
    private Slider tableSizeSlider;
    private TextButton createTableBtn;
    private TextButton cancelBtn;
    private Slider AFKTimeSlider;
    private TextField startMoneyTF;


    public CreateTableDesktop() {

        create();

        buildStage();

    }

    public void buildStage() {
        //Actors
        Image tmp1 = new Image(TexturesLoad.backgroundTex);
        stage.addActor(tmp1);

        AFKTimeSlider = new Slider(10f, 60f, 5, false, TexturesLoad.skin);
        AFKTimeSlider.setWidth(stage.getWidth() / 3);
        AFKTimeSlider.setHeight(20f);
        AFKTimeSlider.setPosition(stage.getWidth() / 2, 5 * stage.getHeight() / 10, Align.center);
        stage.addActor(AFKTimeSlider);

        AFKTimeLbl = new Label("PLAY TIME (sec)", TexturesLoad.skin);
        AFKTimeLbl.setPosition(stage.getWidth() / 2, 5 * stage.getHeight() / 10 + AFKTimeSlider.getHeight(), Align.center);
        stage.addActor(AFKTimeLbl);

        AFKValueLbl = new Label(String.valueOf((int) AFKTimeSlider.getValue()), TexturesLoad.skin);
        AFKValueLbl.setPosition(stage.getWidth() / 2, 5 * stage.getHeight() / 10 - AFKTimeSlider.getHeight(), Align.center);
        stage.addActor(AFKValueLbl);


        startMoneyTF = new TextField("1000", TexturesLoad.skin);
        startMoneyTF.setPosition(stage.getWidth() / 2, 3 * stage.getHeight() / 10, Align.center);
        stage.addActor(startMoneyTF);

        startMoneyLbl = new Label("START AMOUNT", TexturesLoad.skin);
        startMoneyLbl.setPosition(stage.getWidth() / 2, 3 * stage.getHeight() / 10 + startMoneyTF.getHeight(), Align.center);
        stage.addActor(startMoneyLbl);

        tableNameTF = new TextField("", TexturesLoad.skin);
        tableNameTF.setPosition(stage.getWidth() / 2, 9 * stage.getHeight() / 10, Align.center);
        stage.addActor(tableNameTF);

        tableNameLbl = new Label("TABLE NAME", TexturesLoad.skin);
        tableNameLbl.setPosition(stage.getWidth() / 2, 9 * stage.getHeight() / 10 + tableNameTF.getHeight(), Align.center);
        stage.addActor(tableNameLbl);

        tableSizeSlider = new Slider(2f, 8f, 1, false, TexturesLoad.skin);
        tableSizeSlider.setWidth(stage.getWidth() / 3);
        tableSizeSlider.setHeight(20f);
        tableSizeSlider.setPosition(stage.getWidth() / 2, 7 * stage.getHeight() / 10, Align.center);
        stage.addActor(tableSizeSlider);

        tableSizeLbl = new Label("NUMBER OF SEATS", TexturesLoad.skin);
        tableSizeLbl.setPosition(stage.getWidth() / 2, 7 * stage.getHeight() / 10 + tableSizeSlider.getHeight(), Align.center);
        stage.addActor(tableSizeLbl);

        seatsValueLbl = new Label(String.valueOf((int) tableSizeSlider.getValue()), TexturesLoad.skin);
        seatsValueLbl.setPosition(stage.getWidth() / 2, 7 * stage.getHeight() / 10 - tableSizeSlider.getHeight(), Align.center);
        stage.addActor(seatsValueLbl);


        createTableBtn = new TextButton("CREATE TABLE", TexturesLoad.skin);
        createTableBtn.setWidth(stage.getWidth() / 5);
        createTableBtn.setPosition((stage.getWidth() / 4), stage.getHeight() / 10, Align.center);
        stage.addActor(createTableBtn);

        cancelBtn = new TextButton("CANCEL", TexturesLoad.skin);
        cancelBtn.setWidth(stage.getWidth() / 5);
        cancelBtn.setPosition(3 * (stage.getWidth() / 4), stage.getHeight() / 10, Align.center);
        stage.addActor(cancelBtn);


        //Listeners
        createTableBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                try {
                    if (Integer.valueOf(startMoneyTF.getText()) >= 100) {
                        Table table = new Table(tableNameTF.getText(), (int) tableSizeSlider.getValue(), (int) AFKTimeSlider.getValue(), Integer.valueOf(startMoneyTF.getText()));
                        CallHandler callHandler = new CallHandler();
                        callHandler.registerGlobal(ServerInterface.class, table);
                        Server server = new Server();
                        int thePortIWantToBind = 4455;
                        server.bind(thePortIWantToBind, callHandler);
                        System.err.println("Server ready");
                        Game g = IZIPokerDesktop.getInstance();
                        g.setScreen(new LobbyDesktop(table));

                        InetAddress localAddress = null;
                        JmDNS mdnsServer = null;
                        try {
                            localAddress = NetworkUtils.getNetworkAddress();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }

                        if (localAddress != null) {
                            // Creates mDNS server.
                            try {
                                mdnsServer = JmDNS.create(localAddress);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                            ServiceInfo testService = ServiceInfo.create("_poker._tcp.local.", "Poker table", thePortIWantToBind, "Poker table");
                            try {
                                mdnsServer.registerService(testService);
                                System.out.println("mDNS Server Ready!");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                } catch (Exception e) {
                    System.err.println("Server exception: " + e.toString());
                    e.printStackTrace();
                }
            }

        });

        tableSizeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                seatsValueLbl.setText(String.valueOf((int) tableSizeSlider.getValue()));
            }
        });
        AFKTimeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AFKValueLbl.setText(String.valueOf((int) AFKTimeSlider.getValue()));
            }
        });


    }

    public void create() {
        stage = new Stage(new ScreenViewport());
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
