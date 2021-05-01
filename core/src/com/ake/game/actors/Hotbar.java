package com.ake.game.actors;

import com.ake.game.core.BaseActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Hotbar extends Actor{
    private Image[] itemFrames;
    private Item[] items;
    private Table hotbarLayout;

    private int currentItem;
    private static final int hotBarSize = 6;

    public Hotbar(Stage stage) {
        this.currentItem = 0;
        this.items = new Item[hotBarSize];
        this.itemFrames = new Image[hotBarSize];
        this.hotbarLayout = new Table();
        
        hotbarLayout.pad(10f);
        for(Image img : itemFrames){
            img = new Image(new Texture("icons/frame.png"));
            img.setColor(1f, 1f, 1f, 0.5f);
            hotbarLayout.add(img).pad(5f);
        }
        stage.addActor(hotbarLayout);
    }

    public Table getHotbar(){
        return this.hotbarLayout;
    }

    public void addItem(Item item){
        this.items[currentItem] = item;
    }
    
    @Override
    public void act(float dt){
        super.act(dt);
    }
}
