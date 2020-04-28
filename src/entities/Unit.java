package entities;

import AI.FiniteStateMachine;
import environment.Board;
import environment.Tile;
import environment.TileEnvironment;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.Game;
import main.MouseClickHandler;
import main.Player;
import units.Archer;
import units.Ballista;
import units.Catapult;
import units.Cavalry;
import units.Cleric;
import units.Garrison;
import units.Knight;
import units.Longbow;
import units.Mage;
import units.Necromancer;
import units.Peasant;
import units.Rogue;
import units.SiegeEngine;
import units.Spearman;
import units.Swordsman;
import units.Warrior;

/**
 * This class represents a unit
 * @author Daniil Molchanov
 */
public class Unit extends Entity{
    
    /**
     * This is the text that's displayed for testing
     */
    private Text displayed;
    
    /**
     * This contains the number of health on it
     */
    private ImageView heart;
    
    /**
     * This contains the number of health parsed to a string
     */
    private Text healthDisplayer;
    
    /**
     * This is the color in text
     */
    private static String textColor;
    
    /**
     * This is the unit's health
     */
    protected int hp;
    
    /**
     * This is the unit's max health
     */
    protected int maxHP;
    
    /**
     * This is the unit's base damage
     */
    protected int damage;
    
    /**
     * This is the unit's speed
     */
    protected int speed;
    
    /**
     * This is the amount of tiles the unit can move
     */
    protected int movementsLeft;
    
    /**
     * This is the unit's range
     */
    protected int range;
    
    /**
     * This is how much gold the unit costs
     */
    protected int cost;
    
    /**
     * This is the unit's type
     */
    protected UnitType type;
    
    private static UnitType foot = new UnitType("foot");
    private static UnitType mounted = new UnitType("mounted");
    private static UnitType structure = new UnitType("structure");
    
    /**
     * Prevents constant initialization of unit textures
     */
    private static boolean filledAlready = false;
    
    /**
     * This is the list of unit textures
     */
    public static Image[] unitTextures = new Image[11];
    
    /**
     * This is the list red team unit textures
     */
    public static Image[] redTeamTextures = new Image[11];
    
    /**
     * This is the list blue team unit textures
     */
    public static Image[] blueTeamTextures = new Image[11];
    
    /**
     * This is the list purple team unit textures
     */
    public static Image[] purpleTeamTextures = new Image[11];
    
    /**
     * This is the list yellow team unit textures
     */
    public static Image[] yellowTeamTextures = new Image[11];
    
    /**
     * This is the imageview
     */
    protected ImageView v = new ImageView();
    
    /**
     * Determines whether the unit is selected or not
     */
    private boolean selected;
    
    /**
     * Determines whether the unit was initially created
     */
    private boolean firstTimeCreated;
    
    /**
     * Contains the urls for all unit textures
     */
    public static String[] urls = new String[55];
    
    /**
     * Unit
     */
    private static Unit peasant = new Peasant("", new Image("file:assets/units/peasant.png"), "Peasant", 0, 0, null);
    
    /**
     * Unit
     */
    private static Unit swordsman = new Swordsman("", new Image("file:assets/units/swordsman.png"), "Swordsman", 0, 0, null);
    
    /**
     * Unit
     */
    private static Unit warrior = new Warrior("", new Image("file:assets/units/warrior.png"), "Warrior", 0, 0, null);
    
    /**
     * Unit
     */
    private static Unit cavalry = new Cavalry("", new Image("file:assets/units/cavalry.png"), "Cavalry", 0, 0, null);
    
    /**
     * Unit
     */
    private static Unit assassin = new Rogue("", new Image("file:assets/units/assassin.png"), "Assassin", 0, 0, null);
    
    /**
     * Unit
     */
    private static Unit spearman = new Spearman("", new Image("file:assets/units/spearman.png"), "Spearman", 0, 0, null);
    
    /**
     * Unit
     */
    private static Unit archer = new Archer("", new Image("file:assets/units/archer.png"), "Archer", 0, 0, null);
    
    /**
     * Unit
     */
    private static Unit mage = new Mage("", new Image("file:assets/units/mage.png"), "Mage", 0, 0, null);
    
    /**
     * Unit
     */
    private static Unit siegeEngine = new SiegeEngine("", new Image("file:assets/units/siege.png"), "Siege", 0, 0, null);
    
