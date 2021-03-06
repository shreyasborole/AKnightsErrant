package com.ake.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ake.game.map.MapState;

public class MiniMap extends Actor{
    private Table table;
    private Image[][] imageNodes;

    private final Texture setState = new Texture(Gdx.files.internal("available.png"));
    private final Texture unsetState = new Texture(Gdx.files.internal("not available.png"));
    private final Texture currentState = new Texture(Gdx.files.internal("set.png"));

    private final boolean withBorderMiniMap = false;

    public MiniMap(Stage stage) {
        this.table = new Table();
        this.imageNodes = new Image[MapState.worldSize][MapState.worldSize];

        if(withBorderMiniMap){
            for(int i = 0; i < MapState.worldSize; i++){
                for(int j = 0; j < MapState.worldSize; j++){
                    setState(i, j, unsetState);
                }
            }
        }

        // Setting correct states
        for (int i = 0; i < MapState.worldSize; i++) {
            for (int j = 0; j < MapState.worldSize; j++) {
                int x = MapState.currentNode.getX();
                int y = MapState.currentNode.getY();
                if(MapState.worldMiniMap[i][j] == 1){
                    setState(i, j, setState);
                }
                if (x == i && y == j){
                    setState(i, j, currentState);
                    if (MapState.worldGenerator.getLeft(x, y) != null){
                        setState(i, j - 1, setState);
                    }
                    if (MapState.worldGenerator.getBottom(x, y) != null){
                        setState(i + 1, j, setState);
                    }
                    if (MapState.worldGenerator.getRight(x, y) != null){
                        setState(i, j + 1, setState);
                    }
                    if (MapState.worldGenerator.getTop(x, y) != null){
                        setState(i - 1, j, setState);
                    }
                }
            }
        }

        // Constructing the table
        for (int i = 0; i < MapState.worldSize; i++) {
            for (int j = 0; j < MapState.worldSize; j++) {
                this.table.add(this.imageNodes[i][j]).pad(2f);
            }
            this.table.row();
        }
        stage.addActor(this.table);
    }

    @Override
    public void act(float dt){
        super.act(dt);
    }

    private void setState(int x, int y, Texture texture){
        if(this.imageNodes[x][y] != null){
            this.imageNodes[x][y].setDrawable(new TextureRegionDrawable(texture));
            MapState.worldMiniMap[x][y] = texture.equals(unsetState) ? 0 : 1;
        }else{
            this.imageNodes[x][y] = new Image(texture);
            this.imageNodes[x][y].setColor(1f, 1f, 1f, 0.6f);
            this.imageNodes[x][y].setScale(1f);
            if(!withBorderMiniMap)
                MapState.worldMiniMap[x][y] = texture.equals(unsetState) ? 0 : 1;
        }
    }

    public Table getMiniMap() {
        Action loading = Actions.sequence(Actions.alpha(0f), Actions.fadeIn(1f));
        this.table.addAction(loading);
        return this.table;
    }
}
