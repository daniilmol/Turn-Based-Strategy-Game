package environment;
import java.util.Random;

import biomes.Grass;
import biomes.Forest;
import biomes.Mountain;
import buildings.City;
import entities.Entity;
import javafx.scene.Group;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * This class represents the game world
 * @author Daniil Molchanov
 */
public class Board extends Pane{
    
    /**
     * This is the board's main group that contains all the game nodes
     */
    public static Group g = new Group();
    
    /**
     * This determines the texture for a certain environment for extra variety
     */
    private static int index;
    
    /**
     * This is the tile being created
     */
    private Tile tile = null;
    
    /**
     * This is the random generator to generate random tile environments
     */
    private Random r = new Random();
    
    /**
     * This is the grass terrain
     */
    private Grass grass;
    
    /**
     * This is the mountain terrain
     */
    private Mountain mountain;
    
    /**
     * This is the forest terrain
     */
    private Forest forest;
    
    /**
     * This is a two dimensional array containing all the tiles the world will have
     */
    public static Tile[][] tiles = new Tile[26][16];
    
    /**
     * This is a world generation object
     */
    private WorldGen wg;
    
    
    /**
     * Board constructor "constructs" the board
     * @param k is the number of players
     */
    public Board(int k, String type) {
        wg = new WorldGen(k, type);
        City.setIcons();
        getChildren().add(g);
        createMap();
    }
    
    /**
     * This method creates a randomly generated map and randomly spawns all player/neutral cities using a world generation algorithm
     */
    private void createMap() {
        initializeTileEnvironments();

        String[] urls = {grass.getPaths()[0],  forest.getPath()[0], mountain.getPath(), grass.getPaths()[1], grass.getPaths()[2], forest.getPath()[1]};
        Image[] images = {grass.getGraphics()[0], forest.getGraphics()[0], mountain.getGraphics(), grass.getGraphics()[1], grass.getGraphics()[2], forest.getGraphics()[1]};
        
        Random r = new Random();
        index = r.nextInt(3);
        
        for(int x = 0; x < 26; x++) {
            for(int y = 0; y < 16; y++) {
                tile = new Tile(x, y, images[index], urls[index]);
                tiles[x][y] = tile;
                g.getChildren().addAll(tile.getRect(), tile.getImag());    
                index = wg.checkSurroundings(tile, x, ++y);
                determineGrassType();
                determineForestType();
                y--;
                
            }
        }
        
        Entity[] e = wg.spawnCities(wg.getNumberOfPlayers(), wg.getType());
        int coordinates[] = wg.getCoordinates();
        for(int i = 0; i < wg.getNumberOfPlayers() * 2; i+= 2) {
            tiles[coordinates[i]][coordinates[i + 1]].setPiece(e[i / 2], false);
        }

        Entity[] f = wg.spawnNeutralCities(wg.getNumberOfPlayers());
        coordinates = wg.getCoordinates();
        for(int i = wg.getNumberOfPlayers() * 2; i < 20; i+=2) {
            tiles[coordinates[i]][coordinates[i + 1]].setPiece(f[i / 2], false);
        }
    }
    
    /**
     * This method resets the cost of each tile for the dijkstra algorithm to work properly next time
     */
    public static void resetCost() {
        for(int i = 0; i < 26; i++) {
            for(int j = 0; j < 16; j++) {
                Board.tiles[i][j].setCost(-1);
                Board.tiles[i][j].unvisit();
            }
        }
    }
    
    /**
     * This method determines a random grass texture type for variety
     */
    private void determineGrassType() {
        if(index == 0) {
            int chanceIndex = r.nextInt(10);
            if(chanceIndex <= 5)
                index = 0;
            if(chanceIndex > 5 && chanceIndex < 8)
                index = 3;
            if(chanceIndex >= 8)
                index = 4;
        }
    }
    
    /**
     * This method determines a random forest texture type for variety
     */
    private void determineForestType() {
        if(index == 1) {
            int chanceIndex = r.nextInt(2);
            if(chanceIndex == 0)
                index = 1;
            else
                index = 5;
        }
    }
    
    /**
     * This method initializes all the environments
     */
    private void initializeTileEnvironments() {
        grass = new Grass();
        forest = new Forest();
        mountain = new Mountain();
    }
    
    /**
     * This method returns the distance between two tiles
     * @param x is the source tile
     * @param y is the destination tile
     * @return
     */
    public static int getDistance(Tile x, Tile y) {
        return (int) (Math.abs((y.getX() - x.getX())) + Math.abs((y.getY() - x.getY())));
    }
}