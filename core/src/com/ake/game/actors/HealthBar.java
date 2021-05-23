package com.ake.game.actors;

import com.ake.game.screens.LevelScreen;
import com.ake.game.utils.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HealthBar extends ProgressBar {
    private Table healthBarLayout;
    private Label healthText;

    /**
     * @param width  of the health bar
     * @param height of the health bar
     */
    public HealthBar(int width, int height) {
        super(0f, 1f, 0.01f, false, new ProgressBarStyle());
        getStyle().background = Utils.getColoredDrawable(width, height, Color.RED);
        getStyle().knob = Utils.getColoredDrawable(0, height, Color.GREEN);
        getStyle().knobBefore = Utils.getColoredDrawable(width, height, Color.GREEN);

        setWidth(width);
        setHeight(height);

        setAnimateDuration(0.0f);
        setValue(1f);

        setAnimateDuration(0.25f);

        this.healthBarLayout = new Table();
        this.healthText = new Label("HP", LevelScreen.labelStyle);
        this.healthText.setFontScale(0.3f);
        this.healthText.setColor(new Color(0.8f, 0f, 0f, 1f));
        healthBarLayout.add(healthText);
        healthBarLayout.add(this);
    }

    public Table getHealthBar(){
        Action loading = Actions.sequence(Actions.alpha(0f), Actions.fadeIn(1f));
        this.healthBarLayout.addAction(loading);
        return this.healthBarLayout;
    }
}