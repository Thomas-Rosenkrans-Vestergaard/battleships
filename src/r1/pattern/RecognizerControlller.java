package r1.pattern;

import battleship.interfaces.Position;

public interface RecognizerControlller {

    /**
     * Pushes a new {@link Position} to the {@link Recognizer}s in the
     * {@link RecognizerControlller}.
     *
     * @param position The position to push.
     */
    public void push(Position position);

    /**
     * Adds a new {@link Recognizer} to the {@link RecognizerController}.
     *
     * @param recognizer The {@link Recognizer} to add.
     * @return The id of the provided {@link Regognizer}.
     */
    public int add(Recognizer recognizer);

    /**
     * Returns the score of the {@link Recognizer} with the provided id.
     *
     * @param recognizer The {@link Recognizer} id.
     * @return The score of the {@link Recognizer} with the provided id.
     */
    public double getScore(int recognizer);

    /**
     * Returns the score of the {@link Recognizer} with the provided id in the
     * last n rounds.
     *
     * @param recognizer The {@link Recognizer} id.
     * @param rounds The number of rounds to count.
     * @return The score.
     */
    public double getScore(int recognizer, int rounds);
}
