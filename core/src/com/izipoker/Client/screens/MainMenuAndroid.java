package com.izipoker.client.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.izipoker.client.IZIPokerClient;

/**
 * Created by Telmo on 03/05/2016.
 */
public class MainMenuAndroid implements Screen {
    private Stage stage;
    private Skin skin;


    private Texture backgroundTex;

    private Label title;
    private TextButton startBtn;
    private TextButton exitBtn;

    public MainMenuAndroid() {
        //super( new StretchViewport(320.0f, 240.0f, new OrthographicCamera()) );
        create();
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));

        //backgroundText = new Texture

        backgroundTex = new Texture("background.png");

        buildStage();
    }

    public void buildStage() {
        //Actors
        Image tmp1 = new Image(backgroundTex);
        tmp1.setSize(stage.getWidth() * 2, stage.getHeight());
        tmp1.setPosition(0, 0);
        stage.addActor(tmp1);

        title = new Label("IZI POKER", skin);
        title.setPosition(stage.getWidth() / 2, 5 * stage.getHeight() / 6, Align.center);
        stage.addActor(title);

        startBtn = new TextButton("START", skin);
        startBtn.setSize(
                7 * stage.getWidth() / 8,
                stage.getHeight() / 8);
        startBtn.setPosition(stage.getWidth() / 2, 3 * stage.getHeight() / 6, Align.center);
        stage.addActor(startBtn);

        exitBtn = new TextButton("EXIT", skin);
        exitBtn.setSize(
                7 * stage.getWidth() / 8,
                stage.getHeight() / 8);
        exitBtn.setPosition(stage.getWidth() / 2, 1 * stage.getHeight() / 6, Align.center);
        stage.addActor(exitBtn);


        //Listeners
        startBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game g = IZIPokerClient.getInstance();
                g.setScreen(new SearchTablesAndroid());
            }

        });

        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

    }

    public void create() {
        //stage = new Stage(new ScreenViewport());
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
        backgroundTex.dispose();
    }
}
