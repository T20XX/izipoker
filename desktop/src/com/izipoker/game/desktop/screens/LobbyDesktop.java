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
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.izipoker.game.Table;
import com.izipoker.game.desktop.IZIPokerDesktop;

/**
 * Created by Telmo on 03/05/2016.
 */
public class LobbyDesktop implements Screen{
    //GUI Variables
    private Stage stage;

    private Texture sliderBackground;
    private Texture sliderKnob;
    private Texture createTableTexUp, createTableTexDown;
    private Texture exitText;

    ImageButton createTableBtn;

    //Game variables
    private Table table;


    public LobbyDesktop(Table table) {
        //Game variables initialization
        this.table = table;
        //super( new StretchViewport(320.0f, 240.0f, new OrthographicCamera()) );
        create();
        //backgroundText = new Texture
        sliderBackground = new Texture("sliderBackground.png");
        sliderKnob = new Texture("sliderKnob.png");
        createTableTexUp = new Texture("startBtnUp.png");
        createTableTexDown = new Texture("startBtnDown.png");

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

        Image tmp1 = new Image(createTableTexUp);
        Image tmp2 = new Image(createTableTexDown);
        createTableBtn = new ImageButton(tmp1.getDrawable(), tmp2.getDrawable());
        createTableBtn.setPosition( stage.getWidth() / 2, 300f, Align.center);
        stage.addActor(createTableBtn);

        tmp1 = new Image(sliderBackground);
        tmp2 = new Image(sliderKnob);
        Slider.SliderStyle ss = new Slider.SliderStyle(tmp1.getDrawable(), tmp2.getDrawable());
        Slider s = new Slider(2f,8f,1,false,ss);
        s.setBounds(100,100,200,20);
        stage.addActor(s);



        //Listeners
        createTableBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game g = IZIPokerDesktop.getInstance();
            }

            ;
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
        sliderBackground.dispose();
        sliderKnob.dispose();
        exitText.dispose();
    }
}