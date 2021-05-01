package com.ake.game.actors;

import com.ake.game.core.BaseActor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Item extends BaseActor{
    protected Items itemID;
    protected Image icon;

    protected Item(Stage s) {
        super(0, 0, s);
    }

    protected void setID(Items itemID){
        this.itemID = itemID;
    }

    protected void setIcon(String filename){
        Texture texture = new Texture(filename);
        this.icon = new Image(texture);
    }

    public Image getIcon(){
        return this.icon;
    }
}