    /**
     * Unit
     */
    private static Unit cleric = new Cleric("", new Image("file:assets/units/cleric.png"), "Cleric", 0, 0, null);
    
    /**
     * Unit
     */
    private static Unit necromancer = new Necromancer("", new Image("file:assets/units/necromancer.png"), "Necromancer", 0, 0, null);
  
    /**
     * List of all possible units
     */
    public final static Unit[] UNITS = {peasant, swordsman, cavalry, spearman, assassin, warrior, mage, cleric, necromancer, archer, siegeEngine}; 
    
    /**
     * List of units that do not have textures yet
     */
    public static Unit[] aksdlf = {new Ballista(null, null, null, 0, 0, null), new Catapult(null, null, null, 0, 0, null), new Garrison(null, null, null, 0, 0, null), 
            new Knight(null, null, null, 0, 0, null), new Longbow(null, null, null, 0, 0, null)}; 
    
    /**
     * The number of seconds it takes for a counter animation to play after an attack
     */
    private static int seconds = 1;  
    
    private FiniteStateMachine fsm;
    
    /**
     * Unit constructor initializes a unit
     * @param name is the unit name
     * @param image is the unit texture
     * @param text is the unit label
     * @param x is the x coordinate
     * @param y is the y coordinate
     * @param ownerShip is the player the unit belongs to
     */
    public Unit(String name, Image image, String text, double x, double y, Player ownerShip, String typeName) {
        super(name, image, text, x, y, ownerShip);
        
        type = new UnitType(typeName);
        selected = false;
        firstTimeCreated = true;
        
        displayed = new Text(this.xCoord, this.yCoord, text);
        displayed.setFill(Color.BLUE);
        //displayed.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        g.getChildren().add(displayed);
        displayed.relocate(xCoord * 50 + 6, yCoord * 50 + 10);
        displayed.setFont(Font.loadFont("file:resources/ancient.ttf", 20));


        if(!filledAlready) {
            fillUnitTextures();
            fillUnitURLS();
            filledAlready = true;
        }

        initializeHeart();
        initializeHealthText();
    }
    
    /**
     * This returns a boolean determining whether the unit was initially created
     */
    public boolean isFirstCreated() {
        return firstTimeCreated;
    }
    
    /**
     * This method makes the unit initially created
     */
    public void firstTime() {
        firstTimeCreated = true;
    }
    
    /**
     * This method selects the unit
     */
    public void select() {
        selected = true;
    }
    
    /**
     * This method deselects all units
     */
    public void deselect() {
        
        selected = false;
        for(int x = 0; x < 26; x++) {
            for(int y = 0; y < 16; y++) {
                if(Board.tiles[x][y].getImag().getEffect() != null) {
                    Board.tiles[x][y].getImag().setEffect(null);
                }
            }
        }
        for(int i = 0; i < Game.players.size(); i++) {
            if(Game.players.get(i).isTurn()) {
                Player.resetLocks(Game.players.get(i).getColor(), false, true);
            }
        }
        MouseClickHandler.pieceSelected = false;
    }
    
    /**
     * Units with no terrain movement bonuses will have no penalty reduction
     * @param te is the tile environment
     * @return 0
     */
    public int getPenaltyReduction(TileEnvironment te) {
        return 0;
    }
    
    /**
     * Units with no abilities will not perform special actions
     */
    public void useAbility(Player player) {
        System.out.println("This unit does not have an ability");
    }
    
    /**
     * This method sets the amount of tiles a unit can move back to its speed when the turn is finished
     * @param storedColor is the player color
     */
    public static void resetMovements(Color storedColor) {
        for(int x = 0; x < 26; x++) {
            for(int y = 0; y < 16; y++) {
                if(Unit.class.isInstance(Board.tiles[x][y].getEntity()) && Board.tiles[x][y].getEntity().getTeam().getColor().equals(storedColor)) {
                    Board.tiles[x][y].getEntity().resetMovement();
                    highlightUnits(findColor(storedColor), textColor, x, y);
                }
            }
        }
    }
    
