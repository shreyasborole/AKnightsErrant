package com.ake.game.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;

/**
 *  Created when program is launched; 
 *  manages the screens that appear during the game.
 */
public abstract class BaseGame extends Game
{
    private static int font_size = 72; 
    private static String font_style = null;
    /**
     *  Stores reference to game; used when calling setActiveScreen method.
     */
    private static BaseGame game;

    /**
     *  Called when game is initialized; stores global reference to game object.
     */
    public BaseGame() {        
        game = this;
    }

    /**
     * Called when game is initialized; sets global font properties like font size and font style.
     */
    protected static void setFont(int fontSize, String fontStyle){
        font_size = fontSize;
        font_style = fontStyle; 
    }

    /**
     * Called when game is initialized; sets global font properties like font size.
     */
    protected static void setFont(int fontSize) {
        font_size = fontSize;
    }

    /**
     * Called when game is initialized; sets global font properties like font style.
     */
    protected static void setFont(String fontStyle) {
        font_style = fontStyle;
    }

    /**
     * Called when game is initialized; sets global cursor icon. The image file should be powers of 2.
     */
    public static void setCursor(String cursor){
        Pixmap pm = new Pixmap(Gdx.files.internal(cursor));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
        pm.dispose();
    }

    
    /**
     *  Used to switch screens while game is running.
     *  Method is static to simplify usage.
     */
    public static void setActiveScreen(BaseScreen s)
    {
        game.setScreen(s);
    }

    /**
     * Used to get default font set for the game
     * @param fontScale set font scale between (0, 100] w.r.t font size
     * @param color set font color
     * @return BitmapFont
     */
    public static BitmapFont getFontConfiguration(float fontScale, Color color) {
        // Font Setup
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(font_style));
        FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
        fontParameter.size = font_size;
        fontParameter.color = color;
        fontParameter.minFilter = TextureFilter.Nearest;
        fontParameter.magFilter = TextureFilter.Nearest;
        BitmapFont bitmapFont = fontGenerator.generateFont(fontParameter);
        bitmapFont.getData().setScale(fontScale / 100f);
        return bitmapFont;
    }

    public static BitmapFont getFontConfiguration(float fontScale) {
        // Font Setup
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(font_style));
        FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
        fontParameter.size = font_size;
        fontParameter.color = Color.WHITE;
        fontParameter.minFilter = TextureFilter.Nearest;
        fontParameter.magFilter = TextureFilter.Nearest;
        BitmapFont bitmapFont = fontGenerator.generateFont(fontParameter);
        bitmapFont.getData().setScale(fontScale / 100f);
        return bitmapFont;
    }

    public static BitmapFont getFontConfiguration(Color color) {
        // Font Setup
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(font_style));
        FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
        fontParameter.size = font_size;
        fontParameter.color = color;
        fontParameter.minFilter = TextureFilter.Nearest;
        fontParameter.magFilter = TextureFilter.Nearest;
        BitmapFont bitmapFont = fontGenerator.generateFont(fontParameter);
        return bitmapFont;
    }

    public static BitmapFont getFontConfiguration() {
        // Font Setup
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(font_style));
        FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
        fontParameter.size = font_size;
        fontParameter.color = Color.WHITE;
        fontParameter.minFilter = TextureFilter.Nearest;
        fontParameter.magFilter = TextureFilter.Nearest;
        BitmapFont bitmapFont = fontGenerator.generateFont(fontParameter);
        return bitmapFont;
    }

    @Override
    public void create() {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);
    }
}
