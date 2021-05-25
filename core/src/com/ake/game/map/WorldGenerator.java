package com.ake.game.map;

import com.ake.game.libs.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * World Generator Class
 * 
 * @author Ankit Oli, Shreyas Borole
 */
public class WorldGenerator {
    private int rows;
    private int cols;
    private long seed;
    private MapNode pathMatrix[][];
    private Node initialNode;
    private ArrayList <Node> finalNodes;
    private ArrayList <Node> blocksArray;
    private ArrayList <List<Node>> paths;
    private final Random random;
    
    private MatrixGeneration mga;

    public WorldGenerator(int matrixSize, long seed){
        this.seed = seed;
        this.random = new Random(this.seed);
        this.mga = new MatrixGeneration(matrixSize, this.seed);
        int[][] matrix = mga.generateMatrix();
        this.rows = this.cols = matrix.length;

        this.finalNodes = new ArrayList<>();
        this.blocksArray = new ArrayList<>();
        this.paths = new ArrayList<>();
        this.pathMatrix = new MapNode[this.rows][this.cols];
        
        initializePathMatrix();
        getPaths(matrix);
        updatePaths();
        finalizePathMatrix();
    }

    public long getSeed(){
        return this.seed;
    }

    public MapNode[][] getPathMatrix(){
        return this.pathMatrix;
    }

    public int getRows(){
        return this.rows;
    }

    public int getCols(){
        return this.cols;
    }

    public MapNode getSource(){
        return this.pathMatrix[this.initialNode.getRow()][this.initialNode.getCol()];
    }

    public MapNode getLeft(int i, int j){
        try {
            MapNode node = this.pathMatrix[i][j - 1];
            if(node.getChar() == '|') 
                return null;
            return node;
        } catch (ArrayIndexOutOfBoundsException aiofbe) {
            return null;
        }
    }

    public MapNode getRight(int i, int j) {
        try {
            MapNode node = this.pathMatrix[i][j + 1];
            if (node.getChar() == '|')
                return null;
            return node;
        } catch (ArrayIndexOutOfBoundsException aiofbe) {
            return null;
        }
    }

    public MapNode getBottom(int i, int j) {
        try {
            MapNode node = this.pathMatrix[i + 1][j];
            if (node.getChar() == '|')
                return null;
            return node;
        } catch (ArrayIndexOutOfBoundsException aiofbe) {
            return null;
        }
    }
    
    public MapNode getTop(int i, int j) {
        try {
            MapNode node = this.pathMatrix[i - 1][j];
            if (node.getChar() == '|')
                return null;
            return node;
        } catch (ArrayIndexOutOfBoundsException aiofbe) {
            return null;
        }
    }

