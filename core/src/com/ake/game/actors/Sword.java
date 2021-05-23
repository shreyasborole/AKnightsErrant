package com.ake.game.actors;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.ake.game.core.SpriteSheet;

class Sword extends Weapon {

    private boolean actionComplete;

    Sword(Hero hero, Stage s) {
        super(hero, s);

        // Item info
        setID(Items.SWORD);
        setIcon("icons/sword.png");

        this.damage = 6;
        this.weaponAnimation = new SpriteSheet("weapons/sword_attack.png", 1, 12, 0.05f);
        this.weaponAttackAnimation = this.weaponAnimation.getAnimation(PlayMode.NORMAL);

        this.setAnimation(this.weaponAttackAnimation);
        setAnimationPaused(true);
        // Action complete assumed to be true
        actionComplete = true;
    }

    @Override
    void attack() {
        // Resume animation
        setAnimationPaused(false);
        setAttack();
        float angleWithMouse = getMouseAngle();

        // Checking for flip animation and setting rotation of sword accordingly
        if (getFlipState())
            setRotation(angleWithMouse);
        else
            setRotation(angleWithMouse + 180f);

        // Reseting actions queue if queue size is zero; inorder to allow rotation in attack() block
        if(getActions().size > 0){
            getActions().clear();
            actionComplete = false;
        }

        // Delaying action complete flag; in order for the act() loop to run the idle rotation action
        addAction(Actions.sequence(Actions.delay(0.7f), Actions.run(() -> actionComplete = true)));
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        // Setting position of weapon w.r.t main character
        this.setPosition(hero.getX() - hero.getOriginX() - hero.getWidth() / 2 + 10f, hero.getY() - hero.getOriginY() - hero.getHeight() / 2 - 10f);

        // Reinitializing animation; redrawing the sword
        if (isAnimationFinished()) {
            resetAttack();
            resetAnimation();
            setAnimation(this.weaponAttackAnimation);
            setAnimationPaused(true);
        }

        // Fliping animation based on angle and adding idle rotation action
        float angleWithMouse = getMouseAngle();
        if (angleWithMouse >= 90f && angleWithMouse <= 270f) {
            flipAnimation(false);
            if(getActions().size == 0 && actionComplete){
                addAction(Actions.rotateTo(45f, 0.2f, Interpolation.bounce));
            }
        } else if(angleWithMouse < 90f || angleWithMouse > 270f){
            flipAnimation(true);
            if(getActions().size == 0 && actionComplete){
                addAction(Actions.rotateTo(335f, 0.2f, Interpolation.bounce));
            }
        }

        // OPTIONAL
        // Changing z-index of sword depending on sword angle; inorder to avoid overlap
        // if (getRotation() <= 315f && getRotation() >= 225f)
        //     hero.toFront();
        // else
        //     this.toFront();
    }
}
