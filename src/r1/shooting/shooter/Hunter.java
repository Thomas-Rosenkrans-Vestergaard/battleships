package r1.shooting.shooter;

import battleship.interfaces.Position;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import r1.Position.Direction;
import r1.PositionedArea;
import r1.placement.ShipPlacement;
import r1.shooting.ShooterComponent;
import r1.shooting.ShooterComponentMemory;
import r1.shooting.ShotFeedback;

public class Hunter implements Shooter {

    public enum Mode {
        FIND_DIRECTION,
        FOLLOW_DIRECTION,
        CLEANUP
    }

    private final ShooterComponent shooterComponent;
    private final r1.Position initialPosition;
    private final PositionedArea boardArea;
    private final ShooterComponentMemory memory;

    private Mode mode = Mode.FIND_DIRECTION;
    private ShotFeedback currentFeedBack;
    private Stack<Direction> excludedDirections;
    private int numberOfHits = 1;
    private Stack<r1.Position> positionsHit = new Stack<>();

    public Hunter(ShooterComponent shooterComponent, ShooterComponentMemory memory, r1.Position initialPosition) {
        this.shooterComponent = shooterComponent;
        this.initialPosition = new r1.Position(initialPosition.x, initialPosition.y);
        this.memory = memory;
        this.boardArea = new PositionedArea(memory.sizeX, memory.sizeY, new Position(0, 0));
        this.excludedDirections = getInitialExcludedDirections(initialPosition);
        this.positionsHit.add(initialPosition);
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

        if (mode == Mode.CLEANUP) {
            Direction nextDirection = getNextPossibleDirection();
            Position nextPosition = initialPosition.getNeighbor(nextDirection);
            return nextPosition;
        }

        if (mode == Mode.FIND_DIRECTION) {
            return excludeDirections();
        }

        if (mode == Mode.FOLLOW_DIRECTION) {
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
            System.out.println("USE CHANGE INSTEAD=" + position);
            System.out.println("notIsInside=" + !nextPosition.inside(boardArea));
            System.out.println("heaBeenFiredAt=" + memory.hasBeenFiredAt(nextPosition));
            return changeFollowDirection();
        }

        return nextPosition;
    }

    private Position changeFollowDirection() {

        System.out.println("changeDirection");

        Direction direction = getNextPossibleDirection();

        r1.Position position = currentFeedBack.getPosition();

        System.out.println("nextPosition=" + position);
        System.out.println("inverse=" + direction);

        System.out.println("used=" + Arrays.toString(memory.getUsedPositions().toArray()));
        
        while (true) {
            
            r1.Position testPosition = position.getNeighbor(direction);
            System.out.println("testPosition=" + testPosition);
            System.out.println("inside=" + testPosition.inside(boardArea));
            System.out.println("fired=" + memory.hasBeenFiredAt(testPosition));

            if (!testPosition.inside(boardArea)) {
                throw new IllegalStateException();
            }
            
            if (!memory.hasBeenFiredAt(testPosition)) {
                return testPosition;
            }
            
            position = testPosition;
        }




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

    public boolean canFire() {
        return this.excludedDirections.size() != 4;
    }

    @Override
    public void onFeedBack(ShotFeedback feedback) {;

        r1.Position position = feedback.getPosition();

        System.out.println("before=" + feedback.getPreviousEnemyFleet().getNumberOfShips());
        System.out.println("after=" + feedback.getCurrentEnemyFleet().getNumberOfShips());
        System.out.println("sunkShip=" + feedback.sunkShip());

        this.currentFeedBack = feedback;

        if (feedback.wasHit()) {
            this.numberOfHits++;
            this.positionsHit.add(position);
            System.out.println("numberOFHITS=" + this.numberOfHits);
        } else {
            this.excludedDirections.add(initialPosition.getDirectionTo(feedback.getPosition()));
        }

        if (feedback.sunkShip()) {
            int sunkShip = feedback.getSunkShip();

            if (sunkShip == -1) {
                throw new IllegalStateException("sunkShip == -1");
            }

            if (sunkShip != numberOfHits) {

                Collection<Position> shipPositions = new Stack<>();
                r1.Position currentShipPosition = feedback.getPosition();
                Direction direction = currentShipPosition.getDirectionTo(initialPosition);
                for (int x = 0; x < sunkShip; x++) {
                    shipPositions.add(currentShipPosition);
                    currentShipPosition = currentShipPosition.getNeighbor(direction);
                }

                for (r1.Position hit : positionsHit) {
                    if (!shipPositions.contains(hit)) {
                        Hunter hunter = new Hunter(shooterComponent, memory, hit);
                        System.out.println("ADDED HUNTER AT " + hit);
                        this.shooterComponent.pushShooter(hunter);
                    }
                }
            }

            this.shooterComponent.removeShooter(this);

            return;
        }

        if (mode == Mode.FIND_DIRECTION && feedback.wasHit()) {
            this.mode = Mode.FOLLOW_DIRECTION;
            return;
        }

        if (mode == Mode.FOLLOW_DIRECTION && !feedback.wasHit()) {
            this.mode = Mode.FIND_DIRECTION;
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
        return "Hunter{" + "shooterComponent=" + shooterComponent + ", initialPosition=" + initialPosition + ", boardArea=" + boardArea + ", memory=" + memory + ", mode=" + mode + ", currentFeedBack=" + currentFeedBack + ", excludedDirections=" + excludedDirections + ", numberOfHits=" + numberOfHits + '}';
    }
}
