package r1.shooting;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import r1.HeatMap;
import r1.HeatMapOutOfBoundsException;
import r1.placement.PlacerImpl;

public class ShooterImpl implements Shooter {

    private ShooterState shooterState;
    private int lastShot = -1;
    private int sizeX;
    private int sizeY;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        //this.shooterState = new ShooterState(rounds, ships, sizeX, sizeY);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {

        System.out.println("getFireCoordinates " + lastShot + "," + sizeX + "," + sizeY);

        if (lastShot == -1) {
            lastShot = 0;
            return new Position(0, 0);
        }

        lastShot += 2;

        Position target = new Position(lastShot % sizeX + (lastShot / sizeY % 2), lastShot / sizeY);
        return target;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        if (hit) {
            shooterState.addShot(new Position(lastShot % sizeX + (lastShot / sizeY % 2), lastShot / sizeY));
        }
    }

    @Override
    public void startRound(int round) {
        this.lastShot = -1;
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

    }

    @Override
    public void endMatch(int won, int lost, int draw) {

    }
}
