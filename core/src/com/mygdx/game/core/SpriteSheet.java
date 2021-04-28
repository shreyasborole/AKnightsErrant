package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class SpriteSheet {
    private int frameWidth;
    private int frameHeight;
    private float frameDuration;
    private int rows;
    private int cols;
    private Texture spriteSheet;

    public SpriteSheet(String filename, int rows, int cols, float frameDuration){
        this.spriteSheet = new Texture(Gdx.files.internal(filename), true);
        this.rows = rows;
        this.cols = cols;
        this.frameWidth = spriteSheet.getWidth() / this.cols;
        this.frameHeight = spriteSheet.getHeight() / this.rows;
        this.frameDuration = frameDuration;
    }

    private TextureRegion getFrame(int row, int col){
        if (row - 1 >= rows || row - 1 < 0 || col - 1 >= this.cols || col - 1 < 0)
            throw new ArrayIndexOutOfBoundsException("SpriteSheet: Invalid row/col number");
        TextureRegion[][] temp = TextureRegion.split(this.spriteSheet, this.frameWidth, this.frameHeight);
        return temp[row - 1][col - 1];
    }

    private Array<TextureRegion> getFrames(int row){
        if(row - 1 >= rows || row - 1 < 0)
            throw new ArrayIndexOutOfBoundsException("SpriteSheet: Invalid row number");
        TextureRegion[][] temp = TextureRegion.split(this.spriteSheet, this.frameWidth, this.frameHeight);
        Array<TextureRegion> textureArray = new Array<TextureRegion>();
        for (int c = 0; c < this.cols; c++) {
            textureArray.add(temp[row - 1][c]);
        }
        return textureArray;
    }

    public Animation<TextureRegion> getAnimation(Animation.PlayMode playMode, int row){
        return new Animation<TextureRegion>(frameDuration, this.getFrames(row), playMode);
    }

    public Animation<TextureRegion> getAnimation(Animation.PlayMode playMode) {
        return new Animation<TextureRegion>(frameDuration, this.getFrames(1), playMode);
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public float getFrameDuration() {
        return frameDuration;
    }

    public void setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
}
