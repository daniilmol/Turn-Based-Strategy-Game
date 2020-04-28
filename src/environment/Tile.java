package environment;

import biomes.Forest;
import biomes.Grass;
import biomes.Mountain;
import entities.Building;
import entities.Entity;
import entities.Unit;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.MouseClickHandler;
import ui.Lockable;

/**
 * This class represents a tile
 * @author Daniil Molchanov
 */
public class Tile extends Rectangle implements Lockable{
    
    /**
     * This is the texture a tile will have
     */
    private ImageView art;
    
    /**
     * This is the tile size
     */
    public static final int SIZE = 50;
    
    /**
     * Tile's x coordinate
     */
    private int x;
    
    /**
     * Tile's y coordinate
     */
    private int y;
    
    /**
     * Entity that's on a tile
     */
    private Entity entity;
    
    /**
     * Environment of the tile's terrain
     */
    private TileEnvironment environment;
    
    /**
     * URL to the tile's texture
     */
    private String filePath;
    
    /**
     * Event handler for each tile
     */
    private static MouseClickHandler eventHandler = new MouseClickHandler();
    
    /**
     * Determines if the tile is locked or not
     */
    private boolean locked = false;
    
    /**
     * City that's on the tile
     */
    private Entity city;
    
    /**
     * Unit that's on the tile
     */
    private Entity unit;
    
    /**
     * Tile's cost
     */
    private int cost = -1;
    
    /**
     * Determines if the tile was visited or not for the dijkstra algorithm
     */
    private boolean visited = false;
    
    /**
     * Shortest path to get to this tile. 
     */
    private Tile path;
       
    
    /**
     * Tile constructor initializes the tile's attributes
     * @param x is the tile's x coordinate
     * @param y is the tile's y coordinate
     * @param image is the tile's texture
     * @param url is the tile's texture's URL
     */
    public Tile(int x, int y, Image image, String url) {
        this.x = x;
        this.y = y;
          
        filePath = url;
        
        setWidth(SIZE);
        setHeight(SIZE);
        setX(x);
        setY(y);
        relocate(x * SIZE, y * SIZE);
        
        initializeView(image);
        setEnvironment();
        
        art.setOnMousePressed(this::processMouseClick);
        
    }
    
    /**
     * Returns the next tile that's on the shortest path
     * @return path
     */
    public Tile getPath(){
        return path;
    }
    
    /**
     * Sets a new shortest path to this tile
     * @param path
     */
    public void setPath(Tile path) {
        this.path = path;
    }
    
    /**
     * Completely resets the path for each unit
     */
    public void clearList() {
        for(int i = 0; i < 26; i++) {
            for(int j = 0; j < 16; j++) {
                Board.tiles[i][j].setPath(null);
            }
        }
    }

    /**
     * Sets the tile's cost to the variable passed in
     * @param cost is the new cost the tile will have
     */
    public void setCost(int cost) {
        this.cost = cost;
    }
    
    /**
     * Returns the tile's cost
     * @return cost
     */
    public int getCost() {
        return cost;
    }
    
    /**
     * Visits the tile
     */
    public void visit() {
        visited = true;
    }
    
    /**
     * Unvisits the tile
     */
    public void unvisit() {
        visited = false;
    }
    
    /**
     * Returns true if the tile was visited, false if it wasn't
     * @return visited
     */
    public boolean visited() {
        return visited;
    }
    
    /**
     * Determines if the tile has a unit
     * @return true if it does, false if it doesn't
     */
    public boolean hasUnit() {
        return unit != null;
    }
    
    /**
     * Determines if the tile has a building
     * @return true if it does, false if it doesn't
     */
    public boolean hasBuilding() {
        return city != null;
    }
    
    /**
     * Returns the building on the tile
     * @return city
     */
    public Entity getSavedEntity() {
        return city;
    }
    
    /**
     * Returns the unit on the tile
     * @return unit
     */
    public Entity getSavedUnit() {
        return unit;
    }
    
    /**
     * Returns the entity on the tile
     * @return entity
     * @throws NullPointerException if there is no entity
     */
    public Entity getEntity() throws NullPointerException{
        return entity;
    }
    
    /**
     * Sets the tile's entity to the variable passed in
     * @param entity is the new entity the tile will have
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
        this.unit = entity;
    }
    
    /**
     * Sets the tile's building to the new variable passed in
     * @param entity is the new entity the tile will have
     */
    public void setSavedEntity(Entity entity) {
        city = entity;
    }
    
    /**
     * Sets the tile's unit to the new variable passed in
     * @param entity is the new entity the tile will have
     */
    public void setSavedUnit(Entity entity) {
        unit = entity;
    }
    
    /**
     * Determines whether the entity on the tile is a building
     * @return true if it is, false if it isn't
     */
    public boolean isBuilding() {
        return Building.class.isInstance(entity); 
    }
    
    /**
     * Determines whether the entity on the tile is a unit
     * @return true if it is, false if it isn't
     */
    public boolean isUnit() {
        return Unit.class.isInstance(entity);
    }
    
    /**
     * Handles the mouse event when clicked
     * @param e
     */
    public void processMouseClick(MouseEvent e) {
        eventHandler.handle(e, this);
    }
    
    /*
     * Returns this tile
     */
    public Rectangle getRect() {
        return this;
    }
    
    /*
     * Highlights the tile 
     */
    public void highlight() {
        Glow movementGlow = new Glow(0.5);
        art.setEffect(movementGlow);
    }
    
