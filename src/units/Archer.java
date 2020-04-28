package units;

import entities.Unit;
import javafx.scene.image.Image;
import main.Player;

public class Archer extends Unit{
    private final static String URL = "file:assets/units/archer.png";
    private static Image icon = new Image(URL);
    
    public Archer(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Foot");        
        initializeStats();
        
    }
    
    private void initializeStats() {
        hp = 18;
        maxHP = 18;
        damage = 5;
        speed = 4;
        range = 2;
        cost = 7;
        movementsLeft = speed;
    }
    
    public static Image getIcon() {
        return icon;
    }
    
    public String getPath() {
        return URL;
    }
}
