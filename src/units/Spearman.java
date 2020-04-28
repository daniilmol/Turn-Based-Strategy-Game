package units;

import entities.Unit;
import javafx.scene.image.Image;
import main.Player;

public class Spearman extends Unit{
    private final static String URL = "file:assets/units/spearman.png";
    private static Image icon = new Image(URL);
    public Spearman(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Foot");
        initializeStats();
    }
    
    private void initializeStats() {
        hp = 26;
        maxHP = 26;
        damage = 7;
        speed = 3;
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
