package r1;

import battleship.interfaces.Board;

public class Position extends battleship.interfaces.Position {

    /**
     * Creates a new {@link Position}.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Position(int x, int y) {
        super(x, y);
    }

    /**
     * Finds the distance to the provided position. Returns -1 if the distance
     * cannot be found, like in cases when the two positions dont't share a
     * line.
     *
     * @param position The other position.
     * @return The distance. Returns -1 if the two position don't share a line.
     */
    public int distanceTo(Position position) {

        if (this.x != position.x && this.y != position.y) {
            return -1;
        }

        if (this.x == position.x) {
            return Math.abs(this.y - position.y);
        }

        if (this.y == position.y) {
            return Math.abs(this.x - position.x);
        }

        throw new IllegalStateException();
    }

    /**
     * Checks if the provided position and this shares a vertical line.
     *
     * @param position The provided position.
     * @return Whether or not the provided position and this shares a vertical
     * line.
     */
    public boolean isVertical(Position position) {
        return this.x == position.x;
    }

    /**
     * Checks if the provided position and this shares a horizontal line.
     *
     * @param position The provided position.
     * @return Whether or not the provided position and this shares a horizontal
     * line.
     */
    public boolean isHorizontal(Position position) {
        return this.y == position.y;
    }

    /**
     * Checks if the provided position and this shares a line.
     *
     * @param position The provided position.
     * @return Whether or not the provided position and this shares a line.
     */
    public boolean sharesLine(Position position) {
        return this.x == position.x || this.y == position.y;
    }

    /**
     * Returns the direction from this to the provided position. Returns null if
     * the two points don't share a line.
     *
     * @param position The position to find the direction to.
     * @return The direction to the provided position. Returns null if the two
     * points don't share a line.
     */
    public Direction getDirectionTo(Position position) {

        if (this.x == position.x && this.y == position.y) {
            return Direction.CENTER;
        }

        if (this.x == position.x) {
            return this.y > position.y ? Direction.BOTTOM : Direction.TOP;
        }

        if (this.y == position.y) {
            return this.x > position.x ? Direction.LEFT : Direction.RIGHT;
        }

        return null;
    }

    /**
     * Returns the {@link Position} above <code>this</code>.
     *
     * @return The {@link Position} above <code>this</code>.
     */
    public Position top() {
        return new Position(this.x, this.y + 1);
    }

    /**
     * Returns the {@link Position} below <code>this</code>.
     *
     * @return The {@link Position} below <code>this</code>.
     */
    public Position bottom() {
        return new Position(this.x, this.y - 1);
    }

    /**
     * Returns the {@link Position} to the left of <code>this</code>.
     *
     * @return The {@link Position} to the left of <code>this</code>.
     */
    public Position left() {
        return new Position(this.x - 1, this.y);
    }

    /**
     * Returns the {@link Position} to the right of <code>this</code>.
     *
     * @return The {@link Position} to the right of <code>this</code>.
     */
    public Position right() {
        return new Position(this.x + 1, this.y);
    }

    /**
     * Returns the index of the position on a board with the provided sizeX.
     *
     * @param sizeX The sizeX.
     * @return The index of the position on a board with the provided sizeX.
     */
    public int toIndex(int sizeX) {
        return this.y * sizeX + x;
    }

    /**
     * Direction.
     */
    public enum Direction {
        TOP,
        BOTTOM,
        CENTER,
        LEFT,
        RIGHT
    }
}
