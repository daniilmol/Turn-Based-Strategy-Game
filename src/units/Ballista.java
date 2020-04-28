package units;

import entities.Unit;
import javafx.scene.image.Image;
import main.Player;

public class Ballista extends Unit{
    
    
    
    public Ballista(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Structure");
        initializeStats();
    }
    
    private void initializeStats() {
        hp = 14;
        maxHP = 14;
        damage = 12;
        speed = 3;
        range = 5;
        cost = 8;
    }
    

}
