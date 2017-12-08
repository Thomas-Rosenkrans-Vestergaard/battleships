package r1.shooting.shooter;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.ArrayDeque;
import java.util.Queue;
import r1.shooting.ShooterComponent;
import r1.shooting.ShooterComponentMemory;
import r1.shooting.ShotFeedback;

public class DiagonalsShooter implements Shooter {

    private final ShooterComponent shooterComponent;
    private final ShooterComponentMemory memory;
    private Queue<Position> fireQueue;

    public DiagonalsShooter(ShooterComponent component, ShooterComponentMemory memory) {
        this.shooterComponent = component;
        this.memory = memory;
    }

    @Override
    public Queue<Position> getFireQueue() {
        return fireQueue;
    }

    @Override
    public void startRound(int round) {

        fireQueue = new ArrayDeque<>();

        for (int i = 4; i > 0; i -= 2) {
            for (int x = 0; x < memory.sizeX; x++) {
                for (int y = 0; y < memory.sizeY; y++) {
                    if ((x - y) % i == 0) {
                        Position position = new Position(x, y);
                        if (!fireQueue.contains(position)) {
                            fireQueue.add(position);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

    }

    @Override
    public void onFeedBack(ShotFeedback feedback) {
        System.out.println("DiagonalsShooter:onFeedBack=");
        System.out.println("currentFeedBack=" + feedback);
        if (feedback.wasHit() && !feedback.sunkShip()) {
            Shooter hunter = new Hunter(this.shooterComponent, memory, feedback.getPosition());
            shooterComponent.pushShooter(hunter);
        }
    }

    @Override
    public void onSecondaryFeedBack(ShotFeedback feedback) {

    }

    public ShooterComponentMemory getMemory() {
        return memory;
    }

    public ShooterComponent getShooterComponent() {
        return shooterComponent;
    }
}
