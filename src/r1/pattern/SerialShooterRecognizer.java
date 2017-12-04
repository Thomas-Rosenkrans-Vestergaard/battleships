package r1.pattern;

import battleship.interfaces.Board;
import battleship.interfaces.Position;
import java.util.Stack;

public class SerialShooterRecognizer implements Recognizer {

    public enum Start {
        NW,
        NE,
        EAST_TO_WEST,
        WEST_TO_EAST,
    }

    public enum Direction {
        VERTICAL,
        HORIZONTAL,
    }

    private final Board board;
    private final Start direction;
    private final Stack<Position> pushes;
    private Position last;
    private double score;

    public SerialShooterRecognizer(Board board, Start direction) {
        this.board = board;
        this.direction = direction;
        this.pushes = new Stack<>();
    }

    @Override
    public void push(Position position) {
        pushes.push(position);

        if (last == null) {
            if (direction == Start.NW) {

            }
        }
    }

    @Override
    public double getScore() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
