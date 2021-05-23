package com.ake.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ake.game.screens.LevelScreen;
import com.ake.game.utils.*;

public class ImageHealthBar extends ProgressBar{
    private Table healthBarLayout;
    private Label healthText;
    private TextureRegion leftBorder;
    private TextureRegion rightBorder;

    public ImageHealthBar(int width, int height){
        super(0f, 1f, 0.01f, false, new ProgressBarStyle());

        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("progressbar/health_bar.atlas"));
        getStyle().background = new TextureRegionDrawable(textureAtlas.findRegion("background"));
        getStyle().knob = Utils.getColoredDrawable(0, height, Color.GREEN);
        getStyle().knobBefore = new TextureRegionDrawable(textureAtlas.findRegion("knobbefore"));

        leftBorder = textureAtlas.findRegion("left");
        rightBorder = textureAtlas.findRegion("right");

        setWidth(width);
        setHeight(height);

        setValue(1f);
        setAnimateDuration(0.25f);
        
        this.healthBarLayout = new Table();
        this.healthText = new Label("HP", LevelScreen.labelStyle);
        this.healthText.setFontScale(0.3f);
        this.healthText.setColor(new Color(0.8f, 0f, 0f, 1f));
        healthBarLayout.add(healthText);
        healthBarLayout.add(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // First draw the left border.
        batch.draw(leftBorder, getX(), getY());
        // Save variables to restore their state after drawing
        float prevX = getX();
        float prevWidth = getWidth();
        // Set the variables which are used to draw the background
        setX(prevX + leftBorder.getRegionWidth());
        setWidth(prevWidth - leftBorder.getRegionWidth() - rightBorder.getRegionWidth());
        // Draw the progress bar as it would be without borders
        super.draw(batch, parentAlpha);
        // Set the variables to draw the right border
        setX(getX() + getWidth());
        // Draw the right border
        batch.draw(rightBorder, getX(), getY());
        // Reset the state of the variables so next cycle the drawing is done at correct
        // position
        setX(prevX);
        setWidth(prevWidth);
    }

    public Table getHealthBar() {
        Action loading = Actions.sequence(Actions.alpha(0f), Actions.alpha(0.75f, 1f));
        this.healthBarLayout.addAction(loading);
        return this.healthBarLayout;
    }
}
