package com.ake.game.actors;

import com.ake.game.core.BaseActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Item extends BaseActor{
    protected Items itemID;
    protected Texture icon;

    protected Item(Stage s) {
        super(0, 0, s);
    }

    protected void setID(Items itemID){
        this.itemID = itemID;
    }

    protected void setIcon(String filename){
        this.icon = new Texture(filename);
        if(icon.getWidth() != 32 && icon.getHeight() != 32){
            System.err.println("Invalid icon size: Icon size should be 32 x 32.");
            Gdx.app.exit();
        }
    }

    public Texture getIcon(){
        return this.icon;
    }
}