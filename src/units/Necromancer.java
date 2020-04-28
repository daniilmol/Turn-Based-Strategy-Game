package units;

import entities.Unit;
import javafx.scene.image.Image;
import main.Player;

public class Necromancer extends Unit{
    private final static String URL = "file:assets/units/necromancer.png";
    private static Image icon = new Image(URL);
    
    public Necromancer(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Magic");        
        initializeStats();
        
    }
    
    private void initializeStats() {
        hp = 14;
        maxHP = 14;
        damage = 3;
        speed = 2;
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
