package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.core.BaseActor;
import com.mygdx.game.core.SpriteSheet;

abstract class Weapon extends BaseActor{
    
    protected Hero hero;
    protected float damage;
    protected SpriteSheet weaponAnimation;
    protected Animation<TextureRegion> weaponAttackAnimation;
    protected boolean attackFlag;

    Weapon(Hero hero, Stage s) {
        super(0, 0, s);
        this.hero = hero;
        resetAttack();
    }

    protected void setAttack(){
        attackFlag = true;
    }

    protected void resetAttack(){
        attackFlag = false;
    }

    protected boolean isAttack() {
        return attackFlag;
    }

    abstract void attack();
}
