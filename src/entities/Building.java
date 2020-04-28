package entities;

import javafx.scene.image.Image;
import main.Player;

/**
 * This class represents a building
 * @author Daniil Molchanov
 */
public class Building extends Entity{

    /**
     * Building constructor initializes buildings
     * @param name is the building name
     * @param image is the building's image
     * @param text is the building's label
     * @param x is the building's x coordinate
     * @param y is the building's y coordinate
     * @param ownerShip is the player the building belongs to
     */
    public Building(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip);
    }
    
    /**
     * General method. Should never get here
     * @return null
     */
    public Image getCity() {
        return null;
    }
}