    /**
     * This method finds the color of the player
     * @param storedColor is the color of the player
     * @return color
     */
    private static Color findColor(Color storedColor) {
        if(storedColor.equals(Color.RED)) {
            System.out.println("Color is red my boi");

            textColor = "red";
            return Color.RED;
        }else if(storedColor.equals(Color.BLUE)) {
            System.out.println("Color is blue my boi");

            textColor = "blue";
            return Color.BLUE;
        }else if(storedColor.equals(Color.PURPLE)) {
            textColor = "purple";
            System.out.println("Color is purple my boi");
            return Color.PURPLE;
        }else{
            textColor = "yellow";
            System.out.println("Color is yellow my boi");

            return Color.YELLOW;
        }
    }
    
    /**
     * This method highlights units that are able to perform actions
     * @param color is the color
     * @param textColor is the color text of the unit
     * @param x is the x coordinate
     * @param y is the y coordinate
     */
    private static void highlightUnits(Color color, String textColor, int x, int y) {
        String url = Board.tiles[x][y].getEntity().getPath();
        System.out.println(url);
        Board.tiles[x][y].getEntity().getI().setImage(new Image(url.substring(0, url.indexOf("its/")) + url.substring(url.indexOf("its/")
            ,url.indexOf('.')) + "-" + textColor + url.substring(url.indexOf('.'), url.length())));
    }
    
    /**
     * This method unhighlights a unit when it has no actions left
     */
    public void unhighlightUnit() {
        getI().setImage(new Image("file:assets/units/" + this.toString().toLowerCase() + ".png"));
    }
    
    /**
     * This method resets the amount of tiles a unit can move to its speed
     */
    public void resetMovement() {
        movementsLeft = speed;
    }
    
    /**
     * This method makes it so the unit is no longer initially created
     */
    public void notFirstAnymore() {
        firstTimeCreated = false;
        
    }
    
    /**
     * This method initializes all the unit textures generally and for all teams
     */
    private void fillUnitTextures() {
        Image a = new Image("file:assets/units/peasant.png");
        Image b = new Image("file:assets/units/peasant-red.png");
        Image c = new Image("file:assets/units/peasant-blue.png");
        Image d = new Image("file:assets/units/peasant-yellow.png");
        Image e = new Image("file:assets/units/peasant-purple.png");
        Image f = new Image("file:assets/units/swordsman.png");
        Image g = new Image("file:assets/units/swordsman-red.png");
        Image h = new Image("file:assets/units/swordsman-blue.png");
        Image i = new Image("file:assets/units/swordsman-yellow.png");
        Image j = new Image("file:assets/units/swordsman-purple.png");
        Image k = new Image("file:assets/units/cavalry.png");
        Image l = new Image("file:assets/units/cavalry-red.png");
        Image m = new Image("file:assets/units/cavalry-blue.png");
        Image n = new Image("file:assets/units/cavalry-purple.png");
        Image o = new Image("file:assets/units/cavalry-yellow.png");
        Image p = new Image("file:assets/units/spearman.png");
        Image q = new Image("file:assets/units/spearman-red.png");
        Image r = new Image("file:assets/units/spearman-blue.png");
        Image s = new Image("file:assets/units/spearman-purple.png");
        Image t = new Image("file:assets/units/spearman-yellow.png");
        Image u = new Image("file:assets/units/assassin.png");
        Image v = new Image("file:assets/units/assassin-red.png");
        Image w = new Image("file:assets/units/assassin-blue.png");
        Image x = new Image("file:assets/units/assassin-purple.png");
        Image y = new Image("file:assets/units/assassin-yellow.png");
        Image z = new Image("file:assets/units/warrior.png");
        Image aa = new Image("file:assets/units/warrior-red.png");
        Image bb = new Image("file:assets/units/warrior-blue.png");
        Image cc = new Image("file:assets/units/warrior-purple.png");
        Image dd = new Image("file:assets/units/warrior-yellow.png");
        Image ee = new Image("file:assets/units/mage.png");
        Image ff = new Image("file:assets/units/mage-red.png");
        Image gg = new Image("file:assets/units/mage-blue.png");
        Image hh = new Image("file:assets/units/mage-purple.png");
        Image ii = new Image("file:assets/units/mage-yellow.png");
        Image jj = new Image("file:assets/units/cleric.png");
        Image kk = new Image("file:assets/units/cleric-red.png");
        Image ll = new Image("file:assets/units/cleric-blue.png");
        Image mm = new Image("file:assets/units/cleric-purple.png");
        Image nn = new Image("file:assets/units/cleric-yellow.png");
        Image oo = new Image("file:assets/units/necromancer.png");
        Image pp = new Image("file:assets/units/necromancer-red.png");
        Image qq = new Image("file:assets/units/necromancer-blue.png");
        Image rr = new Image("file:assets/units/necromancer-purple.png");
        Image ss = new Image("file:assets/units/necromancer-yellow.png");
        Image tt = new Image("file:assets/units/archer.png");
        Image uu = new Image("file:assets/units/archer-red.png");
        Image vv = new Image("file:assets/units/archer-blue.png");
        Image ww = new Image("file:assets/units/archer-purple.png");
        Image yy = new Image("file:assets/units/archer-yellow.png");
        Image zz = new Image("file:assets/units/siege.png");
        Image aaa = new Image("file:assets/units/siege-red.png");
        Image bbb = new Image("file:assets/units/siege-blue.png");
        Image ccc = new Image("file:assets/units/siege-purple.png");
        Image ddd = new Image("file:assets/units/siege-yellow.png");

        Image[] tmp = {a, b, c, e, d, f, g, h, j, i, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, aa, bb, cc, dd, ee, ff, gg, hh, ii, jj, kk, ll, 
                mm, nn, oo, pp, qq, rr, ss, tt, uu, vv, ww, yy, zz, aaa, bbb, ccc, ddd};
        
        for(int xx = 0; xx < 55; xx+=5) {
            unitTextures[xx / 5] = tmp[xx];
            redTeamTextures[xx / 5] = tmp[xx + 1];
            blueTeamTextures[xx / 5] = tmp[xx + 2];
            purpleTeamTextures[xx / 5] = tmp[xx + 3];
            yellowTeamTextures[xx / 5] = tmp[xx + 4];
        }
    }
    
