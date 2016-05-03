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
    private Texture createTableTexUp;
    private Texture exitTexUp;

    ImageButton createTableBtn;
    ImageButton exitBtn;

    public MainMenuDesktop () {
        //super( new StretchViewport(320.0f, 240.0f, new OrthographicCamera()) );
        create();
        //backgroundText = new Texture
        backgroundTex = new Texture("C:\\Users\\Telmo\\git\\izipoker\\android\\assets\\badlogic.jpg");
        createTableTexUp = new Texture("C:\\Users\\Telmo\\git\\izipoker\\android\\assets\\badlogic.jpg");
        exitTexUp = new Texture("C:\\Users\\Telmo\\git\\izipoker\\android\\assets\\badlogic.jpg");
        buildStage();

        /*Deck d = new Deck();
        System.out.println(d);
        d.shuffle(3);
        System.out.println(d);*/
    }

    public void buildStage() {
        //Actors
        Image tmp = new Image(backgroundTex);
        stage.addActor(tmp);

        tmp = new Image(createTableTexUp);
        createTableBtn = new ImageButton(tmp.getDrawable());
        createTableBtn.setPosition( stage.getWidth() / 2, 300f, Align.center);
        stage.addActor(createTableBtn);

        tmp = new Image(exitTexUp);
        exitBtn = new ImageButton(tmp.getDrawable());
        exitBtn.setPosition( stage.getWidth() / 2, 150f, Align.center);
        stage.addActor(exitBtn);


        //Listeners
        createTableBtn.addListener(new ClickListener() {
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
        createTableTexUp.dispose();
        exitTexUp.dispose();
    }
}
