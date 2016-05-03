package com.izipoker.game.desktop.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.izipoker.game.desktop.IZIPokerDesktop;

/**
 * Created by Telmo on 03/05/2016.
 */
public class MainMenuDesktop implements Screen{
    private Stage stage;

    private Texture backgroundTex;
    private Texture startTexUp, startTexDown;
    private Texture exitTexUp, exitTexDown;

    ImageButton startBtn;
    ImageButton exitBtn;

    public MainMenuDesktop () {
        //super( new StretchViewport(320.0f, 240.0f, new OrthographicCamera()) );
        create();
        //backgroundText = new Texture
        backgroundTex = new Texture("badlogic.jpg");
        startTexUp = new Texture("startBtnUp.png");
        startTexDown = new Texture("startBtnDown.png");
        exitTexUp = new Texture("exitBtnUp.png");
        exitTexDown = new Texture("exitBtnDown.png");
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

        tmp1 = new Image(startTexUp);
        Image tmp2 = new Image(startTexDown);
        startBtn = new ImageButton(tmp1.getDrawable(), tmp2.getDrawable());
        startBtn.setPosition( stage.getWidth() / 2, 300f, Align.center);
        stage.addActor(startBtn);

        tmp1 = new Image(exitTexUp);
        tmp2 = new Image(exitTexDown);
        exitBtn = new ImageButton(tmp1.getDrawable(), tmp2.getDrawable());
        exitBtn.setPosition( stage.getWidth() / 2, 150f, Align.center);
        stage.addActor(exitBtn);


        //Listeners
        startBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game g = IZIPokerDesktop.getInstance();
                g.setScreen(new CreateTableDesktop());
            };
        });

        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            };
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
        Gdx.gl.glClearColor(1, 0, 0, 1);
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
        startTexUp.dispose();
        startTexDown.dispose();
        exitTexUp.dispose();
        exitTexDown.dispose();
    }
}
