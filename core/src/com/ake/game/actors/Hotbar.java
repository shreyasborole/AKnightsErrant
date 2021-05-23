package com.ake.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Hotbar extends Actor{
    private Item[] items;
    private Table hotbarLayout;

    private int currentItem;
    private Stage stage;
    private static final int hotBarSize = 6;


    private Image getFrame(int index){
        Texture texture = currentItem == index ? new Texture("icons/frame_highlight.png") : new Texture("icons/frame.png");
        Image frame = new Image(texture);
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
        this.stage.addActor(this);
    }
    
    void render(){
        hotbarLayout.clear();
        hotbarLayout.pad(10f);
        for(int i = 0; i < items.length; ++i) {
            Stack stack = new Stack();
            stack.add(getFrame(i));
            try {
                Image icon = getIcon(items[i]);
                stack.add(icon);
            } catch (NullPointerException e) {}
            hotbarLayout.add(stack).pad(5f);
        }
    }

    public Table getHotbar(){
        Action loading = Actions.sequence(Actions.alpha(0f), Actions.fadeIn(1f));
        this.hotbarLayout.addAction(loading);
        return this.hotbarLayout;
    }

    public void addItem(Item item){
        if(currentItem >= hotBarSize)
            currentItem = 0;
        this.items[currentItem] = item;
        render();
        ++currentItem;
    }
    
    @Override
    public void act(float dt){
        super.act(dt);

        if(Gdx.input.isKeyJustPressed(Keys.NUM_1)){
            currentItem = 0;
            render();
        }
        if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
            currentItem = 1;
            render();
        }
        if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
            currentItem = 2;
            render();
        }
        if (Gdx.input.isKeyJustPressed(Keys.NUM_4)) {
            currentItem = 3;
            render();
        }
        if (Gdx.input.isKeyJustPressed(Keys.NUM_5)) {
            currentItem = 4;
            render();
        }
        if (Gdx.input.isKeyJustPressed(Keys.NUM_6)) {
            currentItem = 5;
            render();
        }
    }
}
