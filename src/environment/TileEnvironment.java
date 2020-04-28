package environment;

/**
 * This class represents the terrain environment
 * @author Daniil Molchanov
 */
public class TileEnvironment {
    
    /**
     * This is the chance that a certain environment would spawn
     */
    protected int spawnChance;
    
    /**
     * This is the penalty a unit will get for moving onto a specific tile
     */
    protected int movementPenalty;
    
    /**
     * This is the environment's name in text
     */
    private String envName;
    
    protected float defenseBonus;
    
    /**
     * Environment constructor initializes the name
     * @param name is the environment name
     */
    public TileEnvironment(String name) {
        envName = name;
        
    }
    
    /**
     * Returns the environment's chance to spawn
     * @return spawnChance
     */
    protected int getSpawnChance() {
        return spawnChance;
    }
    
    /**
     * General method, should never be called
     * @return
     */
    public int getPenalty() {
        System.out.println("THIS METHOD SHOULD NEVER BE CALLED");
        return movementPenalty;
    }
    
    /**
     * Returns the environment name when this class is used as a string
     */
    public String toString() {
        return envName;
    }
    
    public float getDefenseBonus() {
        return defenseBonus;
    }
    
}
