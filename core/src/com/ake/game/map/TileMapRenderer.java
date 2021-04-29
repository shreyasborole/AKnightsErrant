package com.ake.game.map;

import com.ake.game.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class TileMapRenderer extends Actor{
    private MapGenerator mapGenerator;
    private TiledMap tiledMap;
    private OrthographicCamera tiledCamera;
    private OrthoCachedTiledMapRenderer tiledMapRenderer;
    private int[] layers = new int[]{0, 1, 2};

    public static int mapHeight;
    public static int mapWidth;
    public static int tileSize;

    public TileMapRenderer(Stage stage, int seed){
        this.mapGenerator = new MapGenerator(seed);
        this.tiledMap = this.mapGenerator.generateMap();

        mapHeight = this.mapGenerator.getMapHeight();
        mapWidth = this.mapGenerator.getMapWidth();
        tileSize = this.mapGenerator.getTileSize();
        BaseActor.setWorldBounds(mapWidth, mapHeight);
        
        this.tiledMapRenderer = new OrthoCachedTiledMapRenderer(this.tiledMap, 1f, 5000);
        this.tiledMapRenderer.setBlending(true);
        this.tiledCamera = new OrthographicCamera();
        this.tiledCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.tiledCamera.update();

        stage.addActor(this);
    }

    public int getSeed(){
        return this.mapGenerator.getSeed();
    }

    public Array<Vector2> getSolidObjects(){
        return this.mapGenerator != null ? this.mapGenerator.getSolidObjects() : null;
    }

    public MapGenerator getMapGenerator(){
        return this.mapGenerator;
    }

    public void act(float dt){
        super.act(dt);
    }

    public void draw(Batch batch, float parentAlpha){
        // Adjust tilemap camera to stay in sync with the main camera
        Camera mainCamera = getStage().getCamera();
        this.tiledCamera.position.x = mainCamera.position.x;
        this.tiledCamera.position.y = mainCamera.position.y;
        this.tiledCamera.update();
        this.tiledMapRenderer.setView(this.tiledCamera);

        batch.end();
        this.tiledMapRenderer.render(this.layers);
        batch.begin();
    }

    public void dispose(){
        this.tiledMap.dispose();
    }
}
