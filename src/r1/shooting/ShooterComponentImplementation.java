package r1.shooting;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import r1.shooting.hunter.Hunter;
import r1.shooting.hunter.HunterReport;
import r1.shooting.shooter.DiagonalsShooter;
import r1.shooting.shooter.SequenceShooter;
import r1.shooting.shooter.Shooter;

public class ShooterComponentImplementation implements ShooterComponent {

    private List<Shooter> shooters = new ArrayList<>();
    private ShooterComponentMemory memory;
    private SequenceShooter sequenceShooter;
    private DiagonalsShooter diagonalsShooter;
    private Shooter activeShooter;
    private Fleet previousEnemyFleet;
    private Queue<Hunter> hunterQueue = new ArrayDeque();
    private Hunter currentHunter;
    
    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {

        this.memory = new ShooterComponentMemory(rounds, ships, sizeX, sizeY);
        this.previousEnemyFleet = ships;

        this.sequenceShooter = new SequenceShooter(this, memory);
        this.shooters.add(this.sequenceShooter);

        this.diagonalsShooter = new DiagonalsShooter(this, memory);
        this.shooters.add(this.diagonalsShooter);

        this.activeShooter = this.diagonalsShooter;
    }

    @Override
    public void onHunterActivated(Hunter hunter) {
        this.hunterQueue.add(hunter);
    }

    @Override
    public void onHunterFinished(Hunter hunter, HunterReport report) {
        this.hunterQueue.remove(hunter);
        this.currentHunter = null;
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        if (this.hunterQueue.isEmpty()) {
            Queue<Position> fireQueue = this.activeShooter.getFireQueue();
            Position position = fireQueue.poll();
            memory.setLastFiredPosition(position);
            return position;
        } else {
            Hunter hunter = hunterQueue.peek();
            this.currentHunter = hunter;
            if (hunter == null) {
                throw new IllegalStateException("Null hunter.");
            }

            Queue<Position> fireQueue = hunter.getFireQueue();
            Position position = fireQueue.poll();
            memory.setLastFiredPosition(position);
            return position;
        }
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        Position lastFiredPosition = memory.getLastFiredPosition();
        ShotFeedBack feedBack = new ShotFeedBack(lastFiredPosition, hit, this.previousEnemyFleet, enemyShips);
        this.activeShooter.hitFeedBack(feedBack);
        for (Shooter shooter : shooters) {
            if (shooter != this.activeShooter) {
                shooter.onFire(lastFiredPosition);
            }
        }

        if (!hunterQueue.isEmpty() && this.currentHunter != null) {
            hunterQueue.peek().hitFeedBack(feedBack);
        }

        this.previousEnemyFleet = enemyShips;
    }

    @Override
    public void startRound(int round) {
        for (Shooter shooter : shooters) {
            shooter.startRound(round);
        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        for (Shooter shooter : shooters) {
            shooter.endRound(round, points, enemyPoints);
        }
    }

    @Override
    public void endMatch(int won, int lost, int draw) {

    }
}
