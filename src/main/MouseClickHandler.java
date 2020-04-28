package main;

import entities.Unit;
import entities.Vector;
import environment.Board;
import environment.Tile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import ui.BuildingPanel;
import units.Mage;

/**
 * This class contains the main event handling code
 * @author Daniil Molchanov
 */
public class MouseClickHandler {
    
    /**
     * This is the panel that will pop up when clicking on a city
     */
    private BuildingPanel panel = new BuildingPanel(600, 600);
    
    /**
     * The player that activated the event handler
     */
    private static Player currentPlayer;
    
    /**
     * Indicates if there is a unit selected or not
     */
    public static boolean pieceSelected;
    
    /**
     * Indicates direction and tiles units can move on
     */
    private static Vector vector;
    
    /**
     * Color of text
     */
    private static String textColor;
    
    /**
     * The selected unit
     */
    private static Unit selectedUnit;
    
    public static boolean moving = false;
    /**
     * This method handles everything related with user input
     * @param e is the mouse event
     * @param source is the tile clicked on
     */
    public void handle(MouseEvent e, Tile source) {
        if(moving)
            return;
        if(e.getButton() == MouseButton.PRIMARY) {
        System.out.println("This tile is locked: " + source.isLocked());
        findCurrentPlayer();
        if(((source.hasUnit() || source.hasBuilding()) && !source.isLocked())) {
            if(source.isBuilding() && !source.getEntity().getLabel().equals("N") && !pieceSelected) {
                
                panel.setSourceCoordinates(source.getX(), source.getY());
                source.lockAll();
                Game.p.getChildren().add(panel);
                  
            }else if(source.isBuilding() || source.getEntity().getLabel().equals("N") && pieceSelected) {
                for(int x = 0; x < 26; x++) {
                    for(int y = 0; y < 16; y++) {
                        if(Board.tiles[x][y].hasUnit() && !Board.tiles[x][y].isBuilding() && Board.tiles[x][y].getEntity().isSelected()) {
                            Board.tiles[x][y].getEntity().deselect();
                            Board.tiles[(int) source.getX()][(int) source.getY()].setPiece(Board.tiles[x][y].getEntity(), true);
                            Board.tiles[(int) source.getX()][(int) source.getY()].getEntity().deselect();

                            vector.decreaseMovements(x, y, source);  
                            Board.tiles[x][y].clearList();
                            if(Board.tiles[(int)source.getX()][(int)source.getY()].getEntity().getMovements() <= 0) {
                                Board.tiles[(int)source.getX()][(int)source.getY()].getEntity().unhighlightUnit();
                            }
                            if(source.getSavedEntity().getLabel().equals("N") || !source.getSavedEntity().getTeam().getColor().equals(currentPlayer.getColor())) {
                                source.getSavedEntity().setLabel("C");
                                captureCity(source.getSavedUnit().getTeam().getColor(), source);
                                source.getSavedUnit().setMovements(0);
                                source.getSavedUnit().unhighlightUnit();
                                source.getSavedUnit().getTeam().setCities(source.getSavedUnit().getTeam().getCities() + 1);
                            }
                        }
                    }
                }
            }else if(source.isBuilding() && source.getEntity().getLabel().equals("N")){
                  
            }else if(!source.isBuilding() && !source.getEntity().getLabel().equals("N") && source.getEntity().getMovements() > 0
                    && source.getEntity().getTeam().getColor().equals(currentPlayer.getColor())){
                for(int x = 0; x < 26; x++) {
                    for(int y = 0; y < 16; y++) {
                        if(Board.tiles[x][y].hasUnit() && !Board.tiles[x][y].isBuilding() && Board.tiles[x][y].getEntity().isSelected()) {
                            Board.tiles[x][y].getEntity().deselect();
                        }
                    }
                }
                Glow selectGlow = new Glow(1.4f);   
                source.getImag().setEffect(selectGlow); 
                source.getEntity().select();
                selectedUnit = (Unit) source.getSavedUnit();
                pieceSelected = true;
                vector = new Vector(source, (Unit)source.getEntity());
            }else if(!source.isBuilding() && !source.getEntity().getLabel().equals("N")
                    && !source.getEntity().getTeam().getColor().equals(currentPlayer.getColor()) && pieceSelected
                    && selectedUnit.getRange() >= Board.getDistance(Board.tiles[(int) selectedUnit.getI().getX()][(int) selectedUnit.getI().getY()], source)) {
                        
                if(selectedUnit.attack((Unit) source.getEntity(), 1)) {
                    Unit.counter(source, selectedUnit);                        
                }if(Mage.class.isInstance(selectedUnit))
                    Unit.attackAdjacents(source, selectedUnit);
                }                      
        }else if((!source.hasUnit() && !source.isLocked())) {
            System.out.println("This tile does not have a unit");
            for(int x = 0; x < 26; x++) {
                for(int y = 0; y < 16; y++) {
                    if(Board.tiles[x][y].hasUnit() && !Board.tiles[x][y].isBuilding() && Board.tiles[x][y].getEntity().isSelected()) {
                        Board.tiles[x][y].getEntity().deselect();
                        Board.tiles[(int) source.getX()][(int) source.getY()].setPiece(Board.tiles[x][y].getEntity(), true);
                        Board.tiles[(int) source.getX()][(int) source.getY()].getEntity().deselect();

                        vector.decreaseMovements(x, y, source);  
                        Board.tiles[x][y].clearList();

                        if(Board.tiles[(int)source.getX()][(int)source.getY()].getEntity().getMovements() <= 0) {
                            Board.tiles[(int)source.getX()][(int)source.getY()].getEntity().unhighlightUnit();
                        }
                    }
                }
            }
        }
        }else if(e.getButton() == MouseButton.SECONDARY) {
            for(int x = 0; x < 26; x++) {
                for(int y = 0; y < 16; y++) {
                    if(Board.tiles[x][y].hasUnit()) {
                        System.out.println("Find unit to deselect");
                    Board.tiles[x][y].getEntity().deselect();
                    }
                }
            }
        } 
    }
    
    /**
     * This method defines functionality for city capturing
     * @param storedColor indicates the invader's color
     * @param source is the tile clicked on
     * @return color of invader
     */
    private Color captureCity(Color storedColor, Tile source) {
        if(storedColor.equals(Color.RED)) {
            textColor = "red";
            source.getSavedEntity().getI().setImage(new Image("file:assets/red-city.png"));
            source.getSavedEntity().setPlayer(source.getSavedUnit().getTeam());
            return Color.RED;
        }else if(storedColor.equals(Color.BLUE)) {
            textColor = "blue";
            source.getSavedEntity().getI().setImage(new Image("file:assets/blue-city.png"));
            source.getSavedEntity().setPlayer(source.getSavedUnit().getTeam());
            return Color.BLUE;
        }else if(storedColor.equals(Color.PURPLE)) {
            textColor = "purple";
            source.getSavedEntity().getI().setImage(new Image("file:assets/purple-city.png"));
            source.getSavedEntity().setPlayer(source.getSavedUnit().getTeam());
            return Color.PURPLE;
        }else{
            textColor = "yellow";
            source.getSavedEntity().getI().setImage(new Image("file:assets/yellow-city.png"));
            source.getSavedEntity().setPlayer(source.getSavedUnit().getTeam());
            return Color.YELLOW;
        }
    }

    /**
     * Finds out which player activated the event handler
     */
    private static void findCurrentPlayer() {
        for(int i = 0; i < Game.players.size(); i++) {
            if(Game.players.get(i).isTurn()) {
                currentPlayer = Game.players.get(i);
            }
        }
    }
}
