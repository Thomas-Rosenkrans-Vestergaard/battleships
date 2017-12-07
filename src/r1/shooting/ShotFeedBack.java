/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1.shooting;

import battleship.interfaces.Fleet;
import r1.Position;

/**
 *
 * @author Skole
 */
public class ShotFeedBack {

    private Position position;
    private boolean hit;
    private Fleet previousEnemyFleet;
    private Fleet currentEnemyFleet;

    public ShotFeedBack(battleship.interfaces.Position position, boolean hit, Fleet previousEnemyFleet, Fleet currentEnemyFleet) {
        this.position = new Position(position);
        this.hit = hit;
        this.previousEnemyFleet = previousEnemyFleet;
        this.currentEnemyFleet = currentEnemyFleet;
    }

    public boolean sunkShip() {
        return previousEnemyFleet.getNumberOfShips() > currentEnemyFleet.getNumberOfShips();
    }

    public Position getPosition() {
        return position;
    }

    public boolean wasHit() {
        return hit;
    }

    public Fleet getPreviousFleet() {
        return previousEnemyFleet;
    }

    public Fleet getCurrentFleet() {
        return currentEnemyFleet;
    }

}
