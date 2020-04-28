package main;

import AI.AI;
import entities.Unit;
import environment.Board;
import environment.Tile;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * This class represents a player
 * @author Daniil Molchanov
 */
public class Player {

    /**
     * This represents the color of the player using text
     */
    private String color;
    
    /**
     * This represents the color of the player's units
     */
    private Color playerColor;
    
    /**
     * This represents the color each player's city will have
     */
    private Image city;
    
    /**
     * This represents the number of units each player has on the board
     */
    private int units = 0;
    
    /**
     * This represents the amount of gold the player has
     */
    private int gold;
    
    /**
     * This represents the amount of gold the player will receive each turn
     */
    private int goldPerTurn;
    
    /**
     * This represents the number of cities the player owns
     */
    private int citiesOwned;
    
    /**
     * This represents if it's the player's turn or not
     */
    private boolean turn;
    
    /**
     * This represents how many turn cycles will need to pass before it is the player's turn
     */
    private int turnsTillStart;
    
    /**
     * This represents the amount of players in the game
     */
    public static int playerCount = 0;
    
    /**
     * This represents the color of the player who's turn it is.
     */
    public static Color storedColor;
    
    /**
     * Prevents the player's from receiving gold when the game is first started
     */
    private static boolean first = true;
    
    /**
     * Turns the above boolean off so all players will receive gold normally
     */
    private static int countdown;
    
    private boolean bot;
    private AI ai;
    /**
     * Player constructor initializes all the player's attributes
     * @param color is the color text
     * @param turnsTillStart represents the amount of cycles needed to get a turn to play
     */
    public Player(String color, int turnsTillStart, boolean bot) {
        this.bot = bot;
        if(bot)
            ai = new AI(this);
        citiesOwned = 1;       
        turn = false;
        if(playerCount == 0)
            turn = true;
        this.color = color;
        
        if(color.equals("Red")) {
            city = new Image("file:assets/pixil-frame-0.png");
            playerColor = Color.RED;
        }else if(color.equals("Blue")){
            city = new Image("file:assets/pixil-frame-1.png");
            playerColor = Color.BLUE;
        }else if(color.equals("Purple")) {
            city = new Image("file:assets/pixil-frame-3.png");
            playerColor = Color.PURPLE;
        }else if(color.equals("Yellow")) {
            city = new Image("file:assets/pixil-frame-2.png");
            playerColor = Color.YELLOW;
        }
        gold = 5;
        citiesOwned = 1;
        goldPerTurn = citiesOwned * 5;
        this.turnsTillStart = turnsTillStart;
        playerCount++;
        countdown = playerCount;
    }
    
    /**
     * This method deals with all the turn handling. When ending a turn, it will perform everything needed to progress.
     */
    public void cycleTurn(){
       
        turnsTillStart--;
       
        if(turnsTillStart < 1) {
            
            turnsTillStart = Player.playerCount;
            turn = false;
            lockFirstTimeCreatedUnits(playerColor, true, false);
        }
        if(turnsTillStart == 1) {
            testColor();
            
            if(countdown != 0)
                countdown--;
            if(!first) {
                goldPerTurn = citiesOwned * 5;
                gold += goldPerTurn;
            }
            turn = true;
            if(!bot) {
                resetLocks(playerColor, true, false);
                Game.getGoldText().setText("Gold: " + gold);
            }else 
                Board.tiles[0][0].lockAll();
            Unit.resetMovements(playerColor); 
            endActions();
            
            storedColor = playerColor;

            if(countdown == 1)
                first = false;
            if(bot)
                ai.performAction();
        } else {
            turn = false;
            lockFirstTimeCreatedUnits(playerColor, true, false);
        }
    }
    
    /**
     * Unhighlights units and uses unit's abilities if they have any
     */
    private void endActions() {
        for(int x = 0; x < 26; x++) {
            for(int y = 0; y < 16; y++) {
                if(Board.tiles[x][y].hasUnit()) {
                    if(!Board.tiles[x][y].getEntity().getTeam().getColor().equals(playerColor))
                        Board.tiles[x][y].getEntity().unhighlightUnit();
                    Board.tiles[x][y].getEntity().useAbility(this);
                }
            }
        }
    }

