package entities;

import biomes.Forest;
import environment.Board;
import main.Player;
import units.Rogue;

/**
 * This method determines the abilities and skills of each unit
 * @author Daniil Molchanov
 */
public class Ability {
    
    /**
     * This ability is made for assassins specifically to gain invisibility in forests
     * @param player is the player who's turn is active
     */
    public void gainInvisibility(Player player) {
        for(int x = 0; x < 26; x++) {
            for(int y = 0; y < 16; y++) {
                if(Board.tiles[x][y].hasUnit() && Rogue.class.isInstance(Board.tiles[x][y].getEntity())
                        && !Board.tiles[x][y].getEntity().getTeam().getColor().equals(player.getColor())
                        && Forest.class.isInstance(Board.tiles[x][y].getEnvironment())) {
                    Board.tiles[x][y].getEntity().getI().setOpacity(0);
                    Board.tiles[x][y].getSavedUnit().getHeart().setOpacity(0);
                    Board.tiles[x][y].getSavedUnit().getHealth().setOpacity(0);
                }else if(Board.tiles[x][y].hasUnit() && Rogue.class.isInstance(Board.tiles[x][y].getEntity())){
                    Board.tiles[x][y].getEntity().getI().setOpacity(1);
                    Board.tiles[x][y].getSavedUnit().getHeart().setOpacity(1);
                    Board.tiles[x][y].getSavedUnit().getHealth().setOpacity(1);
                }
            }
        }
    }
}
