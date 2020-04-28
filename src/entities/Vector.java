package entities;

import environment.Board;
import environment.Tile;

/**
 * This class represents the entire movement system
 * @author Daniil Molchanov
 */
public class Vector {
    
    /**
     * This is the unit being moved
     */
    private Unit unit;
    
    /**
     * Vector constructor initializes the unit selected unit and shows all tiles it can move onto
     * @param unitTile is the tile the unit is on
     * @param unit is the selected unit
     */
    public Vector(Tile unitTile, Unit unit) {
        this.unit = unit;
        showAvailableTiles((int)unitTile.getX(), (int)unitTile.getY());
    }
    
    /**
     * This method uses pathfinding algorithms to show the available tiles a unit can move to
     * @param x is the unit's x coordinate
     * @param y is the unit's y coordinate
     */
    private void showAvailableTiles(int x, int y) {
        int penalty = 0;
        int speed = unit.getMovements();
        check(penalty, speed, x, y);
        Board.resetCost();
    }
    
    /**
     * This method highlights the tile a unit can move on to under specific conditions
     * @param deltaX is the highlighted tile's x coordinate
     * @param deltaY is the highlighted tile's y coordinate
     * @return false if an enemy unit is in the way, true if it isn't
     */
    private boolean showTiles(int deltaX, int deltaY) {
        if((!Board.tiles[deltaX][deltaY].hasUnit() || Board.tiles[deltaX][deltaY].hasBuilding())) {
            Board.tiles[deltaX][deltaY].unlock();
            Board.tiles[deltaX][deltaY].highlight();
        }if((Board.tiles[deltaX][deltaY].hasUnit() && !Board.tiles[deltaX][deltaY].getEntity().getTeam().getColor().equals(unit.getTeam().getColor()))) {
            Board.tiles[deltaX][deltaY].unlock();
            Board.tiles[deltaX][deltaY].highlightDependingOnUnitColor();
            return false;
        }else {
            return true;
        }
    }
    
    /**
     * This method utilizes the dijkstra algorithm to find the shortest path from the tile being tested, to the selected unit to
     * see if the unit can get to the tile using the shortest path
     * @param penalty is the tile environment being investigated
     * @param speed is the unit's speed
     * @param x is tile's x coordinate
     * @param y is tile's y coordinate
     */
    private void check(int penalty, int speed, int x, int y) {  
        Board.tiles[x][y].setCost(0);
        int f = 4;
        for(int factor = 1; factor < speed; f+=2, factor++);
        
        for(int i = 0; i < speed * f; i++) {
            int numbers[] = findSmallestCost();
            x = numbers[1];
            y = numbers[2];
            if(x - 1 >= 0 && !Board.tiles[x - 1][y].visited()) {
                if(!Board.tiles[x - 1][y].hasUnit() || (Board.tiles[x - 1][y].hasUnit() && !Board.tiles[x - 1][y].getEntity().getTeam().getColor().equals(unit.getTeam().getColor())))
                    dijkstra(penalty, speed, x - 1, y, x, y);
            }if(x + 1 < 26 && !Board.tiles[x + 1][y].visited()) {
                if(!Board.tiles[x + 1][y].hasUnit() || (Board.tiles[x + 1][y].hasUnit() && !Board.tiles[x + 1][y].getEntity().getTeam().getColor().equals(unit.getTeam().getColor())))
                dijkstra(penalty, speed, x + 1, y, x, y);
            }if(y - 1 >= 0 && !Board.tiles[x][y - 1].visited()) {
                if(!Board.tiles[x][y - 1].hasUnit() || (Board.tiles[x][y - 1].hasUnit() && !Board.tiles[x][y - 1].getEntity().getTeam().getColor().equals(unit.getTeam().getColor())))
                dijkstra(penalty, speed, x, y - 1, x, y);
            }if(y + 1 < 16 && !Board.tiles[x][y + 1].visited()) {
                if(!Board.tiles[x][y + 1].hasUnit() || (Board.tiles[x][y + 1].hasUnit() && !Board.tiles[x][y + 1].getEntity().getTeam().getColor().equals(unit.getTeam().getColor())))
                dijkstra(penalty, speed, x, y + 1, x, y);
            }
            Board.tiles[x][y].visit();
        }
    }
    
    /**
     * This method performs the dijkstra algorithm onto a tile to determine the shortest path to get to it
     * @param penalty is the tile environment's movement penalty
     * @param speed is the unit's speed
     * @param x is the investigated tile's x coordinate
     * @param y is the investigated tile's y coordinate
     * @param curX is the current tile's x coordinate
     * @param curY is the current tile's y coordinate
     */
    private void dijkstra(int penalty, int speed, int x, int y, int curX, int curY) {
        if(Board.tiles[x][y].getEnvironment().getPenalty() > 0) {
            penalty = Board.tiles[x][y].getEnvironment().getPenalty() - unit.getPenaltyReduction(Board.tiles[x][y].getEnvironment());
        }
        if(Board.tiles[x][y].getCost() > (Board.tiles[curX][curY].getCost() + penalty + 1) || Board.tiles[x][y].getCost() == -1) {
            Board.tiles[x][y].setCost(penalty + 1 + Board.tiles[curX][curY].getCost());
            Board.tiles[x][y].setPath((Board.tiles[curX][curY]));
        }
        if(Board.tiles[x][y].getCost() <= speed) {
            showTiles(x, y);
        }
    }
    
    /**
     * This method finds the smallest cost a tile has to determine where to start from with the dijkstra algorithm
     * @return an array of numbers containing the tile coordinates and cost.
     */
    private int[] findSmallestCost() {
        int numbers[] = new int[3];
        int smallestCost = -1;
        int xCoord = -1;
        int yCoord = -1;
        for(int tileX = 0; tileX < 26; tileX++) {
            for(int tileY = 0; tileY < 16; tileY++) {
                if(Board.tiles[tileX][tileY].getCost() != -1 && !Board.tiles[tileX][tileY].visited()) {
                    if(smallestCost == -1) {
                        smallestCost = Board.tiles[tileX][tileY].getCost();
                        xCoord = tileX;
                        yCoord = tileY;
                    }
                    if(Board.tiles[tileX][tileY].getCost() < smallestCost) {
                        smallestCost = Board.tiles[tileX][tileY].getCost();
                        xCoord = tileX;
                        yCoord = tileY;
                    }
                }
            }
        }
        numbers[0] = smallestCost;
        numbers[1] = xCoord;
        numbers[2] = yCoord;
        return numbers;
    }

    /**
     * This method decreases the unit's movements left after moving a certain number of tiles
     * @param x is the unit's old x coordinate
     * @param y is the unit's old y coordinate
     * @param source is the tile the unit is moved to
     */
    public void decreaseMovements(int x, int y, Tile source) {
        Tile src = source;
        while(src.getX() != x || src.getY() != y) {
            source.getEntity().setMovements((int)(source.getEntity().getMovements() - ((Board.tiles[(int) src.getX()][(int) src.getY()].getEnvironment().getPenalty() + 1)
                    - (unit.getPenaltyReduction(Board.tiles[(int) src.getX()][(int) src.getY()].getEnvironment())))));
            src = src.getPath();
        }        
    }
}
