package r1.shooting.shooter;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import r1.shooting.ShooterComponentMemory;

/**
 *
 * @author Thomas
 */
public class SequenceShooter implements Shooter {

    private final ShooterComponentMemory memory;
    private Position lastPosition;

    public SequenceShooter(ShooterComponentMemory memory) {
        this.memory = memory;

    }

    public void reset() {
        lastPosition = null;
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {

        if (lastPosition == null) {
            lastPosition = new Position(0, 0);
            return lastPosition;
        }

        if (lastPosition.x >= memory.sizeX - 1) {
            lastPosition = new Position(0, lastPosition.y + 1);
            return lastPosition;
        }

        lastPosition = new Position(lastPosition.x + 1, lastPosition.y);
        return lastPosition;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {

    }

    @Override
    public void onFire(Position position) {

    }
}
