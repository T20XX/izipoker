package com.izipoker.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Singleton class used to store textures and graphics related variables
 */
public class TexturesLoad {
    private static TexturesLoad ourInstance = new TexturesLoad();

    /**
     * Texture of the cards facing down
     */
    public static Texture backTex = new Texture("backCard.png");

    private static Texture cardsTex =  new Texture("cards.png");

    /**
     * 2D Array of 52 cards textures in total
     */
    public static TextureRegion frontTex[][] = TextureRegion.split(cardsTex,cardsTex.getWidth()/13, cardsTex.getHeight()/4);;

    /**
     * Texture with all avatars
     */
    private static Texture avatarTotal = new Texture("avatar.jpg");

    /**
     * Number of avatars
     */
    public final static int MAX_AVATAR = 7;

    /**
     * 2D Array of avatars texture
     * Use like [0][number of avatar]
     */
    public static TextureRegion avatarTex[][] = TextureRegion.split(avatarTotal,avatarTotal.getWidth()/MAX_AVATAR, avatarTotal.getHeight());


    public static BitmapFont font;

    static {
        font = new BitmapFont();
        font.setColor(1.0f, 1.0f, 1.0f,1.0f);
    }

    public static TexturesLoad getInstance() {
        return ourInstance;
    }

    private TexturesLoad() {
    }
}
