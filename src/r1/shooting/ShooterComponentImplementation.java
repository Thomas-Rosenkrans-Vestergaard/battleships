package r1.shooting;

import r1.shooting.shooter.Shooter;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import r1.heatmap.HeatMap;
import r1.shooting.shooter.DiagonalsShooter;
import r1.shooting.shooter.SequenceShooter;

public class ShooterComponentImplementation implements ShooterComponent {

    private ShooterComponentMemory memory;
    private SequenceShooter sequenceShooter;
    private DiagonalsShooter diagonalsShooter;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        this.memory = new ShooterComponentMemory(rounds, ships, sizeX, sizeY);
        this.sequenceShooter = new SequenceShooter(memory);
        this.diagonalsShooter = new DiagonalsShooter(rounds, ships, sizeX, sizeY);
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        Position position = diagonalsShooter.getFireCoordinates(enemyShips);
        this.memory.onFire(position);
        this.sequenceShooter.onFire(position);
        this.diagonalsShooter.onFire(position);
        return position;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        this.diagonalsShooter.hitFeedBack(hit, enemyShips);
    }

    @Override
    public void startRound(int round) {
        //this.sequenceShooter.reset();
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

    }

    @Override
    public void endMatch(int won, int lost, int draw) {

    }
}