    /**
     * This method initializes all the unit texture urls generally and for all teams
     */
    private void fillUnitURLS() {
        String a = new String("file:assets/units/peasant.png");
        String b = new String("file:assets/units/peasant-red.png");
        String c = new String("file:assets/units/peasant-blue.png");
        String d = new String("file:assets/units/peasant-yellow.png");
        String e = new String("file:assets/units/peasant-purple.png");
        String f = new String("file:assets/units/swordsman.png");
        String g = new String("file:assets/units/swordsman-red.png");
        String h = new String("file:assets/units/swordsman-blue.png");
        String i = new String("file:assets/units/swordsman-yellow.png");
        String j = new String("file:assets/units/swordsman-purple.png");
        String k = new String("file:assets/units/cavalry.png");
        String l = new String("file:assets/units/cavalry-red.png");
        String m = new String("file:assets/units/cavalry-blue.png");
        String n = new String("file:assets/units/cavalry-purple.png");
        String o = new String("file:assets/units/cavalry-yellow.png");
        String p = new String("file:assets/units/spearman.png");
        String q = new String("file:assets/units/spearman-red.png");
        String r = new String("file:assets/units/spearman-blue.png");
        String s = new String("file:assets/units/spearman-purple.png");
        String t = new String("file:assets/units/spearman-yellow.png");
        String u = new String("file:assets/units/assassin.png");
        String v = new String("file:assets/units/assassin-red.png");
        String w = new String("file:assets/units/assassin-blue.png");
        String x = new String("file:assets/units/assassin-purple.png");
        String y = new String("file:assets/units/assassin-yellow.png");
        String z = new String("file:assets/units/warrior.png");
        String aa = new String("file:assets/units/warrior-red.png");
        String bb = new String("file:assets/units/warrior-blue.png");
        String cc = new String("file:assets/units/warrior-purple.png");
        String dd = new String("file:assets/units/warrior-yellow.png");
        String ee = new String("file:assets/units/mage.png");
        String ff = new String("file:assets/units/mage-red.png");
        String gg = new String("file:assets/units/mage-blue.png");
        String hh = new String("file:assets/units/mage-purple.png");
        String ii = new String("file:assets/units/mage-yellow.png");
        String jj = new String("file:assets/units/cleric.png");
        String kk = new String("file:assets/units/cleric-red.png");
        String ll = new String("file:assets/units/cleric-blue.png");
        String mm = new String("file:assets/units/cleric-purple.png");
        String nn = new String("file:assets/units/cleric-yellow.png");
        String oo = new String("file:assets/units/necromancer.png");
        String pp = new String("file:assets/units/necromancer-red.png");
        String qq = new String("file:assets/units/necromancer-blue.png");
        String rr = new String("file:assets/units/necromancer-purple.png");
        String ss = new String("file:assets/units/necromancer-yellow.png");
        String tt = new String("file:assets/units/archer.png");
        String uu = new String("file:assets/units/archer-red.png");
        String vv = new String("file:assets/units/archer-blue.png");
        String ww = new String("file:assets/units/archer-purple.png");
        String yy = new String("file:assets/units/archer-yellow.png");
        String zz = new String("file:assets/units/siege.png");
        String aaa = new String("file:assets/units/siege-red.png");
        String bbb = new String("file:assets/units/siege-blue.png");
        String ccc = new String("file:assets/units/siege-purple.png");
        String ddd = new String("file:assets/units/siege-yellow.png");
        String[] tmp = {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, aa, bb, cc, dd, ee, ff, gg, hh, ii, jj, kk, ll, 
                mm, nn, oo, pp, qq, rr, ss, tt, uu, vv, ww, yy, zz, aaa, bbb, ccc, ddd};
        for(int xx = 0; xx < 55; xx++) {
            urls[xx] = tmp[xx];
        }
    }
    
