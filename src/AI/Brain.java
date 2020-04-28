package AI;

import java.util.ArrayList;

import entities.Building;
import entities.Unit;

public class Brain {

    private ArrayList<Unit> units = new ArrayList<Unit>();
    private ArrayList<Building> cities = new ArrayList<Building>();
    
    public Brain() {
        
    }
    
    public ArrayList<Unit> getUnits(){
        return units;
    }
    
    public ArrayList<Building> getCities(){
        return cities;
    }
    
}
