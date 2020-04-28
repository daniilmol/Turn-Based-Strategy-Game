package AI;

import entities.Entity;
import entities.Unit;

public class FiniteStateMachine {
    State states[] = new State[10];
    public FiniteStateMachine(Entity decision) {
        if(Unit.class.isInstance(decision)) {
            State captureCities = new State("Capture City", decision, true);
            State targetEnemy = new State("Target Enemy", decision, false);
            State targetUnit = new State("Target Unit", decision, false);
            State targetCity = new State("Target City", decision, false);
            State states[] = {captureCities, targetEnemy, targetUnit, targetCity};
            
            for(int i = 0; i < 4; i++) {
                this.states[i] = states[i];
            }
            
            states[0].setDestination(states[1]);
            states[0].setSource(states[2]);
            states[0].setSource(states[3]);
            
            states[1].setDestination(states[2]);
            states[1].setDestination(states[3]);
            states[1].setSource(states[0]);
            
            states[2].setDestination(states[0]);
            states[2].setSource(states[1]);
            
            states[3].setDestination(states[0]);
            states[3].setSource(states[1]);
            
        }
    }
}
