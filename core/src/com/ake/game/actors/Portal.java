package com.ake.game.actors;
import com.ake.game.core.*;
import com.ake.game.map.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Portal extends BaseActor{
    public enum Location{
        TOP,
        LEFT,
        BOTTOM,
        RIGHT
    }
    private Location relativeLocation;

    public Portal(Location location, float x, float y, Stage s) {
        super(x, y, s);
        setLocation(location);
        loadTexture("portal.png");
        setBoundaryRectangle();
    }

    private void setLocation(Location location){
        switch(location){
            case BOTTOM:
                setPosition(TileMapRenderer.mapWidth / 2, 0 + 1 * TileMapRenderer.tileSize);
                break;
            case LEFT:
                setPosition(0 + 1 * TileMapRenderer.tileSize, TileMapRenderer.mapHeight / 2);
                break;
            case RIGHT:
                setPosition(TileMapRenderer.mapWidth - 2 * TileMapRenderer.tileSize, TileMapRenderer.mapHeight / 2);
                break;
            case TOP:
                setPosition(TileMapRenderer.mapWidth / 2, TileMapRenderer.mapHeight - 2 * TileMapRenderer.tileSize);
                break;
            default:
                return;
        }
        this.relativeLocation = location;
    }

    public Location getLocation(){
        return this.relativeLocation;
    }
    
}
