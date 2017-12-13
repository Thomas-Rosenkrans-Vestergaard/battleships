/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1.shooting.shooter;

import java.util.ArrayList;
import java.util.Collection;
import r1.Position;
import java.util.List;
import java.util.Stack;
import r1.Position.Direction;
import r1.PositionedArea;
import r1.placement.ShipPlacement.Rotation;
import r1.shooting.ShooterComponent;
import r1.shooting.ShooterComponentMemory;
import r1.shooting.ShotFeedback;

/**
 *
 * @author Skole
 */
public class Cleaner implements Shooter {

    private ShooterComponent shooterComponent;
    private ShooterComponentMemory memory;
    private Position center;
    private Rotation rotation;
    private PositionedArea board;
    private Stack<Direction> possibleDirections;
    private ShotFeedback currentFeedback;

    public Cleaner(ShooterComponent shooterComponent, ShooterComponentMemory memory, Position center, Rotation rotation, PositionedArea board) {
        this.shooterComponent = shooterComponent;
        this.memory = memory;
        this.center = center;
        this.rotation = rotation;
        this.board = board;
        this.possibleDirections = getInitialPossibleDirections();
    }

    private Stack<Direction> getInitialPossibleDirections() {

        Stack<Direction> possibleDirections = new Stack<>();

        Position top = center.top();
        Position bottom = center.bottom();
        Position left = center.left();
        Position right = center.right();

        if ((this.rotation == null || this.rotation == Rotation.VERTICAL) && top.inside(board) && !this.memory.hasBeenFiredAt(top)) {
            possibleDirections.add(Direction.TOP);
        }

        if ((this.rotation == null || this.rotation == Rotation.VERTICAL) && bottom.inside(board) && !this.memory.hasBeenFiredAt(bottom)) {
            possibleDirections.add(Direction.BOTTOM);
        }

        if ((this.rotation == null || this.rotation == Rotation.VERTICAL) && left.inside(board) && !this.memory.hasBeenFiredAt(left)) {
            possibleDirections.add(Direction.LEFT);
        }

        if ((this.rotation == null || this.rotation == Rotation.VERTICAL) && right.inside(board) && !this.memory.hasBeenFiredAt(right)) {
            possibleDirections.add(Direction.RIGHT);
        }

        return possibleDirections;
    }

    @Override
    public boolean canFire() {
        return true;
    }

    public Direction getNextPosibleDirection() {

        if (possibleDirections.isEmpty()) {
            throw new IllegalStateException();
        }

        return possibleDirections.peek();
    }

    @Override
    public ShooterComponent getShooterComponent() {
        return shooterComponent;
    }

    @Override
    public Position getFirePosition() {
        Position position = currentFeedback.getPosition();

        if (currentFeedback.wasHit()) {
            Direction direction = center.getDirectionTo(position);
            return position.getNeighbor(direction);
        } else {
            Direction direction = getNextPosibleDirection();
            return center.getNeighbor(direction);
        }
    }

    @Override
    public void onFeedBack(ShotFeedback feedback) {

        this.currentFeedback = feedback;

        Position position = feedback.getPosition();
        Direction direction = center.getDirectionTo(position);
        if (feedback.wasHit()) {

        } else {
            this.possibleDirections.remove(direction);
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
}