    /**
     * This method updates unit's positions when they move
     */
    public void updatePosition(double x, double y, boolean moving) {
        if(Board.tiles[(int) xCoord][(int) yCoord].hasBuilding()) {
            Board.tiles[(int)xCoord][(int)yCoord].setEntity(Board.tiles[(int)xCoord][(int)yCoord].getSavedEntity());
            Board.tiles[(int)xCoord][(int)yCoord].setSavedUnit(null);
            System.out.println("PREVIOUS COORDINATES REPLACED WITH THE SAVED CITY");
        }else {
            Board.tiles[(int)xCoord][(int)yCoord].setEntity(null);
            Board.tiles[(int)xCoord][(int)yCoord].setSavedUnit(null);
            System.out.println("PREVIOUS COORDINATES WERE SET TO NULL");
        }
        if(moving) {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.7), iv);          
            transition.setToX((x - xCoord) * 50);
            transition.setToY((y - yCoord) * 50);
            transition.setCycleCount(1);
            transition.setAutoReverse(true);
            
            TranslateTransition transition2 = new TranslateTransition(Duration.seconds(0.7), heart); 
            transition2.setToX((x - xCoord) * 50);
            transition2.setToY((y - yCoord) * 50);
            transition2.setCycleCount(1);
            transition2.setAutoReverse(true);
            
            TranslateTransition transition3 = new TranslateTransition(Duration.seconds(0.7), healthDisplayer); 
            transition3.setToX((x - xCoord) * 50);
            transition3.setToY((y - yCoord) * 50);
            transition3.setCycleCount(1);
            transition3.setAutoReverse(true);

