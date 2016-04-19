package com.foradhoras.izipoker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class IZIPoker extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture testcard;
	TextureRegion card;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		testcard = new Texture("ace.png");
		card = new TextureRegion(testcard);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(testcard, 0, 0, 650, 1000, 0, 0, testcard.getWidth(), testcard.getHeight(), false, false);
		batch.draw(card, card.getRegionHeight() * 2, 0, 0, 0, card.getRegionWidth(), card.getRegionHeight(), 2, 2, 90);
		batch.end();
	}
}