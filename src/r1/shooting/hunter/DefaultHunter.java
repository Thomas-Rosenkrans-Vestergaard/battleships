package r1.shooting.hunter;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import r1.Position.Direction;
import r1.PositionedArea;
import r1.shooting.ShooterComponent;
import r1.shooting.ShooterComponentMemory;
import r1.shooting.ShotFeedBack;
import r1.shooting.shooter.Shooter;

public class DefaultHunter implements Hunter {

    public enum Stage {
        FIND_DIRECTION,
        FOLLOW,
    }

    private Shooter shooter;

    private r1.Position initialPosition;
    private ShooterComponent shooterComponent;
    private ShooterComponentMemory memory;
    private Queue<Position> fireQueue = new ArrayDeque<>();
    private PositionedArea boardArea;
    private State state = State.ACTIVE;
    private Stage stage = Stage.FIND_DIRECTION;
    private ShotFeedBack currentFeedBack;

    /**
     * State: Find direction.
     */
    private Stack<Direction> excludedDirections;
    private Direction lastCheckedDirection = null;

    /**
     * State: Follow.
     */
    private Direction currentFollowDirection = null;

    public DefaultHunter(Shooter shooter, Position initialPosition) {
        this.shooter = shooter;
        this.shooterComponent = shooter.getShooterComponent();
        this.initialPosition = new r1.Position(initialPosition.x, initialPosition.y);
        this.memory = shooter.getMemory();
        this.boardArea = new PositionedArea(memory.sizeX, memory.sizeY, new Position(0, 0));
        this.excludedDirections = findExcludedDirections(initialPosition);
    }

    private Stack<Direction> findExcludedDirections(Position position) {
        Stack<Direction> directions = new Stack<>();
        r1.Position betterPosition = new r1.Position(position);

        r1.Position top = betterPosition.top();
        if (memory.hasBeenFiredAt(top)) {
            directions.add(Direction.TOP);
        }

        r1.Position bottom = betterPosition.bottom();
        if (memory.hasBeenFiredAt(bottom)) {
            directions.add(Direction.BOTTOM);
        }

        r1.Position left = betterPosition.left();
        if (memory.hasBeenFiredAt(left)) {
            directions.add(Direction.LEFT);
        }

        r1.Position right = betterPosition.right();
        if (memory.hasBeenFiredAt(right)) {
            directions.add(Direction.RIGHT);
        }

        return directions;
    }

    @Override
    public Queue<Position> getFireQueue() {
        fireQueue.clear();
        
        System.out.println("getFireQueue:");
        System.out.println(this);
        
        if (stage == Stage.FIND_DIRECTION) {
            return fireFindDirection();
        }

        if (stage == Stage.FOLLOW) {
            return fireFollow();
        }

        throw new UnsupportedOperationException();
    }

    private Queue<Position> fireFindDirection() {

        if (excludedDirections.size() == 4) {
            throw new IllegalStateException("Too many excluded directions.");
        }

        Direction nextDirection = getNextPossibleDirection();
        System.out.println(nextDirection);
        Position position = initialPosition.getNeighbor(nextDirection);
        this.lastCheckedDirection = nextDirection;
        fireQueue.add(position);

        return fireQueue;
    }

    private Direction getNextPossibleDirection() {

        if (!excludedDirections.contains(Direction.TOP)) {
            return Direction.TOP;
        }

        if (!excludedDirections.contains(Direction.BOTTOM)) {
            return Direction.BOTTOM;
        }

        if (!excludedDirections.contains(Direction.LEFT)) {
            return Direction.LEFT;
        }

        if (!excludedDirections.contains(Direction.RIGHT)) {
            return Direction.RIGHT;
        }

        throw new IllegalStateException("No next direction.");
    }

    private Queue<Position> fireFollow() {

        if (currentFollowDirection == null) {
            throw new IllegalStateException("No current follow direction.");
        }

        if (currentFeedBack.wasHit()) {
            return continueFollow();
        } else {
            return inverseFollow();
        }
    }

    private Queue<Position> continueFollow() {
        Queue fireQueue = new ArrayDeque();
        r1.Position position = currentFeedBack.getPosition();
        r1.Position neighbor = position.getNeighbor(lastCheckedDirection);

        if (!neighbor.inside(boardArea)) {
            return inverseFollow();
        }

        fireQueue.add(neighbor);
        return fireQueue;
    }

    private Queue<Position> inverseFollow() {
        Direction inverseDirection = lastCheckedDirection.inverse();
        r1.Position nextPosition = currentFeedBack.getPosition();

        while (true) {
            r1.Position testPosition = nextPosition.getNeighbor(inverseDirection);
            if (testPosition.inside(boardArea) && !memory.hasBeenFiredAt(testPosition)) {
                nextPosition = testPosition;
                continue;
            }

            break;
        }

        Queue fireQueue = new ArrayDeque();
        fireQueue.add(nextPosition);
        return fireQueue;
    }

    @Override
    public void hitFeedBack(ShotFeedBack feedBack) {

        this.currentFeedBack = feedBack;

        if (feedBack.sunkShip()) {
            this.state = State.FINISHED;
            this.shooter.getShooterComponent().onHunterFinished(this, new HunterReport());
            return;
        }

        if (stage == Stage.FIND_DIRECTION && feedBack.wasHit()) {
            this.stage = Stage.FOLLOW;
            this.currentFollowDirection = lastCheckedDirection;
            this.excludedDirections.add(lastCheckedDirection);
            return;
        }

        if (stage == Stage.FIND_DIRECTION && !feedBack.wasHit()) {
            this.excludedDirections.add(lastCheckedDirection);
            return;
        }
    }

    @Override
    public void startRound(int round) {

    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        System.out.println(this);
    }

    @Override
    public ShooterComponent getShooterComponent() {
        return shooterComponent;
    }

    @Override
    public String toString() {
        return "DefaultHunter{" + "shooter=" + shooter + ", initialPosition=" + initialPosition + ", shooterComponent=" + shooterComponent + ", memory=" + memory + ", fireQueue=" + fireQueue + ", boardArea=" + boardArea + ", state=" + state + ", stage=" + stage + ", currentFeedBack=" + currentFeedBack + ", excludedDirections=" + excludedDirections + ", lastCheckedDirection=" + lastCheckedDirection + ", currentFollowDirection=" + currentFollowDirection + '}';
    }
}
