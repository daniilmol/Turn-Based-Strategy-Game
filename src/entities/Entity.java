package entities;

import AI.FiniteStateMachine;
import environment.Board;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.Player;

/**
 * This class represents an entity on the board including buildings and units
 * @author Daniil Molchanov
 */
public class Entity extends StackPane{
   
    /**
     * This is the name of the entity
     */
    protected String name;
    
    /**
     * This is the label of the entity
     */
    protected String text;
    
    /**
     * This is the texture of the entity
     */
    protected ImageView iv;
    
    /**
     * This is the entity's x coordinate
     */
    protected double xCoord;
    
    /**
     * This is the entity's y coordinate
     */
    protected double yCoord;
    
    /**
     * This is the group that contains the textures
     */
    protected Group g = new Group();
    
    /**
     * This is the text that's displayed for testing
     */
    protected Text displayed;
    
    /**
     * This is the player the entity belongs to
     */
    protected Player player;
    
    private FiniteStateMachine fsm;
    
    /**
     * 
     * @param name is the entity name
     * @param image is the entity texture
     * @param text is the entity label
     * @param xCoord is the entity x coordinate
     * @param yCoord is the entity y coordinate
     * @param ownerShip is the player the entity belongs to
     */
    public Entity(String name, Image image, String text, double xCoord, double yCoord, Player ownerShip) {
        getChildren().add(g);
        this.text = text;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        if(image != null)
            initializeView(image);
          
        player = ownerShip;
        if(player.isBot())
            fsm = new FiniteStateMachine(this);
    }
    
    /**
     * General method, should never get here
     * @return false
     */
    public boolean isSelected() {
        System.out.println("SHOULD NEVER GET TO THIS IS SELECTED METHOD");
        return false;
    }
    
    /**
     * General method, should never get here
     */
    public void select() {
        System.out.println("SHOULD NEVER GET TO THIS SELECT METHOD");
    }
    
    /**
     * General method, should never get here
     * @return
     */
    public String getPath() {
        return "Shouldn't get here";
    }
    
    /**
     * General method, should never get here
     */
    public void deselect() {
        System.out.println("SHOULD NEVER GET TO THIS DESELECT METHOD");
    }
    
    /**
     * This method initializes the texture of the entity
     * @param image is the image containing the url
     */
    protected void initializeView(Image image) {

        System.out.println("Initializing the view");
        iv = new ImageView(image);
        iv.setFitWidth(50);
        iv.setFitHeight(50);
        iv.setPreserveRatio(true);
        iv.setX(xCoord);
        iv.setY(yCoord);
        iv.relocate(xCoord * 50, yCoord * 50);
        iv.setOnMousePressed(this::mouseClick);

        if(!g.getChildren().contains(iv))
            g.getChildren().add(iv);
   
    }
    
    /**
     * General method, should never get here
     * @return false
     */
    public boolean isFirstCreated() {
        System.out.println("Incorrect method called");
        return false;
    }
    
    /**
     * General method, should never get here
     */
    public void notFirstAnymore() {
        System.out.println("Should never get to this method");
    }
    
    /**
     * General method, units with no abilities won't do anything
     * @param player is the player who's turn is active
     */
    public void useAbility(Player player) {
        System.out.println("This unit does not have an ability");
    }
    
    /**
     * General method, should never get here
     */
    public static void resetMovements() {
        System.out.println("Incorrect reset method");
    }
    
    /**
     * General method, should never get here
     */
    public void resetMovement() {
        
    }
    
    /**
     * General method, should never get here
     * @param e
     */
    public void mouseClick(MouseEvent e) {
    }
    
    /**
     * Returns the entity's image
     * @return iv
     */
    public ImageView getI() {
        return iv;
    }
    
    /**
     * Returns the entity's testing text
     * @return displayed
     */
    public Text getTempText() {
        return displayed;
    }
    
    /**
     * General method, should never get here
     */
    public ImageView getHeart() {
        return null;
    }
    
    /**
     * General method, should never get here
     * @return null
     */
    public Text getHealth() {
        return null;
    }
    
    /**
     * General method, should never get here
     */
    public void setHealth() {
        
    }
 
    /**
     * Returns the player the entity belongs to
     * @return player
     */
    public Player getTeam() {
        return player;
    }
    
    /**
     * Returns the label
     * @return text
     */
    public String getLabel() {
        return text;
    }
    
    /**
     * Sets the entity's label to the variable passed in
     * @param label is the new label the entity will have
     */
    public void setLabel(String label) {
        text = label;
    }
    
    /**
     * General method, should never get here
     */
    public int getMovements() {
        System.out.println("incorrect get movement method");
        return -1;
    }
    
    /**
     * General method, should never get here
     * @param is the number of movements left
     */
    public void setMovements(int movementsLeft) {
        System.out.println("incorrect set movement method");
    }

    /**
     * This method sets the position of cities when they are first spawned in
     * @param x is the x coordinate
     * @param y is the y coordinate
     * @param moving represents whether they are moving or not
     */
    public void updatePosition(double x, double y, boolean moving) {
        
        xCoord = x;
        yCoord = y;
        Image m = iv.getImage();
        iv = null;
        System.out.println("UPDATE METHOD CALLED IN ENTITY");
        initializeView(m);

        Board.g.getChildren().add(iv);
    }
    
    /**
     * This method sets the entity's player
     * @param player is the new player the entity will have
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * General method, should never get here
     * @return null
     */
    public static Image getIcon() {
        return null;
    }

    /**
     * General method, should never get here
     */
    public void unhighlightUnit() {
        System.out.println("Incorrect unhighlight method called");        
    }

    /**
     * General method, should never get here
     * @return
     * @throws Exception
     */
    public int getHP() throws Exception {
        throw new Exception("Shouldn't ever get to this method");
    }

    /**
     * General method, should never get here
     * @param hp 
     * @throws Exception
     */
    public void setHP(int hp) throws Exception {
        throw new Exception("Shouldn't ever get to this method");        
    }

    /**
     * General method, should never get here
     */
    public int getRange() {
        return 0;
    }
}
