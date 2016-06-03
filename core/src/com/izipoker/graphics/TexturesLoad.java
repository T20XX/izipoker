package com.izipoker.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Telmo on 31/05/2016.
 */
public class TexturesLoad {
    private static TexturesLoad ourInstance = new TexturesLoad();

    public static Texture backTex = new Texture("backCard.png");

    private static Texture cardsTex =  new Texture("cards.png");

    public static TextureRegion frontTex[][] = TextureRegion.split(cardsTex,cardsTex.getWidth()/13, cardsTex.getHeight()/4);;

    public static Texture avatarTotal = new Texture("avatar.jpg");

    public final static int MAX_AVATAR = 7;

    public static TextureRegion avatarTex[][] = TextureRegion.split(avatarTotal,avatarTotal.getWidth()/MAX_AVATAR, avatarTotal.getHeight());

    public static TexturesLoad getInstance() {
        return ourInstance;
    }

    private TexturesLoad() {
    }
}
