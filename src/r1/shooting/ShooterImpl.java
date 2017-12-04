package r1.shooting;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import r1.HeatMap;
import r1.HeatMapOutOfBoundsException;

public class ShooterImpl implements Shooter {

    private ShooterState shooterState;
    private Position lastShot;
    private int sizeX;
    private int sizeY;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        this.shooterState = new ShooterState(rounds, ships, sizeX, sizeY);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {

        if (lastShot == null) {
            lastShot = new Position(0, 0);
            return lastShot;
        }

        if (lastShot.x + 2 >= sizeX) {
            return new Position(lastShot.x + 2, lastShot.y + 1);
        }

        return new Position(lastShot.x + 2, lastShot.y);
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        if (hit) {
            shooterState.addShot(lastShot);
        }
    }

    @Override
    public void startRound(int round) {

    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

    }

    @Override
    public void endMatch(int won, int lost, int draw) {

    }
}
