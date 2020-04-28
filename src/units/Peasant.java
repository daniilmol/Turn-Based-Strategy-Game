package units;

import entities.Unit;
import environment.Board;
import javafx.scene.image.Image;
import main.Player;

public class Peasant extends Unit{
    
    private final static String URL = "file:assets/units/peasant.png";
    private static Image icon = new Image(URL);

    public Peasant(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip, "Foot");
        initializeStats();
    }
    
    private void initializeStats() {
        hp = 18;
        maxHP = 18;
        damage = 4;
        speed = 3;
        range = 1;
        cost = 4;
        movementsLeft = speed;
    }
    
    public static Image getIcon() {
        return icon;
    }
    
    public String getPath() {
        return URL;
    }
    
    public int getBuff() {
        int adjacentPeasants = 0;
        for(int x = -1; x < 2; x++) {
            for(int y = -1; y < 2 && (x != 0 || y != 0); y++) {
                if(xCoord + x >= 0 && xCoord + x < 26 && yCoord + y >= 0 && yCoord + y < 16 && Board.tiles[(int) (xCoord + x)][(int) (yCoord + y)].hasUnit()) {
                    if(Peasant.class.isInstance(Board.tiles[(int) (xCoord + x)][(int) (yCoord + y)].getSavedUnit())
                            && Board.tiles[(int) (xCoord + x)][(int) (yCoord + y)].getSavedUnit().getTeam().getColor().equals(getTeam().getColor()))
                        adjacentPeasants++;
                }
            }
        }
        return adjacentPeasants;
    }
}
