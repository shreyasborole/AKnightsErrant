package com.ake.game.actors;

import com.ake.game.core.*;
import com.ake.game.map.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class Hero extends BaseActor {
    public enum Location {
        CENTER, TOP, BOTTOM, RIGHT, LEFT
    };

    public static final float DIRECTION_NORTH = 90f;
    public static final float DIRECTION_SOUTH = 270f;
    public static final float DIRECTION_EAST = 0f;
    public static final float DIRECTION_WEST = 180f;
    private float facingAngle;
    private final int totalHealth = 20;
    private final int dashLimit = 4;

    private int currentHealth;
    private int dashCount;

    private Array<SpriteSheet> spriteSheets;
    private Animation<TextureRegion> run;
    private Animation<TextureRegion> idle;

    private Weapon weapon;
    private ImageHealthBar healthBar;

    Location location = Hero.Location.CENTER;

    public Hero(Location location, Stage s) {
        super(TileMapRenderer.mapWidth / 2, TileMapRenderer.mapHeight / 2, s);
        this.spriteSheets = new Array<>();
        this.spriteSheets.add(new SpriteSheet("hero/run.png", 1, 8, 0.1f));
        this.spriteSheets.add(new SpriteSheet("hero/idle.png", 1, 12, 0.2f));

        this.run = this.spriteSheets.get(0).getAnimation(PlayMode.LOOP_PINGPONG);
        this.idle = this.spriteSheets.get(1).getAnimation(PlayMode.LOOP_PINGPONG);
        
        setAnimation(this.idle);
        setSize(42f, 66f);
        setLocation(location);
        this.facingAngle = DIRECTION_EAST;

        this.weapon = new Sword(this, s);
        this.currentHealth = totalHealth;
        this.healthBar = new ImageHealthBar(500, 20);

        this.dashCount = 0;

        setBoundaryPolygon(8);
        setAcceleration(1000);
        setMaxSpeed(300);
        setDeceleration(800);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        float angleWithMouse = getMouseAngle();

        if (getSpeed() == 0) {
            // Set idle state animation
            if (angleWithMouse >= 90f && angleWithMouse <= 270f) {
                flipAnimation(false);
                setAnimation(this.idle);
            }else if (angleWithMouse < 90f || angleWithMouse > 270f){
                flipAnimation(true);
                setAnimation(this.idle);
            }
        } else {
            // Set direction animation
            if(angleWithMouse >= 90f && angleWithMouse <= 270f){
                flipAnimation(false);
                setAnimation(this.run);
            }else if (angleWithMouse < 90f || angleWithMouse > 270f){
                flipAnimation(true);
                setAnimation(this.run);
            }
        }

        setOrigin(Align.bottomRight);
        setSize(42f, 66f);

        // hero movement controls
        if (Gdx.input.isKeyPressed(Keys.LEFT)){
            accelerateAtAngle(DIRECTION_WEST);
            this.facingAngle = DIRECTION_WEST;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)){
            accelerateAtAngle(DIRECTION_EAST);
            this.facingAngle = DIRECTION_EAST;
        }
        if (Gdx.input.isKeyPressed(Keys.UP)){
            accelerateAtAngle(DIRECTION_NORTH);
            this.facingAngle = DIRECTION_NORTH;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)){
            accelerateAtAngle(DIRECTION_SOUTH);
            this.facingAngle = DIRECTION_SOUTH;
        }

        if (Gdx.input.isKeyPressed(Keys.A)) {
            accelerateAtAngle(DIRECTION_WEST);
            this.facingAngle = DIRECTION_WEST;
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            accelerateAtAngle(DIRECTION_EAST);
            this.facingAngle = DIRECTION_EAST;
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            accelerateAtAngle(DIRECTION_NORTH);
            this.facingAngle = DIRECTION_NORTH;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            accelerateAtAngle(DIRECTION_SOUTH);
            this.facingAngle = DIRECTION_SOUTH;
        }

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            int dash = 100;
            getActions().clear();
            if(dashCount == dashLimit){
                addAction(Actions.sequence(Actions.delay(10f), Actions.run(() -> dashCount = 0)));
            }
            else{
                if (this.facingAngle == DIRECTION_NORTH)
                    addAction(Actions.moveBy(0f, dash, 0.3f));
                if (this.facingAngle == DIRECTION_WEST)
                    addAction(Actions.moveBy(-1f * dash, 0, 0.3f));
                if (this.facingAngle == DIRECTION_SOUTH)
                    addAction(Actions.moveBy(0, -1f * dash, 0.3f));
                if (this.facingAngle == DIRECTION_EAST){
                    addAction(Actions.moveBy(dash, 0, 0.3f));
                }
                ++dashCount;
            }
        }
        
        alignCamera();
        boundToWorld();
        applyPhysics(dt);
    }

    public void setLocation(Location location) {
        this.location = location;
        switch (this.location) {
        case TOP:
            setPosition(TileMapRenderer.mapWidth / 2, TileMapRenderer.mapHeight - 5 * TileMapRenderer.tileSize);
            break;
        case CENTER:
            setPosition(TileMapRenderer.mapWidth / 2, TileMapRenderer.mapHeight / 2);
            this.moveBy(-1 * (this.spriteSheets.get(0).getFrameWidth() / 4), -1 * (this.spriteSheets.get(0).getFrameWidth() / 4));
            break;
        case BOTTOM:
            setPosition(TileMapRenderer.mapWidth / 2, 0 + 3 * TileMapRenderer.tileSize);
            break;
        case RIGHT:
            setPosition(TileMapRenderer.mapWidth - 5 * TileMapRenderer.tileSize, TileMapRenderer.mapHeight / 2);
            break;
        case LEFT:
            setPosition(0 + 3 * TileMapRenderer.tileSize, TileMapRenderer.mapHeight / 2);
            break;
        default:
            break;
        }
    }

    public Weapon getWeapon(){
        return this.weapon;
    }

    public Table getHealthBar(){
        return this.healthBar.getHealthBar();
    }

    public void attack() {
        this.weapon.attack();
    }

    public void attacked(int damage){
        currentHealth -= damage;
        this.healthBar.setValue(MathUtils.map(0, totalHealth, 0f, 1f, currentHealth));
    }

    public boolean isAttack(){
        return this.weapon.isAttack();
    }

    public boolean isDead(){
        return currentHealth <= 0;
    }
}
