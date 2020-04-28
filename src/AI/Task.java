package AI;

import entities.Entity;
import environment.Board;
import environment.Tile;

public class Task {
    private State currentState;
    private Entity entity;
    public Task(State state, Entity entity) {
        this.entity = entity;
        currentState = state;
        if(currentState.isActive()) {
            performTask();
        }
    }
    
    private void performTask() {
        if(currentState.getStateName().equals("Capture City")) {
            captureCity();
        }
    }
    
    private void captureCity() {
        int coordinates[] = findNearestCity();
        int penalty = 0; 
        int speed = entity.getMovements();
    }
    
    private int[] findNearestCity() {
        int coordinates[] = new int[2];
        boolean firstCityFound = true;
        int distance = 0;
        int oldDistance = 0;
        for(int x = 0; x < 26; x++) {
            for(int y = 0; y < 16; y++) {
                if(Board.tiles[x][y].hasBuilding() && Board.tiles[x][y].getSavedEntity().getLabel().equals("N")) {
                    Tile entityTile = Board.tiles[(int) entity.getI().getX()][(int) entity.getI().getY()];
                    distance = Board.getDistance(entityTile, Board.tiles[x][y]);
                    if(firstCityFound) {
                        coordinates[0] = x;
                        coordinates[1] = y;
                        firstCityFound = false;
                        oldDistance = distance;
                        continue;
                    }
                    if(distance < oldDistance) {
                        coordinates[0] = x;
                        coordinates[1] = y;
                        oldDistance = distance;
                    }
                }
            }
        }   
        return coordinates;
    }
}
