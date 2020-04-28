package units;

import entities.Unit;
import javafx.scene.image.Image;
import main.Player;

public class Garrison extends Unit{

    public Garrison(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Foot");
        initializeStats();
    }
    
    private void initializeStats() {
        hp = 28;
        maxHP = 28;
        damage = 5;
        speed = 3;
        range = 2;
        cost = 3;
    }
    
    
}
