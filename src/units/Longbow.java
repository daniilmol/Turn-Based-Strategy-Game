package units;

import entities.Unit;
import javafx.scene.image.Image;
import main.Player;

public class Longbow extends Unit{

    public Longbow(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Foot");
        initializeStats();
    }
    
    private void initializeStats() {
        hp = 20;
        maxHP = 20;
        damage = 6;
        speed = 2;
        range = 4;
        cost = 5;
    }
    
    
}
