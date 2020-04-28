package units;

import entities.Unit;
import javafx.scene.image.Image;
import main.Player;

public class Sorcerer extends Unit{

    public Sorcerer(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Magic");
        initializeStats();
    }
    
    private void initializeStats() {
        hp = 16;
        maxHP = 16;
        damage = 10;
        speed = 2;
        range = 2;
        cost = 7;
    }
    
    
}
