package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.core.BaseActor;
import com.mygdx.game.core.SpriteSheet;

public class NPCTest extends BaseActor{

    public NPCTest(float x, float y, Stage s) {
        super(x, y, s);
        SpriteSheet spriteSheet = new SpriteSheet("hero/idle.png", 1, 12, 0.4f);
        setAnimation(spriteSheet.getAnimation(PlayMode.LOOP_PINGPONG));
        setSize(42f, 66f);
        setBoundaryPolygon(8);
        setAcceleration(800);
        setMaxSpeed(200);
        setDeceleration(400);
    } 
    
}
