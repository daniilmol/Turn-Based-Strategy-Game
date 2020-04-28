package units;

import entities.Unit;
import javafx.scene.image.Image;
import main.Player;

public class Mage extends Unit{

    private final static String URL = "file:assets/units/mage.png";
    private static Image icon = new Image(URL);
    
    public Mage(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Magic");        
        initializeStats();     
    }
    
    private void initializeStats() {
        hp = 14;
        maxHP = 14;
        damage = 6;
        speed = 4;
        range = 2;
        cost = 10;
        movementsLeft = speed;
    }
    
    public static Image getIcon() {
        return icon;
    }
    
    public String getPath() {
        return URL;
    }  
}
