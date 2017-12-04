package r1.shooting;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import r1.HeatMap;

public class ShooterState {

    private final Set<Position> shots;
    private final Map<Integer, HeatMap> shipLocations;
    private final HeatMap hits;
    private Position lastShot;

    public ShooterState(Set<Position> shots, Map<Integer, HeatMap> shipLocations, HeatMap hits) {
        this.shots = shots;
        this.shipLocations = shipLocations;
        this.hits = hits;
    }

    public void addShot(Position position) {
        this.shots.add(position);
    }

    public boolean hasShot(Position position) {
        return shots.contains(position);
    }

    public HeatMap getShipHeatMap(int size) {
        return shipLocations.get(size);
    }

    public HeatMap getHits() {
        return hits;
    }

    public Position getLastShot() {
        return lastShot;
    }

    public void setLastShot(Position lastShot) {
        this.lastShot = lastShot;
    }
    
    
}