            transition.play();
            transition2.play();
            transition3.play();
            MouseClickHandler.moving = true;
            transition.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                @Override 
                public void handle(ActionEvent actionEvent) {
                    xCoord = x;
                    yCoord = y;
                    Board.g.getChildren().removeAll(iv, heart, healthDisplayer);
                    initializeView(iv.getImage());
                    initializeHeart();
                    initializeHealthText();
                    Board.g.getChildren().addAll(iv, heart, healthDisplayer);
                    iv.setOnMousePressed(Board.tiles[(int) x][(int) y]::processMouseClick);
                    healthDisplayer.setOnMousePressed(Board.tiles[(int)x][(int)y]::processMouseClick);
                    heart.setOnMousePressed(Board.tiles[(int)x][(int)y]::processMouseClick);
                    MouseClickHandler.moving = false;
                }
            });

            System.out.println("Unit's coordinates are: " + iv.getX() + ", " + iv.getY());
        }else {
            xCoord = x;
            yCoord = y;
            Image m = iv.getImage();
            iv = null;
            heart = null;
            healthDisplayer = null;
            initializeView(m);
            initializeHeart();
            initializeHealthText();
            Board.g.getChildren().addAll(iv, heart, healthDisplayer);
        }

    }

    /**
     * This method returns the unit's health
     */
    public int getHP() {
        return hp;
    }
    
    /**
     * This method sets the unit's health to the variable passed in
     */
    public void setHP(int hp) {
        this.hp = hp;
    }
    
    /**
     * This method returns the unit's max health
     * @return maxHP
     */
    public int getMaxHP() {
        return maxHP;
    }
    
    /**
     * This method returns the unit's damage
     * @return damage
     */
    public int getDamage() {
        return damage;
    }
    
    /**
     * This method returns the unit's speed
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * This method returns the number of tiles a unit can move
     */
    public int getMovements() {
        return movementsLeft;
    }
    
    /**
     * This method set the number of tiles a unit can move to the variable passed in
     */
    public void setMovements(int movementsLeft) {
        this.movementsLeft = movementsLeft;
    }
    
    /**
     * This method returns the unit's range
     */
    public int getRange() {
        return range;
    }
    
    /**
     * General method, should never get here
     */
    public String getPath() {
        return null;
    }
    
    /**
     * This method returns the unit's cost
     * @return cost
     */
    public int getCost() {
        return cost;
    }
    
    /**
     * This method returns the unit's type
     * @return type
     */
    public UnitType getType() {
        return type;
    }
    
    /**
     * General method, should never get here
     * @return null
     */
    public static Image getIcon() {
        return null;
    }
    
    /**
     * This method sets the player the unit belongs to to the variable passed in
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * This method initializes the heart texture
     */
    private void initializeHeart(){
        
        heart = new ImageView(new Image("file:assets/pixel-heart.png"));
        heart.setX(xCoord);
        heart.setY(yCoord);
        heart.setFitWidth(50);
        heart.setFitHeight(50);
        heart.relocate(xCoord * 50 + 6, yCoord * 50 + 6);
        g.getChildren().add(heart);
        if(!g.getChildren().contains(heart))
            g.getChildren().add(heart);
    }
    
    /**
     * This method initializes the health text
     */
    private void initializeHealthText() {
        
        healthDisplayer = new Text(this.xCoord, this.yCoord, Integer.toString(hp));
        healthDisplayer.setFill(Color.WHITE);
        healthDisplayer.setFont(Font.loadFont("file:resources/ancient.ttf", 17));

        //healthDisplayer.setFont(Font.loadFont(this.getClass().getClassLoader().getResource("fonts/AncientModernTales-a7Po.ttf").toExternalForm(), 14));
        healthDisplayer.relocate(xCoord * 50 + 38, yCoord * 50 + 37);
        if(!g.getChildren().contains(healthDisplayer))
            g.getChildren().add(healthDisplayer);        
    }
    
    /**
     * This method returns the test text
     */
    public Text getTempText() {
        return displayed;
    }
    
    /**
     * This method returns the heart texture
     */
    public ImageView getHeart() {
        return heart;
    }
    
    /**
     * This method returns the text displayed on the heart
     */
    public Text getHealth() {
        return healthDisplayer;
    }
    
    /**
     * This method determines whether the unit is selected or not
     */
    public boolean isSelected() {
        return selected;
    }
    
    /**
     * This method prints the text when using units as strings
     */
    public String toString() {
        return text;
    }
    
    /**
     * This method defines the combat system
     * @param unit is the unit being attacked
     * @param factor is the damage multiplier. Depends on whether the unit is attacking or countering
     * @return false if the attacked unit dies, true if it survives
     */
    public boolean attack(Unit unit, float factor) {
        
        try {

            TranslateTransition transition = new TranslateTransition(Duration.seconds(Math.abs(0.1)), iv);
            transition.setToX((unit.getI().getX() - xCoord) * 50);
            transition.setToY((unit.getI().getY() - yCoord) * 50);
            transition.setCycleCount(2);
            transition.setAutoReverse(true);
            
            TranslateTransition transition2 = new TranslateTransition(Duration.seconds(Math.abs(0.1)), heart); 
            transition2.setToX((unit.getI().getX() - xCoord) * 50);
            transition2.setToY((unit.getI().getY() - yCoord) * 50);
            transition2.setCycleCount(2);
            transition2.setAutoReverse(true);
            
            TranslateTransition transition3 = new TranslateTransition(Duration.seconds(Math.abs(0.1)), healthDisplayer); 
            transition3.setToX((unit.getI().getX() - xCoord) * 50);
            transition3.setToY((unit.getI().getY() - yCoord) * 50);
            transition3.setCycleCount(2);
            transition3.setAutoReverse(true);
            
            transition.play();
            transition2.play(); 
            transition3.play();
            MouseClickHandler.moving = true;
            System.out.println((damage * (hp / maxHP)));
            float damageCalc = (((damage * ((float)hp / (float)maxHP)) * factor + getBuff()));
            unit.setHP((int)(unit.getHP() - (damageCalc - damageCalc * Board.tiles[(int) unit.getI().getX()][(int) unit.getI().getY()].
                    getEnvironment().getDefenseBonus())));
            unit.getHealth().setText(Integer.toString(unit.getHP()));
            transition.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                @Override 
                public void handle(ActionEvent actionEvent) {
                    MouseClickHandler.moving = false;
                }
            });
            if(unit.getHP() <= 0) {
                unit.die();
                deselect();
                movementsLeft = 0;
                unhighlightUnit();
                return false;
            }else {
                Board.tiles[(int) unit.getI().getX()][(int) unit.getI().getY()].setEntity(unit);    
            }
        }catch(Exception e) {
            System.exit(0);
        }
        deselect();
        movementsLeft = 0;
        unhighlightUnit();
        return true;
    }
    
    /**
     * This method is called when a unit's health reaches 0
     */
    private void die() {
        Board.tiles[(int) xCoord][(int) yCoord].setEntity(null);
        Board.g.getChildren().removeAll(iv, heart, healthDisplayer);
    }
    
    /**
     * This method is specifically made for wizards for their area of effect damage
     * @param source is the tile being shot at
     * @param selectedUnit is the mage attacking
     */
    public static void attackAdjacents(Tile source, Unit selectedUnit) {
        try {
             if(source.getX() + 1 < 26 && Board.tiles[(int)source.getX() + 1][(int) source.getY()].hasUnit()
                    && !Board.tiles[(int)source.getX() + 1][(int) source.getY()].getEntity().getTeam().getColor().equals(selectedUnit.getTeam().getColor())) {
                selectedUnit.attack((Unit)(Board.tiles[(int)source.getX() + 1][(int) source.getY()].getEntity()), 0.5F);
            }if(source.getX() - 1 >= 0 && Board.tiles[(int)source.getX() - 1][(int) source.getY()].hasUnit()
                    && !Board.tiles[(int)source.getX() - 1][(int) source.getY()].getEntity().getTeam().getColor().equals(selectedUnit.getTeam().getColor())) {
                selectedUnit.attack((Unit)(Board.tiles[(int)source.getX() - 1][(int) source.getY()].getEntity()), 0.5F);
            }if(source.getY() + 1 < 16 && Board.tiles[(int)source.getX()][(int) source.getY() + 1].hasUnit()
                    && !Board.tiles[(int)source.getX()][(int) source.getY() + 1].getEntity().getTeam().getColor().equals(selectedUnit.getTeam().getColor())) {
                selectedUnit.attack((Unit)(Board.tiles[(int)source.getX()][(int) source.getY() + 1].getEntity()), 0.5F);
            }if(source.getY() - 1 >= 0 && Board.tiles[(int)source.getX()][(int) source.getY() - 1].hasUnit()
                    && !Board.tiles[(int)source.getX()][(int) source.getY() - 1].getEntity().getTeam().getColor().equals(selectedUnit.getTeam().getColor())) {
                selectedUnit.attack((Unit)(Board.tiles[(int)source.getX()][(int) source.getY() - 1].getEntity()), 0.5F);
                }
        }catch(IndexOutOfBoundsException e) {
            System.exit(0);
        }
    }
     
    /**
     * Timer for counter attacks to attack after being attacked, and not instantly
     * @param source is the tile clicked on
     */
     public static void counter(Tile source, Unit selectedUnit) {
         Timeline time = new Timeline();
         time.setCycleCount(1);
         time.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent arg0) {
                  seconds--;
                  if(seconds == 0) {
                      if(source.getEntity().getRange() >= Board.getDistance(Board.tiles[(int) selectedUnit.getI().getX()][(int) selectedUnit.getI().getY()], source))
                          ((Unit) source.getEntity()).attack(selectedUnit, 0.7F);
                      seconds = 1;
                      time.stop();
                  }
             }
             
         }));
         time.play(); 
     }
     
     public int getBuff() {
         System.out.println("This unit does not have any buffs");
         return 0;
     }
}