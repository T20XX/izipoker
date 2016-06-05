package com.izipoker.game.desktop.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.izipoker.cardGame.Card;
import com.izipoker.game.desktop.IZIPokerDesktop;

/**
 * Created by Telmo on 03/05/2016.
 */
public class MainMenuDesktop implements Screen{
    private Stage stage;
    private Skin skin;

    private Texture backgroundTex;

    private Label title;

    private TextButton startBtn;
    private TextButton exitBtn;

    public MainMenuDesktop () {
        //super( new StretchViewport(320.0f, 240.0f, new OrthographicCamera()) );
        create();
        //backgroundText = new Texture
        skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));


        backgroundTex = new Texture("background.png");

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

        title = new Label("IZI POKER", skin);
        //title.setFontScale(10,10);
        title.setPosition(stage.getWidth() / 2, stage.getHeight() - title.getHeight(), Align.center); //* title.getFontScaleY());
        //title.setPosition(0,0);
        stage.addActor(title);

        startBtn = new TextButton("START GAME", skin);
        startBtn.setWidth(stage.getWidth() / 6);
        startBtn.setPosition(stage.getWidth() / 2, 3 * stage.getHeight() / 5, Align.center);
        stage.addActor(startBtn);

        exitBtn = new TextButton("EXIT", skin);
        exitBtn.setWidth(stage.getWidth() / 6);
        exitBtn.setPosition(stage.getWidth() / 2, 2 * stage.getHeight() / 5, Align.center);
        stage.addActor(exitBtn);

        Card c = new Card(Card.rankType.ACE, Card.suitType.CLUBS);
        c.setBounds(0,0,100,100);
        c.flip();
        stage.addActor(c);


        //Listeners
        startBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game g = IZIPokerDesktop.getInstance();
                g.setScreen(new CreateTableDesktop());
            }

            ;
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
        //stage = new Stage( new StretchViewport(400.0f, 300.0f, new OrthographicCamera()));
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
    }
}
