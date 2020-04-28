package biomes;

import environment.TileEnvironment;
import javafx.scene.image.Image;

public class Grass extends TileEnvironment{

    public static int spawnChance = 46;
    private static int baseChance = spawnChance;

    private Image image1;
    private Image image2;
    private Image image3;
    private Image[] grasses = new Image[3];
    private String[] urls = new String[3];
    
    public Grass() {
        super("Grass");
        movementPenalty = 0;
        defenseBonus = 0;
        String pixelGrass = "file:assets/pixel-grass1.png";
        String pixelGrass2 = "file:assets/pixel-grass2.png";
        String pixelGrass3 = "file:assets/pixel-grass3.png";
 
        image1 = new Image(pixelGrass);
        image2 = new Image(pixelGrass2);
        image3 = new Image(pixelGrass3);
        
        grasses[0] = image1;
        grasses[1] = image2;
        grasses[2] = image3;
        
        urls[0] = pixelGrass;
        urls[1] = pixelGrass2;
        urls[2] = pixelGrass3;

    }
    
    public int getSpawnChance() {
        return spawnChance;
    }
    
    public Image[] getGraphics() {
        return grasses;
    }
    
    public String[] getPaths() {
        return urls;
    }
    
    public static int getChance() {
        return baseChance;
    }
    
    public int getPenalty() {
        return movementPenalty;
    }
    
}
