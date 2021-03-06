package com.izipoker.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.izipoker.game.Hand;
import com.izipoker.game.PokerAction;
import com.izipoker.graphics.TexturesLoad;
import com.izipoker.network.PokerClient;
import com.izipoker.network.ServerInterface;


public class GameAndroid implements Screen {
    private Stage stage;

    private TextButton foldBtn, callBtn, raiseBtn, checkBtn;
    private Slider betSlider;

    private Hand hand;
    private TextButton thirdMoney, halfMoney, allIn, sendBtn;
    private Label amountLbl, nameLbl;
    private TextField chatTF, betTF;
    private Image avatarImg;

    private ServerInterface proxyTable;
    private PokerClient listener;
    private String name;

    public GameAndroid(String name, ServerInterface proxyTable, PokerClient listener) {

        create();

        this.name = name;
        this.proxyTable = proxyTable;
        this.listener = listener;

        buildStage();

    }

    public void buildStage() {
        //Actors
        Image tmp1 = new Image(TexturesLoad.backgroundTex);
        tmp1.setSize(stage.getWidth() * 2, stage.getHeight());
        tmp1.setPosition(0, 0);
        stage.addActor(tmp1);


        avatarImg = new Image(TexturesLoad.avatarTex[0][listener.getAvatarID()]);
        avatarImg.setSize(stage.getWidth() / 3, stage.getWidth() / 3);
        avatarImg.setPosition(0, stage.getHeight(), Align.topLeft);
        stage.addActor(avatarImg);

        amountLbl = new Label(Integer.toString(listener.getMoney()), TexturesLoad.skin);
        amountLbl.setWidth(stage.getWidth() / 2);
        amountLbl.setPosition(stage.getWidth(), stage.getHeight() - amountLbl.getHeight(), Align.center);
        stage.addActor(amountLbl);

        nameLbl = new Label(listener.getName(), TexturesLoad.skin);
        nameLbl.setWidth(stage.getWidth() / 2);
        nameLbl.setPosition(stage.getWidth() / 2 + nameLbl.getHeight(), stage.getHeight() - nameLbl.getHeight(), Align.center);
        stage.addActor(nameLbl);

        chatTF = new TextField("", TexturesLoad.skin);
        chatTF.setMessageText("Chat ...");
        chatTF.setWidth(3 * stage.getWidth() / 4);
        chatTF.setPosition(0, stage.getHeight() / 2, Align.left);
        stage.addActor(chatTF);

        sendBtn = new TextButton("SEND", TexturesLoad.skin);
        sendBtn.setWidth(stage.getWidth() / 4);
        sendBtn.setHeight(chatTF.getHeight());
        sendBtn.setPosition(stage.getWidth(), stage.getHeight() / 2, Align.right);
        stage.addActor(sendBtn);

        betSlider = new Slider(listener.getHighestBet(), listener.getMoney(), 10, true, TexturesLoad.skin);
        betSlider.setWidth(stage.getWidth() / 6);
        betSlider.setHeight(stage.getHeight() / 3);
        betSlider.setPosition(stage.getWidth() - betSlider.getWidth() / 2, stage.getHeight() / 6, Align.center);
        stage.addActor(betSlider);

        foldBtn = new TextButton("FOLD", TexturesLoad.skin);
        foldBtn.setWidth(stage.getWidth() / 3);
        foldBtn.setPosition(stage.getWidth() / 4, stage.getHeight() / 4 - 2 * foldBtn.getHeight(), Align.center);
        stage.addActor(foldBtn);

        callBtn = new TextButton("CALL", TexturesLoad.skin);
        callBtn.setWidth(stage.getWidth() / 3);
        callBtn.setPosition(stage.getWidth() / 4, stage.getHeight() / 4 + (2) * callBtn.getHeight(), Align.center);
        stage.addActor(callBtn);

        checkBtn = new TextButton("CHECK", TexturesLoad.skin);
        checkBtn.setWidth(stage.getWidth() / 3);
        checkBtn.setPosition(stage.getWidth() / 4, stage.getHeight() / 4, Align.center);
        stage.addActor(checkBtn);


        raiseBtn = new TextButton("RAISE", TexturesLoad.skin);
        raiseBtn.setWidth(stage.getWidth() / 3);
        raiseBtn.setPosition(3 * stage.getWidth() / 5, stage.getHeight() / 4 + 2 * raiseBtn.getHeight(), Align.center);
        stage.addActor(raiseBtn);

        betTF = new TextField(betSlider.getMinValue() + "", TexturesLoad.skin);
        betTF.setWidth(stage.getWidth() / 6);
        betTF.setPosition(7 * stage.getWidth() / 8, stage.getHeight() / 4 + 2 * raiseBtn.getHeight(), Align.center);
        stage.addActor(betTF);


        thirdMoney = new TextButton("1/3", TexturesLoad.skin);
        thirdMoney.setWidth(stage.getWidth() / 6);
        thirdMoney.setPosition(7 * stage.getWidth() / 10, stage.getHeight() / 4 + thirdMoney.getHeight() / 2, Align.center);
        stage.addActor(thirdMoney);

        halfMoney = new TextButton("1/2", TexturesLoad.skin);
        halfMoney.setWidth(stage.getWidth() / 6);
        halfMoney.setPosition(7 * stage.getWidth() / 10, thirdMoney.getY() - thirdMoney.getHeight(), Align.center);
        stage.addActor(halfMoney);

        allIn = new TextButton("MAX", TexturesLoad.skin);
        allIn.setWidth(stage.getWidth() / 6);
        allIn.setPosition(7 * stage.getWidth() / 10, halfMoney.getY() - halfMoney.getHeight(), Align.center);
        stage.addActor(allIn);

        allIn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int money = listener.getMoney();
                if (betSlider.getMinValue() <= money && betSlider.getMaxValue() >= money)
                    betSlider.setValue(money);
            }
        });
        thirdMoney.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int money = listener.getMoney() / 3;
                if (betSlider.getMinValue() <= money && betSlider.getMaxValue() >= money)
                    betSlider.setValue(money);

            }
        });
        halfMoney.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int money = listener.getMoney() / 2;
                if (betSlider.getMinValue() <= money && betSlider.getMaxValue() >= money)
                    betSlider.setValue(money);

            }
        });
        foldBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                proxyTable.sendPokerAction(name, new PokerAction(PokerAction.actionType.FOLD));
                disableActionButtons();
                listener.resetPossibleActions();
            }
        });

        callBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                proxyTable.sendPokerAction(name, new PokerAction(PokerAction.actionType.CALL));
                disableActionButtons();
                listener.resetPossibleActions();
            }

        });

        raiseBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                proxyTable.sendPokerAction(name, new PokerAction(PokerAction.actionType.RAISE, (int) betSlider.getValue()));
                disableActionButtons();
                listener.resetPossibleActions();

            }

        });
        checkBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                proxyTable.sendPokerAction(name, new PokerAction(PokerAction.actionType.CHECK));
                disableActionButtons();
                listener.resetPossibleActions();
            }

        });

        sendBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                proxyTable.tell(name, chatTF.getText());
                chatTF.setText("");
            }
        });

        betSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                betTF.setText(String.valueOf((int) betSlider.getValue()));
            }
        });
        betTF.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    betSlider.setValue(Integer.valueOf(betTF.getText()));
                } catch (Exception e) {

                }

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
        Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (listener.isChanged()) {
            updateChanges();

        }

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

    public void disableActionButtons() {
        foldBtn.setVisible(false);
        checkBtn.setVisible(false);
        callBtn.setVisible(false);
        raiseBtn.setVisible(false);
        betTF.setVisible(false);
        betSlider.setVisible(false);
        thirdMoney.setVisible(false);
        halfMoney.setVisible(false);
        allIn.setVisible(false);
    }

    public void updateChanges() {

        if (listener.getHand() != null) {
            if (listener.getState() != PokerClient.clientState.PLAYING) {
                Dialog resultDialog = new Dialog("End", TexturesLoad.skin);
                resultDialog.text(listener.getState().toString());
                resultDialog.button(new TextButton("Close", TexturesLoad.skin)).addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Gdx.app.exit();
                    }
                });
                resultDialog.show(stage);
            }

            if (hand != listener.getHand()) {
                if (hand != null) {
                    hand.getCards()[0].remove();
                    hand.getCards()[1].remove();
                }
                try {
                    hand = listener.getHand();
                    hand.getCards()[0].setBounds(stage.getWidth() / 4, stage.getHeight() - stage.getHeight() / 3, stage.getWidth() / 4, stage.getHeight() / 5);
                    stage.addActor(hand.getCards()[0]);
                    hand.getCards()[1].setBounds(stage.getWidth() / 2, stage.getHeight() - stage.getHeight() / 3, stage.getWidth() / 4, stage.getHeight() / 5);
                    stage.addActor(hand.getCards()[1]);
                } catch (Exception e) {

                }
            }
        }
        //Actions Buttons update
        foldBtn.setVisible(listener.getPossibleActions()[0]);
        checkBtn.setVisible(listener.getPossibleActions()[1]);
        callBtn.setVisible(listener.getPossibleActions()[2]);
        raiseBtn.setVisible(listener.getPossibleActions()[3]);
        betTF.setVisible(listener.getPossibleActions()[3]);
        betSlider.setVisible(listener.getPossibleActions()[3]);
        if (listener.getHighestBet() < listener.getMoney())
            betSlider.setRange(listener.getHighestBet(), listener.getMoney());
        else betSlider.setRange(listener.getMoney(), listener.getMoney());
        thirdMoney.setVisible(listener.getPossibleActions()[3]);
        halfMoney.setVisible(listener.getPossibleActions()[3]);
        allIn.setVisible(listener.getPossibleActions()[3]);

        //Amount update
        if (listener.getMoney() > 0) {
            amountLbl.setText(Integer.toString(listener.getMoney()));
        }
        listener.setChanged(false);
    }
}
