package entities;

/**
 * This class represents the unit type
 * @author Daniil Molchanov
 */
public class UnitType {
    
    /**
     * This is a representation of unit type in text
     */
    private String type;
    
    public static String types[] = {"Foot", "Mounted", "Structure"};
    
    /**
     * Constructor determines the type
     * @param type is the unit type
     */
    public UnitType(String type) {
        this.type = type;
    }

    /**
     * Prints the type when acting as a string
     */
    public String toString() {
        return type;
    }
    
}
