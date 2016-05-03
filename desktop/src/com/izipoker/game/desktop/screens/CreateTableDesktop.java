package com.izipoker.game.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Telmo on 03/05/2016.
 */
public class CreateTableDesktop extends Stage implements Screen{
    private Stage stage;

    private Texture backgroundText;
    private Texture createTableTextUp;
    private Texture exitText;

    ImageButton createTableBtn;


    public CreateTableDesktop() {
        super( new StretchViewport(320.0f, 240.0f, new OrthographicCamera()) );
        create();
        //backgroundText = new Texture
        backgroundText = new Texture("C:\\Users\\Telmo\\git\\izipoker\\android\\assets\\badlogic.jpg");
        createTableTextUp = new Texture("C:\\Users\\Telmo\\git\\izipoker\\android\\assets\\badlogic.jpg");
        buildStage();

        /*Deck d = new Deck();
        System.out.println(d);
        d.shuffle(3);
        System.out.println(d);*/
    }

    public void buildStage() {
        //Actors
        Image tmp = new Image(backgroundText);
       // addActor(tmp);

        //Listeners

    }

    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.act(delta);
        super.draw();
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
    super.dispose();
        backgroundText.dispose();
        createTableTextUp.dispose();
        exitText.dispose();
    }
}
