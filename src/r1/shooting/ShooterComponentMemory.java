package r1.shooting;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import r1.heatmap.HeatMap;

public class ShooterComponentMemory {

    public final int sizeX;
    public final int sizeY;
    private final int numberOfRounds;
    private final Fleet initialEnemyFleet;
    private final int numberOfHeatMaps;
    private final int numberOfShips;

    private HeatMap hitHeatMap;
    private Map<Integer, HeatMap> shipHeatMaps = new HashMap<>();
    private Set<Position> usedPositions = new HashSet<>();
    private Position lastFiredPosition;
    private int currentRound = 0;
    private int remainingShots;
    private int lastShotNumber;
    private int currentHeatMapVersion = 0;

    public void reset() {
        this.remainingShots = sizeY * sizeX;
        this.lastShotNumber = 0;
        this.hitHeatMap = new HeatMap(sizeX, sizeY);
        this.usedPositions = new HashSet<Position>();

        for (int x = 0; x < this.numberOfShips; x++) {
            int size = initialEnemyFleet.getShip(x).size();
            if (!shipHeatMaps.containsKey(size)) {
                shipHeatMaps.put(size, new HeatMap(sizeX, sizeY));
            }
        }
    }

    public ShooterComponentMemory(int numberOfRounds, Fleet fleet, int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numberOfRounds = numberOfRounds;
        this.initialEnemyFleet = fleet;
        this.numberOfHeatMaps = (int) (5 * Math.pow(2, Math.log10((double) numberOfRounds) - 1));;
        this.numberOfShips = fleet.getNumberOfShips();
        reset();

    }

    public Fleet getInitialFleet() {
        return initialEnemyFleet;
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

    public Position getLastFiredPosition() {
        return lastFiredPosition;
    }

    public void onFire(Position position) {
        usedPositions.add(position);
        this.lastFiredPosition = position;
        this.lastShotNumber++;
        this.remainingShots--;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
