package units;

import biomes.Mountain;
import entities.Unit;
import environment.TileEnvironment;
import javafx.scene.image.Image;
import main.Player;

public class Warrior extends Unit{
    private final static String URL = "file:assets/units/warrior.png";
    private static Image icon = new Image(URL);
    public Warrior(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Foot");        
        initializeStats();
        
    }
    
    private void initializeStats() {
        hp = 20;
        maxHP = 20;
        damage = 10;
        speed = 4;
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
   
    public int getPenaltyReduction(TileEnvironment environment) {
        if(Mountain.class.isInstance(environment))
            return 2;
        return 0;
    }
    
}
