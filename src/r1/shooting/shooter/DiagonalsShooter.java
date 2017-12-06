package r1.shooting.shooter;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import r1.shooting.ShooterComponentMemory;

/**
 *
 * @author Thomas
 */
/*public class DiagonalsShooter implements Shooter {
    public enum Mode {
        SEEK,
        HUNT,
    }

    private List<Shooter> tactics = new ArrayList<>();
    private ShooterComponentMemory memory;

    private Set<Integer> firedShots;
    private Stack<Position> positions;

    private Position seekShot;
    private Position huntShot;
    private Mode currentMode = Mode.SEEK;
    private int totalHits = 0;
    private int possibleHits;

    private Fleet previousEnemyFleet;
    private Fleet originalFleet;
    private boolean hitLastHunt;
    private boolean sunkLastHunt;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {

        this.memory = new ShooterComponentMemory(sizeX, sizeY);
        this.originalFleet = ships;
        for (int x = 0; x < ships.getNumberOfShips(); x++) {
            possibleHits += ships.getShip(x).size();
        }

        System.out.println("size" + possibleHits);
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        
        if (currentMode == Mode.SEEK) {
            seekShot = positions.pop();
            firedShots.add(toIndex(seekShot));
            return seekShot;
        }

        if (currentMode == Mode.HUNT) {

            if (sunkLastHunt) {
                currentMode = Mode.SEEK;
                return getFireCoordinates(enemyShips);
            }

            if (hitLastHunt) {

            }

            Position top = new Position(seekShot.x, seekShot.y + 1);
            if (isValid(top) && !firedShots.contains(toIndex(top))) {
                firedShots.add(toIndex(top));
                return top;
            }

            Position left = new Position(seekShot.x - 1, seekShot.y);
            if (isValid(left) && !firedShots.contains(toIndex(left))) {
                firedShots.add(toIndex(left));
                return left;
            }

            Position right = new Position(seekShot.x + 1, seekShot.y);
            if (isValid(right) && !firedShots.contains(toIndex(right))) {
                firedShots.add(toIndex(right));

                return right;
            }

            Position bottom = new Position(seekShot.x, seekShot.y - 1);
            if (isValid(bottom) && !firedShots.contains(toIndex(bottom))) {
                firedShots.add(toIndex(bottom));
                return bottom;
            }
        }

        throw new UnsupportedOperationException();

        return new Position(0, 0);
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {

        if (currentMode == Mode.SEEK && hit) {
            currentMode = Mode.HUNT;
        }

        if (currentMode == Mode.HUNT) {
            hitLastHunt = hit;
        }
    }

    @Override
    public void startRound(int round) {
        this.positions = new Stack<>();
        this.firedShots = new HashSet<>();
        this.previousEnemyFleet = originalFleet;
        this.currentMode = Mode.SEEK;
        this.totalHits = 0;
        for (int j = 0; j < 10; j++) {
            for (int i = 19; i >= 0; i -= 4) {
                if ((i - j) >= 0 && (i - j) < 10) {
                    Position position = new Position(i - j, j);
                    positions.add(position);
                }
            }
        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

    }

    @Override
    public void endMatch(int won, int lost, int draw) {

    }

    private int toIndex(Position position) {
        return position.y * memory.sizeX + position.x;
    }

    private boolean isValid(Position position) {
        return position.x >= 0 && position.x < memory.sizeX && position.y >= 0 && position.y < memory.sizeY;
    }
}*/