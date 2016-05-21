package com.izipoker.game.desktop.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.izipoker.game.Table;
import com.izipoker.game.desktop.IZIPokerDesktop;
import com.izipoker.interfaces.ServerInterface;

import lipermi.handler.CallHandler;
import lipermi.net.Server;

/**
 * Created by Telmo on 03/05/2016.
 */
public class CreateTableDesktop implements Screen{
    private Stage stage;
    private Skin skin;

    private Label tableNameLbl;
    private TextField tableNameTF;

    private Label tableSizeLbl;
    private Label seatsValueLbl;
    private Slider tableSizeSlider;

    private TextButton createTableBtn;
    private TextButton cancelBtn;


    public CreateTableDesktop() {
        //super( new StretchViewport(320.0f, 240.0f, new OrthographicCamera()) );
        create();
        //backgroundText = new Texture
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));

        buildStage();

        /*Deck d = new Deck();
        System.out.println(d);
        d.shuffle(3);
        System.out.println(d);*/
    }

    public void buildStage() {
        //Actors
        //Image tmp1 = new Image(backgroundTex);
        //stage.addActor(tmp1);

        tableNameTF = new TextField("",skin);
        tableNameTF.setPosition( stage.getWidth() / 2, 7 * stage.getHeight() / 8, Align.center);
        stage.addActor(tableNameTF);

        tableNameLbl = new Label("TABLE NAME", skin);
        tableNameLbl.setPosition( stage.getWidth() / 2, 7 * stage.getHeight() / 8 + tableNameTF.getHeight(), Align.center);
        stage.addActor(tableNameLbl);

        tableSizeSlider = new Slider(2f,8f,1,false,skin);
        tableSizeSlider.setWidth(stage.getWidth() / 3);
        tableSizeSlider.setHeight(20f);
        tableSizeSlider.setPosition( stage.getWidth() / 2, 4 * stage.getHeight() / 8, Align.center);
        stage.addActor(tableSizeSlider);

        tableSizeLbl = new Label("NUMBER OF SEATS", skin);
        tableSizeLbl.setPosition( stage.getWidth() / 2, 4 * stage.getHeight() / 8 + tableSizeSlider.getHeight(), Align.center);
        stage.addActor(tableSizeLbl);

        seatsValueLbl = new Label(String.valueOf((int)tableSizeSlider.getValue()), skin);
        seatsValueLbl.setPosition( stage.getWidth() / 2, 4 * stage.getHeight() / 8 - tableSizeSlider.getHeight(), Align.center);
        stage.addActor(seatsValueLbl);


        createTableBtn = new TextButton("CREATE TABLE", skin);
        createTableBtn.setWidth(stage.getWidth() / 5);
        createTableBtn.setPosition((stage.getWidth() / 4), stage.getHeight() / 8, Align.center);
        stage.addActor(createTableBtn);

        cancelBtn = new TextButton("CANCEL", skin);
        cancelBtn.setWidth(stage.getWidth() / 5);
        cancelBtn.setPosition( 3 * (stage.getWidth() / 4), stage.getHeight() / 8, Align.center);
        stage.addActor(cancelBtn);


        //Listeners
        createTableBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                try {
                    Table table = new Table(tableNameTF.getText(), (int)tableSizeSlider.getValue());
                    CallHandler callHandler = new CallHandler();
                    callHandler.registerGlobal(ServerInterface.class, table);
                    Server server = new Server();
                    int thePortIWantToBind = 4455;
                    server.bind(thePortIWantToBind, callHandler);
                    System.err.println("Server ready");
                    Game g = IZIPokerDesktop.getInstance();
                    g.setScreen(new LobbyDesktop(table));
                } catch (Exception e) {
                    System.err.println("Server exception: " + e.toString());
                    e.printStackTrace();
                }
            }

            ;
        });

        tableSizeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                seatsValueLbl.setText(String.valueOf((int)tableSizeSlider.getValue()));
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
    }
}
