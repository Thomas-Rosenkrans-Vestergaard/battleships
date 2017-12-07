package r1.shooting.shooter;

import battleship.interfaces.Position;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;
import r1.Position.Direction;
import r1.PositionedArea;
import r1.shooting.ShooterComponent;
import r1.shooting.ShooterComponentMemory;
import r1.shooting.ShotFeedback;

public class Hunter implements Shooter {

    public enum Stage {
        FIND_DIRECTION,
        FOLLOW_DIRECTION,
    }

    private final ShooterComponent shooterComponent;
    private final r1.Position initialPosition;
    private final PositionedArea boardArea;
    private final ShooterComponentMemory memory;

    private Stage stage = Stage.FIND_DIRECTION;
    private ShotFeedback currentFeedBack;

    private Stack<Direction> excludedDirections;
    private Direction lastCheckedDirection = null;

    private Direction currentFollowDirection = null;

    public Hunter(ShooterComponent shooterComponent, ShooterComponentMemory memory, Position initialPosition) {
        this.shooterComponent = shooterComponent;
        this.initialPosition = new r1.Position(initialPosition.x, initialPosition.y);
        this.memory = memory;
        this.boardArea = new PositionedArea(memory.sizeX, memory.sizeY, new Position(0, 0));
        this.excludedDirections = findExcludedDirections(initialPosition);
    }

    private Stack<Direction> findExcludedDirections(Position position) {
        Stack<Direction> directions = new Stack<>();
        r1.Position betterPosition = new r1.Position(position);

        r1.Position top = betterPosition.top();
        if (memory.hasBeenFiredAt(top) || !top.inside(boardArea)) {
            directions.add(Direction.TOP);
        }

        r1.Position bottom = betterPosition.bottom();
        if (memory.hasBeenFiredAt(bottom) || !bottom.inside(boardArea)) {
            directions.add(Direction.BOTTOM);
        }

        r1.Position left = betterPosition.left();
        if (memory.hasBeenFiredAt(left) || !left.inside(boardArea)) {
            directions.add(Direction.LEFT);
        }

        r1.Position right = betterPosition.right();
        if (memory.hasBeenFiredAt(right) || right.inside(boardArea)) {
            directions.add(Direction.RIGHT);
        }

        return directions;
    }

    @Override
    public Queue<Position> getFireQueue() {

        System.out.println("STAGE=" + stage);

        if (stage == Stage.FIND_DIRECTION) {
            return fireFindDirection();
        }

        if (stage == Stage.FOLLOW_DIRECTION) {
            return fireFollow();
        }

        throw new UnsupportedOperationException();
    }

    private Queue<Position> fireFindDirection() {

        if (excludedDirections.size() == 4) {
            throw new IllegalStateException("Too many excluded directions.");
        }

        Queue<Position> fireQueue = new ArrayDeque<>();
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

        if (!neighbor.inside(boardArea) || memory.hasBeenFiredAt(position)) {
            return inverseFollow();
        }

        fireQueue.add(neighbor);
        return fireQueue;
    }

    private Queue<Position> inverseFollow() {
        Direction inverseDirection = lastCheckedDirection.inverse();
        r1.Position nextPosition = currentFeedBack.getPosition();

        System.out.println("lastCheckedDirection=" + lastCheckedDirection);
        System.out.println("inverse=" + inverseDirection);

        // TODO: Iterate specific number of times.
        for(int x = 0; x < 6; x++) {
            r1.Position testPosition = nextPosition.getNeighbor(inverseDirection);
            System.out.println("testPosition=" + testPosition);
            System.out.println("eval=" + (testPosition.inside(boardArea) && !memory.hasBeenFiredAt(testPosition)));
            if (testPosition.inside(boardArea) && !memory.hasBeenFiredAt(testPosition)) {
                nextPosition = testPosition;
                break;
            }
        }

        Queue fireQueue = new ArrayDeque();
        this.lastCheckedDirection = inverseDirection;
        fireQueue.add(nextPosition);
        return fireQueue;
    }

    @Override
    public void onFeedBack(ShotFeedback feedback) {

        this.currentFeedBack = feedback;

        System.out.println("Hunter:onFeedBack=");
        System.out.println(feedback);
        System.out.println("before=" + feedback.getPreviousEnemyFleet());
        System.out.println("after=" + feedback.getPreviousEnemyFleet());
        System.out.println("hash = " + System.identityHashCode(feedback.getPreviousEnemyFleet()) + ":" + System.identityHashCode(feedback.getCurrentEnemyFleet()));
        System.out.println("Sunkship=" + feedback.sunkShip());

        if (feedback.sunkShip()) {
            System.out.println("REMOVED HUNTER");
            this.shooterComponent.removeShooter(this);
            return;
        }

        if (stage == Stage.FIND_DIRECTION && feedback.wasHit()) {
            this.stage = Stage.FOLLOW_DIRECTION;
            this.currentFollowDirection = lastCheckedDirection;
            this.excludedDirections.add(lastCheckedDirection);
            return;
        }

        if (stage == Stage.FIND_DIRECTION && !feedback.wasHit()) {
            this.excludedDirections.add(lastCheckedDirection);
            return;
        }
    }

    @Override
    public void onSecondaryFeedBack(ShotFeedback feedback) {

    }

    @Override
    public void startRound(int round) {

    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

    }

    @Override
    public ShooterComponent getShooterComponent() {
        return shooterComponent;
    }

    @Override
    public String toString() {
        return "Hunter{" + "shooterComponent=" + shooterComponent + ", initialPosition=" + initialPosition + ", boardArea=" + boardArea + ", memory=" + memory + ", stage=" + stage + ", currentFeedBack=" + currentFeedBack + ", excludedDirections=" + excludedDirections + ", lastCheckedDirection=" + lastCheckedDirection + ", currentFollowDirection=" + currentFollowDirection + '}';
    }
}
