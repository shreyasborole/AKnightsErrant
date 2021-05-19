package com.ake.game.actors;

import com.ake.game.core.BaseActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Hotbar extends Actor{
    private Item[] items;
    private Table hotbarLayout;

    private int currentItem;
    private Stage stage;
    private static final int hotBarSize = 6;


    private Image getFrame(){
        Image frame = new Image(new Texture("icons/frame.png"));
        frame.setColor(1f, 1f, 1f, 0.5f);
        return frame;
    }

    private Image getIcon(Item item){
        return new Image(item.getIcon());
    }

    public Hotbar(Stage stage) {
        this.stage = stage;
        this.currentItem = 0;
        this.items = new Item[hotBarSize];
        this.hotbarLayout = new Table();

        render();
        this.stage.addActor(hotbarLayout);
    }
    
    void render(){
        hotbarLayout.clear();
        hotbarLayout.pad(10f);
        for (Item item : items) {
            Stack stack = new Stack();
            stack.add(getFrame());
            try {
                Image icon = getIcon(item);
                stack.add(icon);
            } catch (NullPointerException e) {}
            hotbarLayout.add(stack).pad(5f);
        }
    }

    public Table getHotbar(){
        return this.hotbarLayout;
    }

    public void addItem(Item item){
        this.items[currentItem++] = item;
        for(Item i : items){
            System.out.println("Item : " + i);
        }
        render();
    }
    
    @Override
    public void act(float dt){
        super.act(dt);
    }
}
