package units;

import entities.Unit;
import javafx.scene.image.Image;
import main.Player;

public class Cleric extends Unit{

    private final static String URL = "file:assets/units/cleric.png";
    private static Image icon = new Image(URL);
    
    public Cleric(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Magic");        
        initializeStats();
        
    }
    
    private void initializeStats() {
        hp = 22;
        maxHP = 22;
        damage = 6;
        speed = 4;
        range = 1;
        cost = 5;
        movementsLeft = speed;
    }
    
    public static Image getIcon() {
        return icon;
    }
    
    public String getPath() {
        return URL;
    }
    
    
}
