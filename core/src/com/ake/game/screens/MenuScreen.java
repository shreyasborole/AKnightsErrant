package com.ake.game.screens;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.ake.game.*;
import com.ake.game.core.*;
import com.ake.game.map.MapState;
import com.ake.game.utils.*;

import java.time.Instant;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class MenuScreen extends BaseScreen
{
    
    public void initialize(){
        BaseActor background = new BaseActor(0, 0, uiStage);
        background.loadTexture("background1.jpg");
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setZIndex(0);

        BaseActor title = new BaseActor(0,0, uiStage);
        title.loadTexture("A-Knights-Errant.png");
        title.scaleBy(0.5f);
        
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("button.png"));
        textButtonStyle.up = new NinePatchDrawable(new NinePatch(buttonTex, 24, 24, 24, 24));
        textButtonStyle.font = AKEGame.getFontConfiguration(30f, new Color(0.55f, 0.24f, 0.46f, 1f));

        TextButtonStyle inactiveTextButtonStyle = new TextButtonStyle();
        inactiveTextButtonStyle.up = new NinePatchDrawable(new NinePatch(buttonTex, 24, 24, 24, 24));
        inactiveTextButtonStyle.font = AKEGame.getFontConfiguration(30f, new Color(0.82f, 0.91f, 0.42f, 1f));
        
        TextButton startButton = new TextButton("Start", textButtonStyle);
        startButton.scaleBy(0.5f);
        startButton.setColor(new Color(0.08f, 0.08f, 0.04f, 0.7f));
        uiStage.addActor(startButton);

        TextButton settingsButton = new TextButton("Settings", textButtonStyle);
        settingsButton.scaleBy(0.5f);
        settingsButton.setColor(new Color(0.08f, 0.08f, 0.04f, 0.7f));
        uiStage.addActor(settingsButton);

        TextButton exitButton = new TextButton("Exit", textButtonStyle);
        exitButton.scaleBy(0.5f);
        exitButton.setColor(new Color(0.08f, 0.08f, 0.04f, 0.7f));
        uiStage.addActor(exitButton);
        
        startButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                startButton.setStyle(inactiveTextButtonStyle);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                startButton.setStyle(textButtonStyle);
            }

            @Override
	        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                startGame();
                return false;
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
               settingsButton.setStyle(inactiveTextButtonStyle);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
               settingsButton.setStyle(textButtonStyle);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                openSettings();
                return false;
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitButton.setStyle(inactiveTextButtonStyle);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitButton.setStyle(textButtonStyle);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                exitGame();
                return false;
            }
        });

        uiTable.add(title).pad(50f);
        uiTable.row();
        uiTable.add(startButton).pad(10f);
        uiTable.row();
        uiTable.add(settingsButton).pad(10f);
        uiTable.row();
        uiTable.add(exitButton).pad(10f);
        
        
        Debug.endTime = Instant.now();
		Debug.clearScreen();
        Debug.measureTime("Menu Screen loaded in");
    }

    private void startGame(){
        MapState.initialize(new Random().nextLong());
        uiTable.reset();
        AKEGame.setActiveScreen(new LevelScreen());
    }

    private void openSettings(){
        this.hide();
        uiTable.addAction(Actions.sequence(Actions.run(() -> this.triggerFadeOut()), 
            Actions.run(() -> {
                AKEGame.setActiveScreen(new SettingsScreen());
            }
        )));
    }

    private void exitGame(){
        uiTable.addAction(Actions.sequence(Actions.run(() -> this.triggerFadeOut()), Actions.delay(0.1f), Actions.run(() -> {
            Gdx.app.exit();
        })));
    }

    public void update(float dt){}
    
    public boolean keyDown(int keycode){
        if (Gdx.input.isKeyPressed(Keys.ENTER))
            startGame();
        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit();
        return false;
    }
}
