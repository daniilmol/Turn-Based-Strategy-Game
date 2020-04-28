package units;

import entities.Unit;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Player;

public class Swordsman extends Unit{
    private final static String URL = "file:assets/units/swordsman.png";
    private static Image icon = new Image(URL);
    
    public Swordsman(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Foot");        
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
