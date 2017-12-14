package r1.shooting;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import r1.DensityCalculator;
import r1.FleetCopy;
import r1.heatmap.HeatMap;
import r1.heatmap.HeatMapView;

public class ShooterComponentMemory {

    public final int sizeX;
    public final int sizeY;
    private final int numberOfRounds;
    private final FleetCopy initialEnemyFleet;
    private final int numberOfHeatMaps;
    private final int numberOfShips;

    private FleetCopy currentEnemyFleet;
    private HeatMap hitHeatMap;
    private Map<Integer, HeatMap> shipHeatMaps = new HashMap<>();
    private Set<Position> usedPositions = new HashSet<>();
    private Position lastFiredPosition;
    private int currentRound = 0;
    private int remainingShots;
    private int lastShotNumber;
    private int currentHeatMapVersion = 0;
    private DensityCalculator densityCalculator;

    public void reset() {
        this.remainingShots = sizeY * sizeX;
        this.lastShotNumber = 0;
        this.hitHeatMap = new HeatMap(sizeX, sizeY);
        this.usedPositions = new HashSet<Position>();
        this.densityCalculator = new DensityCalculator();
        for (int x = 0; x < this.initialEnemyFleet.getNumberOfShips(); x++) {
            this.densityCalculator.addShip(this.initialEnemyFleet.getShip(x).size());
        }

        for (int x = 0;
                x
                < this.numberOfShips;
                x++) {
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
        this.initialEnemyFleet = new FleetCopy(fleet);
        this.currentEnemyFleet = new FleetCopy(fleet);
        this.numberOfHeatMaps = (int) (5 * Math.pow(2, Math.log10((double) numberOfRounds) - 1));;
        this.numberOfShips = fleet.getNumberOfShips();

        reset();
    }

    public HeatMapView getDensityView() {
        try {
            HeatMap hm = new HeatMap(sizeX, sizeY);
            hm.makeVersion(true);
            this.densityCalculator.calculate(hm);
            return hm.getActiveVersion();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public FleetCopy getInitialFleet() {
        return initialEnemyFleet;
    }

    public int getNumberOfShips() {
        return numberOfShips;
    }

    public FleetCopy getCurrentEnemyFleet() {
        return currentEnemyFleet;
    }

    public void setCurrentEnemyFleet(FleetCopy currentEnemyFleet) {
        this.currentEnemyFleet = currentEnemyFleet;
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

    public Set<Position> getUsedPositions() {
        return usedPositions;
    }

    public Position getLastFiredPosition() {
        return lastFiredPosition;
    }

    public void onFire(Position position) {
        try {
            usedPositions.add(position);
            System.out.println("FIRED AT " + position);
            this.lastFiredPosition = position;
            this.lastShotNumber++;
            this.remainingShots--;
            this.densityCalculator.addBlocker(position);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
