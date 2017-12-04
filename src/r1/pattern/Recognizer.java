package r1.pattern;

import battleship.interfaces.Position;

public interface Recognizer {

    /**
     * Pushes a new {@link Position} to the {@link Recognizer}.
     *
     * @param posiiton The position to push to the {@link Recognizer}.
     */
    public void push(Position posiiton);

    /**
     * Returns the score of the {@link Recognizer}. The score is a percentage.
     *
     * @return The score of the {@link Recognizer}.
     */
    public double getScore();
}
