package units;

import entities.Unit;
import javafx.scene.image.Image;
import main.Player;

public class SiegeEngine extends Unit{

    private final static String URL = "file:assets/units/siege.png";
    private static Image icon = new Image(URL);
    
    public SiegeEngine(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Structure");        
        initializeStats();
        
    }
    
    private void initializeStats() {
        hp = 30;
        maxHP = 30;
        damage = 3;
        speed = 3;
        range = 1;
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
