package r1.shooting.shooter;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import r1.shooting.ShooterComponentMemory;

/**
 *
 * @author Thomas
 */
public class DiagonalsShooter implements Shooter {

    public enum Mode {
        SEEK,
        HUNT,
    }

    private List<Shooter> tactics = new ArrayList<>();
    private ShooterComponentMemory memory;

    private Position seekShot;
    private Position huntShot;
    private Mode currentMode = Mode.SEEK;
    private int totalHits = 0;
    private int shotIncrement = 4;

    private boolean hitLastHunt;
    private boolean sunkLastHunt;

    private Queue<Position> fireQueue;

    public DiagonalsShooter(ShooterComponentMemory memory) {
        this.memory = memory;
    }

    @Override
    public Queue<Position> getFireQueue() {
        return fireQueue;
    }

    @Override
    public void startRound(int round) {

        fireQueue = new ArrayDeque<Position>();

        for (int i = 4; i > 0; i--) {
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
    public void hitFeedBack(boolean hit, Fleet enemyShips) {

        if (currentMode == Mode.SEEK && hit) {
            currentMode = Mode.HUNT;
        }

        if (currentMode == Mode.HUNT) {
            hitLastHunt = hit;
        }
    }

    @Override
    public void onFire(Position position) {

    }

    private boolean isValid(Position position) {
        return position.x >= 0 && position.x < memory.sizeX && position.y >= 0 && position.y < memory.sizeY;
    }
}