    /**
     * Highlights the tile depending on the unit color
     */
    public void highlightDependingOnUnitColor() {
        if(unit.getTeam().getColor().equals(Color.RED)) {
            applyColorAdjust(-0.5);
        }else if(unit.getTeam().getColor().equals(Color.BLUE)) {
            applyColorAdjust(0.75);
        }else if(unit.getTeam().getColor().equals(Color.YELLOW)) {
            applyColorAdjust(-0.3);
        }else if(unit.getTeam().getColor().equals(Color.PURPLE)) {
            applyColorAdjust(1);
        }
    }
    
    /**
     * Provides the tile highlight with a specific color 
     * @param hue determines the color the highlight will have
     */
    private void applyColorAdjust(double hue) {
        ColorAdjust c = new ColorAdjust(); // creating the instance of the ColorAdjust effect.   
        c.setBrightness(0.2); // setting the brightness of the color.   
        c.setContrast(0); // setting the contrast of the color  
        c.setHue(hue); // setting the hue of the color  
        c.setSaturation(0.2); // setting the hue of the color.   
        art.setEffect(c);
    }
    
    /**
     * This returns the tile texture
     * @return art
     */
    public ImageView getImag() {
        return art;
    }    
    
    /**
     * This sets the tile texture to the variable passed in
     * @param a is the new texture the tile will have
     */
    public void setImag(ImageView a) {
        art = a;
    }
    
    /**
     * This sets the environment of the tile depending on the URL content
     */
    private void setEnvironment() {
        
        if(filePath.contains("grass")) {
            environment = new Grass();
        }else if(filePath.contains("mountain")) {
            environment = new Mountain();   
        }else if(filePath.contains("forest")) {
            environment = new Forest();
        }
    }
    
    /**
     * This initializes the tile texture
     * @param image is the texture image
     */
    private void initializeView(Image image) {
        art = new ImageView(image);
        art.setFitWidth(SIZE);
        art.setFitHeight(SIZE);
        art.setX(x);
        art.setY(y);
        art.setPreserveRatio(true);
        art.relocate(x * SIZE, y * SIZE);

    }
   
    /**
     * This method sets an entity onto a tile
     * @param x is the entity being set
     * @param moving determines if the entity is moving or spawned in
     */
    public void setPiece(Entity x, boolean moving) {
        if(Building.class.isInstance(x)) {
            System.out.println("This tile contained a city");
            city = x;
        }else if(Unit.class.isInstance(x)) {
            System.out.println("This tile contained a unit");
            unit = x;
            unit.getHeart().setOnMousePressed(this::processMouseClick);
            unit.getHealth().setOnMousePressed(this::processMouseClick);
        }
        
        entity = x;
        entity.updatePosition(this.x, y, moving);
        entity.getI().setOnMousePressed(this::processMouseClick);
    }
    
    /**
     * This method returns the tile's terrain's environment
     * @return environment
     */
    public TileEnvironment getEnvironment() {
        return environment;
    }

    /**
     * This method locks the tile and prevents it from being clicked on
     */
    @Override
    public void lock() {
        locked = true;
    }

    /**
     * This method unlocks the tile and allows it to be clicked
     */
    @Override
    public void unlock() {
        locked = false;
    }

    /**
     * This method locks all tiles
     */
    @Override
    public void lockAll() {
        for(int x = 0; x < 26; x++) {
            for(int y = 0; y < 16; y++) {
                Board.tiles[x][y].lock();
            }
        }
    }
    
    /**
     * This method unlocks all tiles
     */
    @Override
    public void unlockAll() {
        for(int x = 0; x < 26; x++) {
            for(int y = 0; y < 16; y++) {
                Board.tiles[x][y].unlock();
            }
        }
    }
    
    /**
     * This method determines if the tile is locked or not
     */
    @Override
    public boolean isLocked() {
        return locked;
    }
    
    /**
     * This method locks all tiles containing enemy units
     */
    @Override
    public void lockEnemyTiles(Color playerColor) {
      
        for(int x = 0; x < 26; x++) {
            for(int y = 0; y < 16; y++) {
                if((Board.tiles[x][y].hasUnit() || Board.tiles[x][y].hasBuilding()) && !Board.tiles[x][y].getEntity().getLabel().equals("N") && !Board.tiles[x][y].getEntity().getTeam().getColor().equals(playerColor)) {
                    Board.tiles[x][y].lock();
                    System.out.println("Enemy tile found at " + x + ", " + y);
                }
            }
        }
    }
    
    /**
     * This method unlocks all tiles containing friendly units
     */
    @Override
    public void unlockFriendlyTiles(Color playerColor) {
        for(int x = 0; x < 26; x++) {
            for(int y = 0; y < 16; y++) {
                if((Board.tiles[x][y].hasUnit() && !Board.tiles[x][y].getSavedUnit().isFirstCreated() && !Board.tiles[x][y].getEntity().getLabel().equals("N") && Board.tiles[x][y].getEntity().getTeam().getColor().equals(playerColor))
                        || Board.tiles[x][y].isBuilding() && !Board.tiles[x][y].getEntity().getLabel().equals("N") && Board.tiles[x][y].getEntity().getTeam().getColor().equals(playerColor)) {
                        Board.tiles[x][y].unlock();
    
                    System.out.println("Friendly tile found at " + x + ", " + y);
       
                }
            }
        }
    }
}
