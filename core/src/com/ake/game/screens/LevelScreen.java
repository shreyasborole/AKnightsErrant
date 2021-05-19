package com.ake.game.screens;

import com.ake.game.*;
import com.ake.game.core.*;
import com.ake.game.actors.*;
import com.ake.game.map.*;
import com.ake.game.utils.Debug;

import java.time.Instant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;


public class LevelScreen extends BaseScreen {
    private LabelStyle labelStyle;
    private Label fpsLabel;
    private Label seedLabel;

    private TileMapRenderer mapRenderer;
    private Hero hero;
    private Hotbar hotbar;
    private MiniMap miniMap;

    public void initialize() {
        // Label and font setup
        this.labelStyle = new LabelStyle();
        this.labelStyle.font = AKEGame.getFontConfiguration();

        // Map
        this.mapRenderer = new TileMapRenderer(mainStage, MapState.currentNode.getSeed());
        
        Array<Vector2> solidObjects = this.mapRenderer.getSolidObjects();
        for(Vector2 obj : solidObjects){
            new Solid(obj.x, obj.y, TileMapRenderer.tileSize, TileMapRenderer.tileSize, mainStage);
        }
        MapState.setPortals(mainStage);
        
        // Actors
        new Rock(200, 150, mainStage);
        new Rock(100, 300, mainStage);
        new Rock(300, 350, mainStage);
        new Rock(450, 200, mainStage);

        // Hero
        this.hero = new Hero(MapState.heroLocation, mainStage);

        new NPCTest(this.hero.getX() + 100f, this.hero.getY() + 100f, mainStage);
        
        // FPS Label
        this.fpsLabel = new Label("FPS: ", this.labelStyle);
        this.fpsLabel.setFontScale(0.3f);
        this.fpsLabel.setColor(new Color(1f, 1f, 1f, 0.7f));
        uiStage.addActor(this.fpsLabel);

        // Seed Label
        this.seedLabel = new Label("Seed: " + this.mapRenderer.getSeed(), this.labelStyle);
        this.seedLabel.setFontScale(0.2f);
        this.seedLabel.setColor(new Color(1f, 1f, 0f, 0.7f));
        uiStage.addActor(this.seedLabel);

        // Hotbar and Minimap setup
        this.hotbar = new Hotbar(uiStage);
        this.miniMap = new MiniMap(uiStage);

        // UI building for HUD
        hudTable.pad(20f);
        hudTable.add();
        hudTable.add().expandX().expandY();
        hudTable.row();
        hudTable.add();
        hudTable.add(hotbar.getHotbar()).center();
        hudTable.add(miniMap.getMiniMap()).right().bottom().pad(5f);

        // UI building for debug UI
        uiTable.pad(10f);
        uiTable.add(fpsLabel).left().top();
        uiTable.add().expandX().expandY();
        uiTable.row();
        uiTable.add(seedLabel).left().bottom();

        Debug.endTime = Instant.now();
        Debug.measureTime("Level Screen loaded in");
    }
    
    public void update(float dt) {
        this.fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());

        for(BaseActor NPCActor : BaseActor.getList(mainStage, "NPCTest")){
            this.hero.preventOverlap(NPCActor);
            if(this.hero.isAttack()){
                if(hero.isWithinDistance(25f, NPCActor)){
                    final float angle = this.hero.getMotionAngle();
                    final float magnitude = 150f;
                    NPCActor.getActions().clear();
                    NPCActor.addAction(Actions.moveBy(magnitude * MathUtils.cosDeg(angle), magnitude * MathUtils.sinDeg(angle), 0.6f, Interpolation.pow2InInverse));
                    System.out.println("NPC is within range");
                }
            }
        }

        for(BaseActor solidActor : BaseActor.getList(mainStage, "Solid")){
            this.hero.preventOverlap(solidActor);
            for (BaseActor NPCActor : BaseActor.getList(mainStage, "NPCTest"))
                NPCActor.preventOverlap(solidActor);

        }

        for (BaseActor rockActor : BaseActor.getList(mainStage, "Rock"))
            this.hero.preventOverlap(rockActor);
        
        for (BaseActor portalActor : BaseActor.getList(mainStage, "Portal")){
            if(this.hero.overlaps(portalActor)){
                if (this.mapRenderer != null) {
                    this.mapRenderer.dispose();
                }
                Portal portal = (Portal) portalActor;
                MapState.changeState(portal.getLocation());
            }
        }

        if(Gdx.input.isKeyJustPressed(Keys.L)){
            this.hotbar.addItem(hero.getWeapon());
        }
 
        if(Gdx.input.isKeyPressed(Keys.R)) {
            if (this.mapRenderer != null) {
                this.mapRenderer.dispose();
            }
            MapState.heroLocation = Hero.Location.CENTER;
            AKEGame.setActiveScreen(new LevelScreen());
        }

        if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
            Gdx.app.exit();
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == Buttons.LEFT){
            this.hero.attack();
            return true;
        }
        return false;
    }
    
}
