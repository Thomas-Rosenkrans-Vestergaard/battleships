/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1.shooting;

import battleship.interfaces.Fleet;
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
    private final int previousEnemyFleet;
    private final int currentEnemyFleet;

    public ShotFeedback(Shooter shooter, battleship.interfaces.Position position, boolean hit, int previousEnemyFleet, int currentEnemyFleet) {
        this.shooter = shooter;
        this.position = new Position(position);
        this.hit = hit;
        this.previousEnemyFleet = previousEnemyFleet;
        this.currentEnemyFleet = currentEnemyFleet;
    }

    public boolean sunkShip() {
        return previousEnemyFleet > currentEnemyFleet;
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

    public int getPreviousEnemyFleet() {
        return previousEnemyFleet;
    }

    public int getCurrentEnemyFleet() {
        return currentEnemyFleet;
    }

    @Override
    public String toString() {
        return "ShotFeedback{" + "position=" + position + ", hit=" + hit + ", previousEnemyFleet=" + previousEnemyFleet + ", currentEnemyFleet=" + currentEnemyFleet + '}';
    }

}
