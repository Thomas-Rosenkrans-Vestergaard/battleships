package r1.shooting;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import r1.heatmap.HeatMap;

public class ShooterComponentMemory {

    /**
     * The size of the x-axis.
     */
    public final int sizeX;
    
    /**
     * The size of the y-axis.
     */
    public final int sizeY;
    
    /**
     * The number of rounds to be played.
     */
    private final int numberOfRounds;
    
    private final int numberOfHeatMaps;
    private final int numberOfShips;
    private final HeatMap hitHeatMap;
    private final Map<Integer, HeatMap> shipHeatMaps = new HashMap<>();
    private final Set<Position> usedPositions = new HashSet<>();
    private Position lastFiredPosition;
    private int currentRound;
    private int remainingShots;
    private int currentHeatMapVersion = 0;

    public ShooterComponentMemory(int numberOfRounds, Fleet fleet, int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numberOfShips = fleet.getNumberOfShips();
        this.numberOfRounds = numberOfRounds;
        this.numberOfHeatMaps = (int) (5 * Math.pow(2, Math.log10((double) numberOfRounds) - 1));;
        this.hitHeatMap = new HeatMap(sizeX, sizeY);

        for (int x = 0; x < this.numberOfShips; x++) {
            int size = fleet.getShip(x).size();
            if (!shipHeatMaps.containsKey(size)) {
                shipHeatMaps.put(size, new HeatMap(sizeX, sizeY));
            }
        }

    }

    public int getNumberOfShips() {
        return numberOfShips;
    }

    public HeatMap getHitHeatMap() {
        return hitHeatMap;
    }

    public HeatMap getShipHeatMap(int shipSize) {
        return shipHeatMaps.get(shipSize);
    }

    public boolean hasBeenFiredAt(Position position) {
        return usedPositions.contains(position);
    }

    public Position getLastFiredPosition(){
        return lastFiredPosition;
    }

    public void setLastFiredPosition(Position lastFiredPosition) {
        this.lastFiredPosition = lastFiredPosition;
    }
    
    public void onFire(Position position) {
        usedPositions.add(position);
        this.lastFiredPosition = position;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
