package com.mygdx.game.map;

/**
 * Matrix Generation Algorithm
 * 
 * @author Shreyas Borole
 */
public class MapNode {
    public static final char SOURCE = '%';
    public static final char DESTINATION = '#';
    public static final char BOSS = '$';
    public static final char OBSTACLE = '|';
    public static final char PATH = '~';

    private char charMap;
    private int seed;
    private int x, y;
    MapNode(){
        this.charMap = '|';
        this.seed = 0;
        this.x = -1;
        this.y = -1;
    }

    MapNode(int x, int y, char charMap, int seed){
        this.x = x;
        this.y = y;
        this.charMap = charMap;
        this.seed = seed;
    }

    void setX(int index){
        this.x = index;
    }

    void setY(int index){
        this.y = index;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public char getChar(){
        return this.charMap;
    }

    public int getSeed(){
        return this.seed;
    }

    void setChar(char ch){
        this.charMap = ch;
    }

     void setSeed(int seed){
        this.seed = seed;
    }

    @Override
    public String toString(){
        return Character.toString(charMap) + "\t(x, y): (" + x + "," + y + ")";
    }
}
