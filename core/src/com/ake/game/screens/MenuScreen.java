package com.ake.game.screens;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
        BaseActor title = new BaseActor(0,0, mainStage);
        title.loadTexture( "starfish-collector.png" );
        title.moveBy(0,100);
        
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("button.png"));
        textButtonStyle.up = new NinePatchDrawable(new NinePatch(buttonTex, 24, 24, 24, 24));
        textButtonStyle.font = AKEGame.getFontConfiguration(50f, Color.FIREBRICK);
        
        TextButton startButton = new TextButton("Start", textButtonStyle);
        uiStage.addActor(startButton);

        TextButton exitButton = new TextButton("Exit", textButtonStyle);
        uiStage.addActor(exitButton);
        
        startButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                startButton.setColor(Color.GRAY);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                startButton.setColor(Color.WHITE);
            }

            @Override
	        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                startGame();
                return false;
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitButton.setColor(Color.GRAY);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitButton.setColor(Color.WHITE);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return false;
            }
        });

        uiTable.add(title).pad(10f);
        uiTable.row();
        uiTable.add(startButton).pad(10f);
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

    public void update(float dt){}
    
    public boolean keyDown(int keycode){
        if (Gdx.input.isKeyPressed(Keys.ENTER))
            startGame();
        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit();
        return false;
    }
}
