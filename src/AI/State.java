package AI;

import java.util.ArrayList;

import entities.Entity;

public class State {

    private String stateName;
    private ArrayList<State> destStates = new ArrayList<State>();
    private ArrayList<State> sourceStates = new ArrayList<State>();
    private Task task;
    private boolean active;
    
    public State(String stateName, Entity decision, boolean stateActivity) {
        this.stateName = stateName;
        active = stateActivity;
        task = new Task(this, decision);
    }
    
    public String getStateName() {
        return stateName;
    }
    
    public void setDestination(State state) {
        destStates.add(state);
    }
    
    public void setSource(State state) {
        sourceStates.add(state);
    }
    
    public boolean isActive() {
        return active;
    }
}