    private void initializePathMatrix(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.pathMatrix[i][j] = new MapNode(i, j, MapNode.OBSTACLE, 0);
            }
        }
    }

    private void finalizePathMatrix(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                MapNode node = this.pathMatrix[i][j];
                if(node.getChar() == MapNode.DESTINATION || node.getChar() == MapNode.SOURCE || node.getChar() == MapNode.BOSS || node.getChar() == MapNode.PATH){
                    node.setSeed(random.nextInt());
                } 
            }
        }
        if (checkPaths() > 0) {
            removeCursedNodes();
            updatePaths();
        }
    }

    private void getPaths(int maze[][]){
        for(int i = 0;i < this.rows; i++){
            for(int j = 0;j < this.cols; j++){
                if(maze[i][j]==-1)
                    this.initialNode = new Node(i, j);
                if(maze[i][j]==9)
                    this.finalNodes.add(new Node(i, j));
                if(maze[i][j]==1)
                    this.blocksArray.add(new Node(i, j));
            }
        }

        for(Node node : this.finalNodes){
            AStar a_star = new AStar(rows, cols, this.initialNode, node, 0, -1);
            a_star.setBlocks(this.blocksArray);

            List<Node> path = a_star.findPath();
            this.paths.add(path);
        }
    }

    private void updatePaths(){
        ArrayList<Integer> pathSizes = new ArrayList<>();
        for(List<Node> path : this.paths){
            pathSizes.add(path.size());
            for(Node node : path){
                this.pathMatrix[node.getRow()][node.getCol()].setChar(MapNode.PATH);
            }
        }
        int max = Collections.max(pathSizes);
        this.pathMatrix[initialNode.getRow()][initialNode.getCol()].setChar(MapNode.SOURCE);

        boolean bossRoom = false;
        for(int i = 0; i < this.finalNodes.size(); i++){
            if(max == pathSizes.get(i) && !bossRoom){
                this.pathMatrix[this.finalNodes.get(i).getRow()][this.finalNodes.get(i).getCol()].setChar(MapNode.BOSS);
                bossRoom = true;
            }else{
                this.pathMatrix[this.finalNodes.get(i).getRow()][this.finalNodes.get(i).getCol()].setChar(MapNode.DESTINATION);
            }
        }
        pathSizes.clear();
    }

    public int checkPaths(){
        int count = 0;
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                if(this.pathMatrix[i][j].getChar() == MapNode.BOSS || this.pathMatrix[i][j].getChar() == MapNode.DESTINATION){
                    if (check(i, j))
                        count++;
                }
            }
        }
        return count;
    }
    
    private List<Node> getCursedNodes(){
        List<Node> cursedNodes = new ArrayList<>();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if(this.pathMatrix[i][j].getChar() == MapNode.BOSS || this.pathMatrix[i][j].getChar() == MapNode.DESTINATION){
                    if(check(i, j)){
                        cursedNodes.add(new Node(i, j));
                    }
                }
            }
        }
        return cursedNodes;
    }

    private void removeCursedNodes(){
        List<Node> cursedNode = getCursedNodes();
        ArrayList<Node> dummyBlocks = new ArrayList<>(); 
        for(Node node : cursedNode){
            AStar a_star = new AStar(rows, cols, this.initialNode, node, 0, -1);
            a_star.setBlocks(dummyBlocks);
            List<Node> path = a_star.findPath();
            this.paths.add(path);
            for(int i = 0; i < path.size(); i++){
                if(path.get(i).getRow() == this.blocksArray.get(i).getRow() && path.get(i).getCol() == this.blocksArray.get(i).getCol()){
                    this.blocksArray.remove(i);
                }
            }
        }
    }

    private boolean check(int i, int j) {
        char obstacle = MapNode.OBSTACLE;
        boolean flag = false;
        // [row][col]
        if(i == 0 || j == 0 || i == this.rows - 1 || j == this.cols - 1){
            // EDGES
            // left
            if(j == 0 && i - 1 >= 0 && i + 1 < this.rows && j + 1 < this.cols)
                if (this.pathMatrix[i - 1][j].getChar() == obstacle && this.pathMatrix[i + 1][j].getChar() == obstacle && this.pathMatrix[i][j + 1].getChar() == obstacle)
                    flag = true;
            // right
            if(j == this.cols - 1 && i - 1 >= 0 && i + 1 < this.rows && j - 1 >= 0)
                if (this.pathMatrix[i - 1][j].getChar() == obstacle && this.pathMatrix[i + 1][j].getChar() == obstacle && this.pathMatrix[i][j - 1].getChar() == obstacle)
                    flag = true;
            // top
            if(i == 0 && i + 1 < this.rows && j - 1 >= 0 && j + 1 < this.cols)
                if (this.pathMatrix[i + 1][j].getChar() == obstacle && this.pathMatrix[i][j - 1].getChar() == obstacle && this.pathMatrix[i][j + 1].getChar() == obstacle)
                    flag = true;
            // bottom
            if(i == this.rows - 1 && i - 1 >= 0 && j - 1 >= 0 && j + 1 < this.cols)
                if (this.pathMatrix[i - 1][j].getChar() == obstacle && this.pathMatrix[i][j - 1].getChar() == obstacle && this.pathMatrix[i][j + 1].getChar() == obstacle)
                    flag = true;

            // CORNERS
            // bottom-left
            if(j == 0 && i == this.rows - 1 && i - 1 >= 0 && j + 1 < this.cols)
                if (this.pathMatrix[i - 1][j].getChar() == obstacle && this.pathMatrix[i][j + 1].getChar() == obstacle)
                    flag = true;
            // bottom-right
            if(j == this.cols - 1 && i == this.rows - 1 && i - 1 >= 0 && j - 1 >= 0)
                if (this.pathMatrix[i - 1][j].getChar() == obstacle && this.pathMatrix[i][j - 1].getChar() == obstacle)
                    flag = true;
            // top-left
            if(j == 0 && i == 0 && i + 1 < this.rows && j + 1 < this.cols)
                if (this.pathMatrix[i + 1][j].getChar() == obstacle && this.pathMatrix[i][j + 1].getChar() == obstacle)
                    flag = true;
            // top-right
            if(j == this.cols - 1 && i == 0 && i + 1 < this.rows && j - 1 >= 0)
                if (this.pathMatrix[i + 1][j].getChar() == obstacle && this.pathMatrix[i][j - 1].getChar() == obstacle)
                    flag = true;
        }else{
            // CENTER
            if (this.pathMatrix[i - 1][j].getChar() == obstacle && this.pathMatrix[i + 1][j].getChar() == obstacle && this.pathMatrix[i][j - 1].getChar() == obstacle && this.pathMatrix[i][j + 1].getChar() == obstacle)
                flag = true;
        }
        return flag;
    }


    private void displayPaths() {
        for(int i = 0; i < this.rows; i++){
            System.out.println();
            for(int j = 0; j < this.cols; j++){
                System.out.print("  " + this.pathMatrix[i][j].getChar());
            }
        }
        System.out.println();
	}

    public void displayAll(){
        System.out.println("World Seed: " + this.seed);
        this.mga.displayMatrix();
        this.displayPaths();
    }

}