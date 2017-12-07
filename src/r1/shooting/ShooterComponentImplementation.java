package r1.shooting;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import r1.shooting.shooter.DiagonalsShooter;
import r1.shooting.shooter.SequenceShooter;
import r1.shooting.shooter.Shooter;

public class ShooterComponentImplementation implements ShooterComponent {
    
    private List<Shooter> shooters = new ArrayList<>();
    private ShooterComponentMemory memory;
    private SequenceShooter sequenceShooter;
    private DiagonalsShooter diagonalsShooter;
    private Shooter activeShooter;
    private Queue<Position> fireQueue;
    private Position position;
    
    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        
        this.memory = new ShooterComponentMemory(rounds, ships, sizeX, sizeY);
        
        this.sequenceShooter = new SequenceShooter(memory);
        this.shooters.add(this.sequenceShooter);
        
        this.diagonalsShooter = new DiagonalsShooter(memory);
        this.shooters.add(this.diagonalsShooter);
        
        this.activeShooter = this.diagonalsShooter;
    }
    
    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        Queue<Position> fireQueue = this.activeShooter.getFireQueue();
        position = fireQueue.poll();
        return position;
    }
    
    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        this.activeShooter.hitFeedBack(hit, enemyShips);
        for (Shooter shooter : shooters) {
            if (shooter != this.activeShooter) {
                shooter.onFire(position);
            }
        }
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
