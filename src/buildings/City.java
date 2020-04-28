package buildings;

import entities.Building;
import javafx.scene.image.Image;
import main.Player;

public class City extends Building{

    private static Image[] cityIcons = new Image[5];
    private boolean unitCreatedForFirstTime = false;
    private boolean containsUnit = false;
    
    public City(String name, Image image, String text, double x, double y, Player ownerShip) {
        super(name, image, text, x, y, ownerShip);
    }
    
    public static void setIcons() {

            cityIcons[0] = new Image("file:assets/red-city.png");
            cityIcons[1] = new Image("file:assets/blue-city.png");
            cityIcons[2] = new Image("file:assets/yellow-city.png");
            cityIcons[3] = new Image("file:assets/purple-city.png");
            cityIcons[4] = new Image("file:assets/pixel-neutral-city.png");

    }
    
    
    
    public void noLongerFirstInCity() {
        unitCreatedForFirstTime = false;
    }
    
    public void unitInside(boolean x) {
        containsUnit = x;
    }
    
    public boolean containsFirstTimeCreatedUnit() {
        return unitCreatedForFirstTime;
    }
    
    public boolean containsUnit() {
        return containsUnit;
    }
    
    public static Image[] getIcons() {
        return cityIcons;
    }
    
    public String getLabel() {
        return text;
    }

}
