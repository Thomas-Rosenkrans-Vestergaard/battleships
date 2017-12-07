package r1.shooting;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;
import r1.shooting.shooter.DiagonalsShooter;
import r1.shooting.shooter.SequenceShooter;
import r1.shooting.shooter.Shooter;

public class ShooterComponentImplementation implements ShooterComponent {

    private final Stack<Shooter> shooters = new Stack<>();
    private final ShooterComponentMemory memory;
    private Fleet previousEnemyFleet;
    private int previousNumberOfShips;
    private Shooter previouslyUsedShooter;

    public ShooterComponentImplementation(ShooterComponentMemory memory) {

        System.out.println("CONSTRUCOTR");
        this.memory = memory;
        this.shooters.add(new DiagonalsShooter(this, memory));
        //this.shooters.add(new SequenceShooter(this, memory));
    }

    @Override
    public void pushShooter(Shooter shooter) {
        this.shooters.push(shooter);
    }

    @Override
    public void removeShooter(Shooter shooter) {
        this.shooters.remove(shooter);
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        System.out.println("getFireCoordinates");
        System.out.println("currentEnemyShips=" + previousEnemyFleet.getNumberOfShips());
        System.out.println(this);
        Shooter shooter = shooters.peek();
        System.out.println("numberOfShooters=" + shooters.size());
        System.out.println("using=" + shooter.getClass().getName());

        if (shooter == null) {
            throw new IllegalStateException("No more shooters.");
        }

        Queue<Position> fireQueue = shooter.getFireQueue();
        Position position = fireQueue.poll();
        this.memory.setLastFiredPosition(position);
        this.previouslyUsedShooter = shooter;
        return position;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        System.out.println("hitFeedBack");
        System.out.println("preEns=" + previousEnemyFleet.getNumberOfShips());
        System.out.println("enemyShips=" + enemyShips.getNumberOfShips());
        Position position = memory.getLastFiredPosition();
        ShotFeedback feedback = new ShotFeedback(previouslyUsedShooter, position, hit, previousNumberOfShips, enemyShips.getNumberOfShips());
        Enumeration<Shooter> shootersEnumeration = shooters.elements();
        while (shootersEnumeration.hasMoreElements()) {
            Shooter shooter = shootersEnumeration.nextElement();
            if (shooter == previouslyUsedShooter) {
                shooter.onFeedBack(feedback);
            } else {
                shooter.onSecondaryFeedBack(feedback);
            }
        }

        this.previousEnemyFleet = enemyShips;
        this.previousNumberOfShips = enemyShips.getNumberOfShips();
    }

    @Override
    public void startRound(int round) {
        this.previousEnemyFleet = memory.getInitialFleet();
        this.previousNumberOfShips = this.previousEnemyFleet.getNumberOfShips();
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
