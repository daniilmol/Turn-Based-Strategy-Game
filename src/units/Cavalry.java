package units;

import entities.Unit;
import javafx.scene.image.Image;
import main.Player;

public class Cavalry extends Unit{

    private final static String URL = "file:assets/units/cavalry.png";
    private static Image icon = new Image(URL);
    
    public Cavalry(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Mounted");        
        initializeStats();
        
    }
    
    private void initializeStats() {
        hp = 24;
        maxHP = 24;
        damage = 7;
        speed = 7;
        range = 1;
        cost = 9;
        movementsLeft = speed;
    }
    
    public static Image getIcon() {
        return icon;
    }
    
    public String getPath() {
        return URL;
    }
    
}
