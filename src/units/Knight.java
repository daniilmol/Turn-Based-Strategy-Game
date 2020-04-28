package units;

import entities.Unit;
import javafx.scene.image.Image;
import main.Player;

public class Knight extends Unit{

    public Knight(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Mounted");
        initializeStats();
    }
    
    private void initializeStats() {
        hp = 30;
        maxHP = 30;
        damage = 10;
        speed = 5;
        range = 1;
        cost = 10;
    }
    
    
}
