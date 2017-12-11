package r1.shooting.shooter;

import battleship.interfaces.Position;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
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

    private final Map<Direction, Position> furthestDirectionPosition = new HashMap<>();

    private Stage stage = Stage.FIND_DIRECTION;
    private ShotFeedback currentFeedBack;

    private Stack<Direction> excludedDirections;
    private int numberOfHits = 1;

    public Hunter(ShooterComponent shooterComponent, ShooterComponentMemory memory, Position initialPosition) {
        this.shooterComponent = shooterComponent;
        this.initialPosition = new r1.Position(initialPosition.x, initialPosition.y);
        this.memory = memory;
        this.boardArea = new PositionedArea(memory.sizeX, memory.sizeY, new Position(0, 0));
        this.excludedDirections = getInitialExcludedDirections(initialPosition);
        this.furthestDirectionPosition.put(Direction.TOP, initialPosition);
        this.furthestDirectionPosition.put(Direction.BOTTOM, initialPosition);
        this.furthestDirectionPosition.put(Direction.LEFT, initialPosition);
        this.furthestDirectionPosition.put(Direction.RIGHT, initialPosition);

//        if (memory.hasBeenFiredAt(initialPosition)) {
//            this.numberOfHits = 1;
//        }
    }

    private Stack<Direction> getInitialExcludedDirections(Position position) {

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
        if (memory.hasBeenFiredAt(right) || !right.inside(boardArea)) {
            directions.add(Direction.RIGHT);
        }

        return directions;
    }

    @Override
    public Position getFirePosition() {

        System.out.println(this);

        if (stage == Stage.FIND_DIRECTION) {
            return excludeDirections();
        }

        if (stage == Stage.FOLLOW_DIRECTION) {
            return followDirection();
        }

        throw new UnsupportedOperationException();
    }

    private Position excludeDirections() {

        Direction nextDirection = getNextPossibleDirection();
        Position position = initialPosition.getNeighbor(nextDirection);

        return position;
    }

    private Position followDirection() {

        if (currentFeedBack.wasHit()) {
            return continueFollow();
        } else {
            return changeFollowDirection();
        }
    }

    private Position continueFollow() {

        r1.Position position = currentFeedBack.getPosition();
        Direction direction = this.initialPosition.getDirectionTo(position);
        r1.Position nextPosition = position.getNeighbor(direction);

        if (!nextPosition.inside(boardArea) || memory.hasBeenFiredAt(nextPosition)) {
            this.excludedDirections.add(direction);
            System.out.println("USE CHANGE INSTEAD=" + nextPosition);
            System.out.println("notIsInside=" + !nextPosition.inside(boardArea));
            System.out.println("heaBeenFiredAt=" + memory.hasBeenFiredAt(nextPosition));
            return changeFollowDirection();
        }

        return nextPosition;
    }

    private Position changeFollowDirection() {

        System.out.println("changeDirection");

        Direction direction = getNextPossibleDirection();

        r1.Position nextPosition = currentFeedBack.getPosition();

        System.out.println("nextPosition=" + nextPosition);
        System.out.println("inverse=" + direction);

        while (true) {
            r1.Position testPosition = nextPosition.getNeighbor(direction);
            System.out.println("testPosition=" + testPosition);
            System.out.println("inside=" + testPosition.inside(boardArea));
            System.out.println("fired=" + memory.hasBeenFiredAt(testPosition));
            if (!testPosition.inside(boardArea)) {
                throw new IllegalStateException();
            }

            if (memory.hasBeenFiredAt(testPosition)) {
                nextPosition = testPosition;
                continue;
            }

            nextPosition = testPosition;
            break;
        }

        System.out.println("nextPosition=" + nextPosition);

        return nextPosition;
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

    @Override
    public void onFeedBack(ShotFeedback feedback) {;

        Position position = feedback.getPosition();
        Direction direction = initialPosition.getDirectionTo(position);

        System.out.println("before=" + feedback.getPreviousEnemyFleet().getNumberOfShips());
        System.out.println("after=" + feedback.getCurrentEnemyFleet().getNumberOfShips());
        System.out.println("sunkShip=" + feedback.sunkShip());

        this.currentFeedBack = feedback;

        if (feedback.wasHit()) {
            this.numberOfHits++;
            this.furthestDirectionPosition.put(direction, position);
            System.out.println("numberOFHITS=" + this.numberOfHits);
        }

        if (!feedback.wasHit()) {
            this.excludedDirections.add(initialPosition.getDirectionTo(feedback.getPosition()));
        }

        if (feedback.sunkShip()) {
            int sunkShip = feedback.getSunkShip();

            if (sunkShip == -1) {
                throw new IllegalStateException("sunkShip == -1");
            }

            this.shooterComponent.removeShooter(this);

            System.out.println("sunkShip=" + sunkShip);
            System.out.println("numberOfHits=" + numberOfHits);

            if (sunkShip != numberOfHits) {
                Hunter newHunter = new Hunter(shooterComponent, memory, initialPosition);
                this.shooterComponent.pushShooter(newHunter);
            }

            return;
        }

        if (stage == Stage.FIND_DIRECTION && feedback.wasHit()) {
            this.stage = Stage.FOLLOW_DIRECTION;
            return;
        }

        if (stage == Stage.FOLLOW_DIRECTION && !feedback.wasHit()) {
            this.stage = Stage.FIND_DIRECTION;
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
        return "Hunter{" + "shooterComponent=" + shooterComponent + ", initialPosition=" + initialPosition + ", boardArea=" + boardArea + ", memory=" + memory + ", furthestDirectionPosition=" + furthestDirectionPosition + ", stage=" + stage + ", currentFeedBack=" + currentFeedBack + ", excludedDirections=" + excludedDirections + ", numberOfHits=" + numberOfHits + '}';
    }

}
