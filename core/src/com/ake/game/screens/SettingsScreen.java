package com.ake.game.screens;

import com.ake.game.AKEGame;
import com.ake.game.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class SettingsScreen extends BaseScreen{

    @Override
    public void initialize() {
        BaseActor background = new BaseActor(0, 0, uiStage);
        background.loadTexture("background1.jpg");
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setZIndex(0);
        
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("button.png"));
        textButtonStyle.up = new NinePatchDrawable(new NinePatch(buttonTex, 24, 24, 24, 24));
        textButtonStyle.font = AKEGame.getFontConfiguration(30f, new Color(0.55f, 0.24f, 0.46f, 1f));

        TextButtonStyle inactiveTextButtonStyle = new TextButtonStyle();
        inactiveTextButtonStyle.up = new NinePatchDrawable(new NinePatch(buttonTex, 24, 24, 24, 24));
        inactiveTextButtonStyle.font = AKEGame.getFontConfiguration(30f, new Color(0.82f, 0.91f, 0.42f, 1f));
        
        TextButton musicButton = new TextButton("Music: ON", textButtonStyle);
        musicButton.scaleBy(0.5f);
        musicButton.setColor(new Color(0.08f, 0.08f, 0.04f, 0.7f));
        uiStage.addActor(musicButton);

        TextButton debugButton = new TextButton("Debug: ON", textButtonStyle);
        debugButton.scaleBy(0.5f);
        debugButton.setColor(new Color(0.08f, 0.08f, 0.04f, 0.7f));
        uiStage.addActor(debugButton);

        TextButton backButton = new TextButton("Back", textButtonStyle);
        backButton.scaleBy(0.5f);
        backButton.setColor(new Color(0.08f, 0.08f, 0.04f, 0.7f));
        uiStage.addActor(backButton);

        musicButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                musicButton.setStyle(inactiveTextButtonStyle);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                musicButton.setStyle(textButtonStyle);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return false;
            }
        });

        debugButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                debugButton.setStyle(inactiveTextButtonStyle);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                debugButton.setStyle(textButtonStyle);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return false;
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                backButton.setStyle(inactiveTextButtonStyle);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                backButton.setStyle(textButtonStyle);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backToMenu();
                return false;
            }
        });

        uiTable.add(musicButton).pad(10f);
        uiTable.row();
        uiTable.add(debugButton).pad(10f);
        uiTable.row();
        uiTable.add(backButton).pad(10f);
    }

    private void backToMenu(){
        uiTable.addAction(Actions.sequence(Actions.run(() -> this.triggerFadeOut()), 
            Actions.run(() -> {
                AKEGame.setActiveScreen(new MenuScreen());
            }
        )));
    }

    @Override
    public void update(float dt) {}
}
