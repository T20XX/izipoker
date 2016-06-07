package com.izipoker.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Singleton class used to store textures and graphics related variables
 */
public class TexturesLoad {

    public static TexturesLoad getInstance() {
        return ourInstance;
    }
    /**
     * Number of avatars
     */
    public final static int MAX_AVATAR = 16;

    /**
     * Texture of the cards facing down
     */
    public static Texture backTex = new Texture("backCard.png");
    /**
     * Texture of the table
     */
    public static Texture tableTex = new Texture("table.png");
    /**
     * Font used to draw in batch (override draw method)
     */
    public static BitmapFont font;
    /**
     * Skin used in all game to create all GUI components
     */
    public static Skin skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));
    /**
     * Texture of background table that appear in most menus
     */
    public static Texture backgroundTex = new Texture("background.png");
    /**
     * Select Rectangle used to show the current player playing
     */
    public static ShapeRenderer selectRectangle = new ShapeRenderer();
    private static TexturesLoad ourInstance = new TexturesLoad();
    private static Texture cardsTex = new Texture("cards.png");
    /**
     * 2D Array of 52 cards textures in total
     */
    public static TextureRegion frontTex[][] = TextureRegion.split(cardsTex, cardsTex.getWidth() / 13, cardsTex.getHeight() / 4);
    /**
     * Texture with all avatars
     */
    private static Texture avatarTotal = new Texture("avatar.png");
    /**
     * 2D Array of avatars texture
     * Use like [0][number of avatar]
     */
    public static TextureRegion avatarTex[][] = TextureRegion.split(avatarTotal, avatarTotal.getWidth() / MAX_AVATAR, avatarTotal.getHeight());

    static {
        font = new BitmapFont();
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private TexturesLoad() {
    }
}
