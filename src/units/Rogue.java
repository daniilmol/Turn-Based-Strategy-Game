package units;

import entities.Ability;
import entities.Unit;
import environment.TileEnvironment;
import javafx.scene.image.Image;
import main.Player;

public class Rogue extends Unit{

    private final static String URL = "file:assets/units/assassin.png";
    private Ability invisibility;
    private static Image icon = new Image(URL);    
    
    public Rogue(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Foot");        
        initializeStats();
        invisibility = new Ability();
    }
    
    private void initializeStats() {
        hp = 18;
        maxHP = 18;
        damage = 8;
        speed = 4;
        range = 1;
        cost = 7;
        movementsLeft = speed;
    }
    
    public static Image getIcon() {
        return icon;
    }
    
    public String getPath() {
        return URL;
    }
    
    public int getPenaltyReduction(TileEnvironment environment) {
        return environment.getPenalty();
    }
    
    public void useAbility(Player player) {
        invisibility.gainInvisibility(player);
    }
    
    
}
