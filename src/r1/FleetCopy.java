package r1;

import battleship.interfaces.Fleet;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Thomas
 */
public class FleetCopy implements Iterable {

    private int numberOfShips;
    private int shortestShip;
    private int longestShip;
    private List<Ship> ships;
    private Map<Integer, Integer> frequency = new HashMap<>();

    public FleetCopy(Fleet fleet) {
        this.numberOfShips = fleet.getNumberOfShips();
        this.shortestShip = Integer.MAX_VALUE;
        this.longestShip = Integer.MIN_VALUE;
        this.ships = new ArrayList<>();
        for (Ship ship : fleet) {
            this.ships.add(ship);
            int length = ship.size();
            if (length < shortestShip) {
                this.shortestShip = length;
            }
            if (length > this.longestShip) {
                this.longestShip = length;
            }
            this.frequency.put(length, this.frequency.getOrDefault(length, 0) + 1);
        }
    }

    public int getFrequency(int size) {
        return frequency.getOrDefault(size, 0);
    }

    public int getFrequency(Ship ship) {
        return getFrequency(ship.size());
    }

    public int getShortestShip() throws EmptyFleetException {
        if (this.shortestShip == Integer.MAX_VALUE) {
            throw new EmptyFleetException();
        }
        return shortestShip;
    }

    public int getLongestShip() throws EmptyFleetException {
        if (this.longestShip == Integer.MIN_VALUE) {
            throw new EmptyFleetException();
        }
        return longestShip;
    }

    public int getNumberOfShips() {
        return numberOfShips;
    }

    @Override
    public Iterator<Ship> iterator() {
        return ships.iterator();
    }
}
