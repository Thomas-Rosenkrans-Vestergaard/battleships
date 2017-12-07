package r1.shooting.shooter;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import r1.shooting.ShooterComponent;
import r1.shooting.ShooterComponentMemory;
import r1.shooting.ShotFeedBack;
import r1.shooting.hunter.Hunter;
import r1.shooting.hunter.HunterReport;

/**
 *
 * @author Thomas
 */
public class SequenceShooter implements Shooter {

    private final ShooterComponent shooterComponent;
    private final ShooterComponentMemory memory;
    private final Queue fireQueue = new ArrayDeque();
    private final int sizeX;
    private final int sizeY;
    private final int numberOfPositions;

    public SequenceShooter(ShooterComponent component, ShooterComponentMemory memory) {
        this.shooterComponent = component;
        this.memory = memory;
        this.sizeX = memory.sizeX;
        this.sizeY = memory.sizeY;
        this.numberOfPositions = sizeX * sizeY;
    }

    @Override
    public void startRound(int round) {
        fireQueue.clear();
        for (int index = 0; index < numberOfPositions; index++) {
            fireQueue.add(new Position(index % sizeX, index / sizeX));
        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

    }

    @Override
    public void hitFeedBack(ShotFeedBack feedBack) {

    }

    @Override
    public void onFire(Position position) {
        fireQueue.remove(position);
    }

    @Override
    public Queue<Position> getFireQueue() {
        return fireQueue;
    }

    public ShooterComponentMemory getMemory() {
        return memory;
    }

    public ShooterComponent getShooterComponent() {
        return shooterComponent;
    }
}
