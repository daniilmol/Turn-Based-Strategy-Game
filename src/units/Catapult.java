package units;

import entities.Unit;
import javafx.scene.image.Image;
import main.Player;

public class Catapult extends Unit{

    public Catapult(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Structure");
        initializeStats();
    }
    
    private void initializeStats() {
        hp = 16;
        maxHP = 16;
        damage = 12;
        speed = 2;
        range = 6;
        cost = 8;
    }
    
    
}
