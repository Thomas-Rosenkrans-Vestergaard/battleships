/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1.shooting;

import battleship.interfaces.Fleet;
import java.util.ArrayList;
import java.util.List;
import r1.EmptyFleetException;
import r1.FleetCopy;
import r1.Position;
import r1.shooting.shooter.Shooter;

/**
 *
 * @author Skole
 */
public class ShotFeedback {

    private final Shooter shooter;
    private final Position position;
    private final boolean hit;
    private final FleetCopy previousEnemyFleet;
    private final FleetCopy currentEnemyFleet;

    public ShotFeedback(Shooter shooter, battleship.interfaces.Position position, boolean hit, FleetCopy previousEnemyFleet, FleetCopy currentEnemyFleet) {
        this.shooter = shooter;
        this.position = new Position(position);
        this.hit = hit;
        this.previousEnemyFleet = previousEnemyFleet;
        this.currentEnemyFleet = currentEnemyFleet;
    }

    public boolean sunkShip() {
        return previousEnemyFleet.getNumberOfShips() > currentEnemyFleet.getNumberOfShips();
    }

    public int getSunkShip() {
        if (!sunkShip()) {
            return -1;
        }

        try {

            int longestShip = previousEnemyFleet.getLongestShip();
            for (int x = 1; x <= longestShip; x++) {
                if (previousEnemyFleet.getFrequency(x) > currentEnemyFleet.getFrequency(x)) {
                    return x;
                }
            }

            return -1;
        } catch (EmptyFleetException e) {
            throw new IllegalStateException(e);
        }
    }

    public Position getPosition() {
        return position;
    }

    public Shooter getShooter() {
        return shooter;
    }

    public boolean wasHit() {
        return hit;
    }

    public FleetCopy getPreviousEnemyFleet() {
        return previousEnemyFleet;
    }

    public FleetCopy getCurrentEnemyFleet() {
        return currentEnemyFleet;
    }

    @Override
    public String toString() {
        return "ShotFeedback{" + "position=" + position + ", hit=" + hit + ", previousEnemyFleet=" + previousEnemyFleet + ", currentEnemyFleet=" + currentEnemyFleet + '}';
    }

}
