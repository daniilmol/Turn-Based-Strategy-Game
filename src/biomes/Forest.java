package biomes;

import environment.TileEnvironment;
import javafx.scene.image.Image;

public class Forest extends TileEnvironment{

    public static int spawnChance = 35;
    private static int baseChance = spawnChance;
    private Image image;
    private Image image2;
    private Image[] forests = new Image[2];
    private String[] urls = new String[2];
    
    public Forest() {
        super("Forest");
        defenseBonus = 0.1F;
        movementPenalty = 1;
        String pixelForest = "file:assets/pixel-forest1.png";
        String pixelForest2 = "file:assets/pixel-forest2.png";
        
        image = new Image(pixelForest);
        image2 = new Image(pixelForest2);
        
        urls[0] = pixelForest;
        urls[1] = pixelForest2;
        
        forests[0] = image;
        forests[1] = image2;
        
    } 
    
    public int getSpawnChance() {
        return spawnChance;
    }
    
    public Image[] getGraphics() {
        return forests;
    }
    
    public String[] getPath() {
        return urls;
    }
    
    public static int getChance() {
        return baseChance;
    }
    
    public int getPenalty() {
        return movementPenalty;
    }
}
