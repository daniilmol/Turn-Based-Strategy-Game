package AI;

import java.util.Random;

import entities.Building;
import entities.Unit;
import environment.Board;
import javafx.scene.image.Image;
import main.Player;
import units.Archer;
import units.Cavalry;
import units.Cleric;
import units.Mage;
import units.Necromancer;
import units.Peasant;
import units.Rogue;
import units.SiegeEngine;
import units.Spearman;
import units.Swordsman;
import units.Warrior;

public class AI {

    private Brain brain;
    private int score;
    private Player player;
    private FiniteStateMachine AIFSM;
    private FiniteStateMachine UnitFSM;
    
    public AI(Player owner) {
        player = owner;
        brain = new Brain();
        findCity();
    }
    
    public void performAction() {
        moveUnits();
        spawnUnits();
    }
    
    private void spawnUnits() {
        boolean unitCreated = false;
        for(int x = 0; x < 26; x++) {
            for(int y = 0; y < 16; y++) {
                int failure = 0;
                if(Board.tiles[x][y].hasBuilding() && !Board.tiles[x][y].hasUnit() && !Board.tiles[x][y].getSavedEntity().getLabel().equals("N") && Board.tiles[x][y].getEntity().getTeam().getColor().equals(player.getColor()))
                    while(!unitCreated && failure < 100) {
                        Unit unit = getUnit();
                        failure++;
                        if(player.getGold() >= unit.getCost()) {
                            player.setGold(player.getGold() - unit.getCost());
                            unit.setPlayer(player);
                            unit.firstTime();
                            Board.tiles[x][y].setPiece(unit, false);
                            unitCreated = true;
                        }else {
                            System.out.println("AI NOT ENOUGH GOLD");
                        }
                        if(failure >= 100)
                            System.out.println("FAILURE HAS OCCURED");
                    }
            }
        }
    }
    
    private void moveUnits() {
        for(int x = 0; x < 26; x++) {
            for(int y = 0; y < 16; y++) {
                if(Board.tiles[x][y].hasUnit() && !Board.tiles[x][y].isBuilding() && Board.tiles[x][y].getSavedUnit().getTeam().getColor().equals(player.getColor()) 
                        && Board.tiles[x][y].getSavedUnit().getMovements() > 0 && !Board.tiles[x][y].getSavedUnit().isFirstCreated()) {
                    int moveX = new Random().nextInt(3) + 1;
                    int moveY = new Random().nextInt(3) + 1; 
                    moveX-=2;
                    moveY-=2;
                    if(x + moveX >= 0 && x + moveX < 26 && y + moveY >= 0 && y + moveY < 16 && (moveX != 0 || moveY != 0)) {
                        Board.tiles[x][y].getSavedUnit().setMovements(0);
                        Board.tiles[x + moveX][y + moveY].setPiece(Board.tiles[x][y].getSavedUnit(), true);
                    }
                }
            }
        }
    }
    
    private Unit getUnit() {
        Unit unit = Unit.UNITS[new Random().nextInt(Unit.UNITS.length)];
        if(Peasant.class.isInstance(unit))
            unit = new Peasant("P", new Image("file:assets/units/peasant.png"), "Peasant", 0, 0, null);
        if(Swordsman.class.isInstance(unit))
            unit = new Swordsman("S", new Image("file:assets/units/swordsman.png"), "Swordsman", 0, 0, null);
        if(Archer.class.isInstance(unit))
            unit = new Archer("A", new Image("file:assets/units/archer.png"), "Archer", 0, 0, null);
        if(Cleric.class.isInstance(unit))
            unit = new Cleric("C", new Image("file:assets/units/cleric.png"), "Cleric", 0, 0, null);
        if(Necromancer.class.isInstance(unit))
            unit = new Necromancer("N", new Image("file:assets/units/necromancer.png"), "Necromancer", 0, 0, null);
        if(Mage.class.isInstance(unit))
            unit = new Mage("M", new Image("file:assets/units/mage.png"), "Mage", 0, 0, null);
        if(Warrior.class.isInstance(unit))
            unit = new Warrior("W", new Image("file:assets/units/warrior.png"), "Warrior", 0, 0, null);
        if(Spearman.class.isInstance(unit))
            unit = new Spearman("Sp", new Image("file:assets/units/spearman.png"), "Spearman", 0, 0, null);
        if(Cavalry.class.isInstance(unit))
            unit = new Cavalry("Ca", new Image("file:assets/units/cavalry.png"), "Cavalry", 0, 0, null);
        if(SiegeEngine.class.isInstance(unit))
            unit = new SiegeEngine("Si", new Image("file:assets/units/siege.png"), "Siege", 0, 0, null);
        if(Rogue.class.isInstance(unit))
            unit = new Rogue("R", new Image("file:assets/units/assassin.png"), "Assassin", 0, 0, null);
        return unit;
    }
    
    private void findCity() {
        for(int x = 0; x < 26; x++) {
            for(int y = 0; y < 26; y++) {
                if(Board.tiles[x][y].hasBuilding() && !Board.tiles[x][y].getSavedEntity().getLabel().equals("N")
                        && Board.tiles[x][y].getSavedEntity().getTeam().getColor().equals(player.getColor())) {
                    brain.getCities().add((Building)Board.tiles[x][y].getSavedEntity());
                }
            }
        }
    }
}
