package environment;

import java.util.Random;

import biomes.Forest;
import biomes.Grass;
import biomes.Mountain;
import buildings.City;
import entities.Building;
import entities.Entity;
import javafx.scene.image.Image;
import main.Game;

/**
 * This class contains the world generation algorithm to make worlds that are more realistic and less scattered
 * @author Daniil Molchanov
 */
public class WorldGen {
    
    /**
     * This is 
     */
    private int initialX;
    private int initialY;
    private int newX;
    private int newY;
    private int newY2;
    private int newX2;
    
    Entity[] cities = new Entity[10];
    
    private int x[] = new int[20];
    
    public static int cityCount = 0;
    
    private int numPlayers;
    
    private String type;
    
    public WorldGen(int k, String type) {
        numPlayers = k;
        this.type = type;
    }
    
    public int checkSurroundings(Tile x, int line, int yCoord) {
        
        if(line == 0) {       
            
            Tile aboveRectangle = (Tile)Board.g.getChildren().get((yCoord - 1) * 2);
            checkEnvironment(aboveRectangle);
            int index = spawnTile();
            return index;
            
        }else {
            
            Tile aboveRectangle = (Tile)Board.g.getChildren().get((yCoord - 1) * 2);
            Tile leftRectangle = (Tile)Board.g.getChildren().get((line - 1) * 2);
            Tile topLeftRectangle = null;
            Tile botLeftRectangle = null;
            
            if(x.getY() != 0) {
                topLeftRectangle = (Tile)Board.g.getChildren().get(((yCoord - 1) * 2 + (line - 1) * 2) + (line - 1) * 14);
            }
            if(x.getY() != 15) {
                botLeftRectangle = (Tile)Board.g.getChildren().get(((yCoord + 1) * 2 + (line - 1) * 2) + (line - 1) * 14);
            }
            
            if(topLeftRectangle != null && botLeftRectangle != null)
                checkEnvironment(aboveRectangle, leftRectangle, topLeftRectangle, botLeftRectangle);
            if(topLeftRectangle != null && botLeftRectangle == null)
                checkEnvironment(aboveRectangle, leftRectangle, topLeftRectangle);
            if(topLeftRectangle == null && botLeftRectangle != null)
                checkEnvironment(aboveRectangle, leftRectangle, botLeftRectangle);
            int index = spawnTile();
            return index;
            
        }
    }
    
    private void checkEnvironment(Tile ... tile) {        
        for(int i = 0; i < tile.length; i++) {
            if(Grass.class.isInstance(tile[i].getEnvironment())) {
                changeSpawnChance(10, 0, 0);
            }else if(Forest.class.isInstance(tile[i].getEnvironment())) {
                changeSpawnChance(0, 10, 0);
            }else if(Mountain.class.isInstance(tile[i].getEnvironment())) {
                changeSpawnChance(-5, -15, 20);
            }
        }       
    }
    
    private void changeSpawnChance(int a, int b, int c) {
        Grass.spawnChance += a; 
        Forest.spawnChance += b;
        Mountain.spawnChance += c;
    }
    
    private int spawnTile() {
        
        Random r = new Random();
        int full = r.nextInt(102);
        if(full <= Grass.spawnChance) {
            resetSpawnChance();
            return 0;
        }
        else if(full > Grass.spawnChance && full <= Forest.spawnChance + Grass.spawnChance) {
            resetSpawnChance();
            return 1;
        }
        else if(full > Forest.spawnChance + Grass.spawnChance) {
            resetSpawnChance();
            return 2;
        }
        return -1;
        
    }
    
    private void resetSpawnChance() {
        
        Grass.spawnChance = Grass.getChance();
        Forest.spawnChance = Forest.getChance();
        Mountain.spawnChance = Mountain.getChance();
        
    }
    
    private int getLength(int[] x) {
        int realLength = 0;
        for(int i = 0; i < x.length; i++) {
            if(x[i] != 0) {
                realLength++;
            }
        }
        return realLength;
    }
    
    public Entity[] spawnCities(int numPlayers, String type) {
        Game.setPlayers(numPlayers, type);
        Random random = new Random();
        Building city = null;
        Building city2 = null;
        do {
            initialX = random.nextInt(26);
            initialY = random.nextInt(16);
        }while(initialX == 0 || initialX == 25 || initialY == 0 || initialY == 15);
        newX = 26;
        newY = 26;
        for(int i = 0; i < numPlayers * 2; i+=2) {
            if(i != 0) {
                do{
                    newX = random.nextInt(26);
                    newY = random.nextInt(16);
                    
                }while((newX == 0 || newX == 25 || newY == 0 || newY == 15) || !check(10, newX, newY) || 
                        Mountain.class.isInstance(Board.tiles[newX][newY].getEnvironment()) || 
                        Forest.class.isInstance(Board.tiles[newX][newY].getEnvironment()));
                
                    city2 = new City("", City.getIcons()[i / 2], "C", newX, newY, Game.players.get(i / 2));
                    cities[i / 2] = city2;
                    x[i] = newX;
                    x[i + 1] = newY;
            }else {
                city = new City("", City.getIcons()[0], "C", initialX, initialY, Game.players.get(i / 2));
                cities[i / 2] = city;
                x[i] = initialX;
                x[i + 1] = initialY;
            }
        }
        cityCount = numPlayers;
        return cities;
    }
    
    public Entity[] spawnNeutralCities(int numPlayers) {
        Random random = new Random();
        Building neutralCity = null;
        for(int i = numPlayers * 2; i < x.length; i+=2) {
            if(i == 20)
                break;

            do {                   
                    newX2 = random.nextInt(26);
                    newY2 = random.nextInt(16);
                
            }while((newX2 == 0 || newX2 == 25 || newY2 == 0 || newY2 == 15) || !check(5, newX2, newY2) || 
                    Mountain.class.isInstance(Board.tiles[newX2][newY2].getEnvironment()) || 
                    Forest.class.isInstance(Board.tiles[newX2][newY2].getEnvironment()));   
            neutralCity = new City("", City.getIcons()[4], "N", newX2, newY2, null);
            cityCount++;
            x[i] = newX2;
            x[i + 1] = newY2;

            cities[i / 2] = neutralCity;

        }
        return cities;
    }
    
    private boolean check(int threshold, int x, int y) {
        for(int k = 0; k + 2 <= getLength(this.x); k+=2) {
            if(Math.abs(x - this.x[k]) + Math.abs(y - this.x[k + 1]) < threshold) {
                return false;
            }
        }
        return true;
    }
    
    public int[] getCoordinates() {
        return x;
    }
    
    public int getNumberOfPlayers() {
        return numPlayers;
    }
    
    public String getType() {
        return type;
    }
}
