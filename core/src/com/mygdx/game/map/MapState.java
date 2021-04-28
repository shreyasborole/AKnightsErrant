package com.mygdx.game.map;

import java.util.Arrays;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.AKEGame;
import com.mygdx.game.actors.*;
import com.mygdx.game.screens.LevelScreen;
import com.mygdx.game.utils.Debug;

public class MapState {
    public static Hero.Location heroLocation;
    public static MapNode currentNode;
    public static WorldGenerator worldGenerator;

    public static final int worldSize = 16;

    public static int[][] worldMiniMap;

    public static void initialize(long seed) {
        // Debug.clearScreen();
        MapState.worldGenerator = new WorldGenerator(worldSize, seed);
        currentNode = worldGenerator.getSource();
        heroLocation = Hero.Location.CENTER;
        worldMiniMap = new int[worldSize][worldSize];
        for(int[] row : worldMiniMap)
            Arrays.fill(row, 0);
    }

    public static void setPortals(Stage stage) {
        int x = currentNode.getX();
        int y = currentNode.getY();
        if (worldGenerator.getLeft(x, y) != null)
            new Portal(Portal.Location.LEFT, 0, 0, stage);
        if (worldGenerator.getBottom(x, y) != null)
            new Portal(Portal.Location.BOTTOM, 0, 0, stage);
        if (worldGenerator.getRight(x, y) != null)
            new Portal(Portal.Location.RIGHT, 0, 0, stage);
        if (worldGenerator.getTop(x, y) != null)
            new Portal(Portal.Location.TOP, 0, 0, stage);

        // worldGenerator.displayAll();
        // System.out.println("TOP: " + worldGenerator.getTop(x, y));
        // System.out.println("BOTTOM: " + worldGenerator.getBottom(x, y));
        // System.out.println("RIGHT: " + worldGenerator.getRight(x, y));
        // System.out.println("LEFT: " + worldGenerator.getLeft(x, y));
        // System.out.println("Current seed: " + currentNode.getSeed());
    }

    public static void changeState(Portal.Location location) {
        Debug.clearScreen();
        int x = currentNode.getX();
        int y = currentNode.getY();
        switch (location) {
            case BOTTOM:
                currentNode = worldGenerator.getBottom(x, y);
                MapState.heroLocation = Hero.Location.TOP;
                AKEGame.setActiveScreen(new LevelScreen());
                break;
            case LEFT:
                currentNode = worldGenerator.getLeft(x, y);
                MapState.heroLocation = Hero.Location.RIGHT;
                AKEGame.setActiveScreen(new LevelScreen());
                break;
            case RIGHT:
                currentNode = worldGenerator.getRight(x, y);
                MapState.heroLocation = Hero.Location.LEFT;
                AKEGame.setActiveScreen(new LevelScreen());
                break;
            case TOP:
                currentNode = worldGenerator.getTop(x, y);
                MapState.heroLocation = Hero.Location.BOTTOM;
                AKEGame.setActiveScreen(new LevelScreen());
                break;
            default:
                break;
        }
    }
}