    /**
     * This tests for color
     */
    private void testColor() {
        System.out.println("Testing which color is stored...");
        if(playerColor.equals(Color.RED))
            System.out.println("Stored color is red");
        if(playerColor.equals(Color.BLUE))
            System.out.println("Stored color is blue");
        if(playerColor.equals(Color.PURPLE))
            System.out.println("Stored color is purple");
        if(playerColor.equals(Color.YELLOW))
            System.out.println("Stored color is yellow");
    }
    
    /**
     * This method resets the locks on all tiles
     * @param storedColor represents the color of the player who's turn is active
     * @param calledFromPlayer is true if this method is called from the player class
     * @param calledFromElseWhere is true if this method is called from anywhere but the player class
     */
    public static void resetLocks(Color storedColor, boolean calledFromPlayer, boolean calledFromElseWhere) {
        Board.tiles[0][0].lockAll();
        Board.tiles[0][0].unlockFriendlyTiles(storedColor);
        Board.tiles[0][0].lockEnemyTiles(storedColor);
        
        lockFirstTimeCreatedUnits(storedColor, calledFromPlayer, calledFromElseWhere);       
    }
    
    /**
     * This locks tiles that contain initially created units
     * @param storedColor represents the color of the player who's turn is active
     * @param calledFromPlayer is true if this method is called from the player class
     * @param calledFromElseWhere is true if this method is called from anywhere but the player class
     */
    private static void lockFirstTimeCreatedUnits(Color storedColor, boolean calledFromPlayer, boolean calledFromElseWhere) {
        for(int x = 0; x < 26; x++) {
            for(int y = 0; y < 16; y++) {
                
                if(Board.tiles[x][y].hasUnit() && Board.tiles[x][y].hasBuilding()
                        && Board.tiles[x][y].getSavedUnit().getTeam().getColor().equals(storedColor)
                        && Board.tiles[x][y].getSavedUnit().isFirstCreated()) {
                    Board.tiles[x][y].lock();
                    if(calledFromPlayer) {
                        Board.tiles[x][y].getSavedUnit().notFirstAnymore();
                    }
                }
            }
        }
    }
    
    /**
     * This detects if a player's turn is active or not
     * @return turn
     */
    public boolean isTurn() {
        return turn;
    }
    
    /**
     * This returns the color as text 
     * @return color
     */
    public String getColorText() {
        return color;
    }
    
    /**
     * This returns the player's color
     * @return playerColor
     */
    public Color getColor() {
        return playerColor;
    }
    
    /**
     * When printing players out it is nice to know what color they are
     */
    public String toString() {
        return "Player is team " + color;
    }
    
    /**
     * This method returns the city image this player has
     * @return city
     */
    public Image getCity() {
        return city;
    }
    
    /**
     * This method returns the number of cities the player has
     * @return citiesOwned
     */
    public int getCities() {
        return citiesOwned;
    }
    
    /**
     * This method sets the number of cities the player has to the passed in variable
     * @param cities is the new number of cities the player will have
     */
    public void setCities(int cities) {
        citiesOwned = cities;
    }
    
    /**
     * This method sets the gold amount the player has to the passed in variable
     * @param x is the new number of gold the player will have
     */
    public void setGold(int x) {
        gold = x;
    }
    
    /**
     * This method returns the gold amount the player has
     * @return gold
     */
    public int getGold() {
        return gold;
    }
    
    /**
     * This method returns the income the player has
     * @return goldPerTurn
     */
    public int getRate() {
        return goldPerTurn;
    }
    
    /**
     * This method sets the income the player has to the passed in variable
     * @param numCities is the number of cities
     */
    public void setRate(int numCities) {
        goldPerTurn = numCities;   
    }

    /**
     * This method sets the number of units the player has to the passed in variable
     * @param units is the number of units
     */
    public void setUnits(int units) {
        this.units = units;
    }
    
    /**
     * This method returns the number of units the player has
     * @return units
     */
    public int getUnits() {
        return units;
    }
    
    public boolean isBot() {
        return bot;
    }
}
