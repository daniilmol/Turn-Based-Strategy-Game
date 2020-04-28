package biomes;

import environment.TileEnvironment;
import javafx.scene.image.Image;

public class Mountain extends TileEnvironment{

    public static int spawnChance = 20;
    private static int baseChance = spawnChance;
    private Image image;
    private String pixelMountain;
    
    public Mountain() {
        super("Mountain");
        movementPenalty = 2;
        defenseBonus = 0.3F;
        pixelMountain = "file:assets/pixel-mountain1.png";
        image = new Image(pixelMountain);
    }
    
    public int getSpawnChance() {
        return spawnChance;
    }
    
    public Image getGraphics() {
        return image;
    }
    
    public String getPath() {
        return pixelMountain;
    }
    
    public static int getChance() {
        return baseChance;
    }
    
    public int getPenalty() {
        return movementPenalty;
    }
    
}
