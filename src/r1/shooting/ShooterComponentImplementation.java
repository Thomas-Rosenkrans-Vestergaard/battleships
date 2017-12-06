package r1.shooting;

import r1.shooting.shooter.Shooter;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import r1.shooting.shooter.SequenceShooter;

public class ShooterComponentImplementation implements ShooterComponent {

    private List<Shooter> shooters = new ArrayList<>();
    private ShooterComponentMemory memory;
    private SequenceShooter sequenceShooter;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {

        this.memory = new ShooterComponentMemory(sizeX, sizeY);
        this.sequenceShooter = new SequenceShooter(memory);
        this.shooters.add(sequenceShooter);
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        return sequenceShooter.getFireCoordinates(enemyShips);
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {

    }

    @Override
    public void startRound(int round) {
        this.sequenceShooter.reset();
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

    }

    @Override
    public void endMatch(int won, int lost, int draw) {

    }
}
