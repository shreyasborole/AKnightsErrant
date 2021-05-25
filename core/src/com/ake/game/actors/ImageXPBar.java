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

public class ImageXPBar extends ProgressBar {
    private Table XPBarLayout;
    private Label XPText;
    private Label xpAddLabel;
    private TextureRegion leftBorder;
    private TextureRegion rightBorder;

    public ImageXPBar(int width, int height) {
        super(0f, 1f, 0.01f, false, new ProgressBarStyle());

        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("progressbar/xp_bar.atlas"));
        getStyle().background = new TextureRegionDrawable(textureAtlas.findRegion("background"));
        getStyle().knob = Utils.getColoredDrawable(0, height, Color.GREEN);
        getStyle().knobBefore = new TextureRegionDrawable(textureAtlas.findRegion("knobbefore"));

        leftBorder = textureAtlas.findRegion("left");
        rightBorder = textureAtlas.findRegion("right");

        setWidth(width);
        setHeight(height);

        setValue(0f);
        setAnimateDuration(0.25f);

        this.XPBarLayout = new Table();
        this.XPBarLayout.pad(10f);
        this.XPText = new Label("XP", LevelScreen.labelStyle);
        this.XPText.setFontScale(0.3f);
        this.XPText.setColor(new Color(0, 0f, 0.8f, 1f));

        this.xpAddLabel = new Label("+99", LevelScreen.labelStyle);
        this.xpAddLabel.setFontScale(0.3f);
        this.xpAddLabel.getColor().a = 0f;

        XPBarLayout.add(XPText);
        XPBarLayout.add(this);
        XPBarLayout.add(xpAddLabel);
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

    public Table getXPBar() {
        Action loading = Actions.sequence(Actions.alpha(0f), Actions.alpha(0.75f, 1f));
        this.XPBarLayout.addAction(loading);
        return this.XPBarLayout;
    }

    public void triggerXP(int xp) {
        xpAddLabel.setText("+ " + xp);
        Action show = Actions.sequence(Actions.color(Color.YELLOW, 0.1f), Actions.alpha(0.7f, 0.3f), Actions.color(new Color(1f, 1f, 1f, 0f)));
        xpAddLabel.addAction(show);
    }
}
