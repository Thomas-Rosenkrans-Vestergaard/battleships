package r1;

import battleship.interfaces.Position;
import java.util.Arrays;

public class HeatMap {

    /**
     * The horisontal size of the {@link HeatMap}.
     */
    private final int sizeX;

    /**
     * The vertical size of the {@link HeatMap}.
     */
    private final int sizeY;

    /**
     * The amount incremented when the {@link HeatMap#put(r1.Position)} method
     * is called.
     */
    private final int increment;

    /**
     * The internal storage of the {@link HeatMap}.
     */
    private int[][] map;

    /**
     * Creates a new {@link HeatMap}. The default value of the positions in the
     * {@link HeatMap} are set the zero. The increment value is set to one.
     *
     * @param sizeX The horisontal size of the {@link HeatMap}.
     * @param sizeY The vertical size of the {@link HeatMap}.
     */
    public HeatMap(int sizeX, int sizeY) {

        this(sizeX, sizeY, 0, 1);
    }

    /**
     * Creates a new {@link HeatMap}. The increment value is set to one.
     *
     * @param sizeX The horisontal size of the {@link HeatMap}.
     * @param sizeY The vertical size of the {@link HeatMap}.
     * @param defaultValue The default values of the positions in the
     * {@link HeatMap}.
     */
    public HeatMap(int sizeX, int sizeY, int defaultValue) {
        this(sizeX, sizeY, defaultValue, 1);
    }

    /**
     * Creates a new {@link HeatMap}.
     *
     * @param sizeX The horisontal size of the {@link HeatMap}.
     * @param sizeY The vertical size of the {@link HeatMap}.
     * @param defaultValue The default values of the positions in the
     * {@link HeatMap}.
     * @param increment The amount incremented when the
     * {@link HeatMap#put(r1.Position)} method is called.
     */
    public HeatMap(int sizeX, int sizeY, int defaultValue, int increment) {

        if (sizeX < 1) {
            throw new IllegalArgumentException("sizeX cannot be less than 1.");
        }

        if (sizeY < 1) {
            throw new IllegalArgumentException("sizeY cannot be less than 1.");
        }

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.increment = increment;
        this.map = new int[sizeX][sizeY];
        reset(defaultValue);
    }

    /**
     * Creates a new {@link HeatMap} using a provided map.
     *
     * @param map The map.
     */
    public HeatMap(int[][] map) {
        this(map, 1);
    }

    /**
     * *
     * Creates a new {@link HeatMap} using a provided map.
     *
     * @param map The map.
     * @param increment The increment.
     */
    public HeatMap(int[][] map, int increment) {
        this.map = map;
        this.increment = increment;
        this.sizeX = map.length;
        this.sizeY = map[0].length;
    }

    /**
     * Increments the {@link HeatMap} at the provided position.
     *
     * @param position The posiiton
     * @throws HeatMapOutOfBoundsException
     */
    public void put(Position position) throws HeatMapOutOfBoundsException {
        try {
            map[position.x][position.y] += increment;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new HeatMapOutOfBoundsException();
        }
    }

    public int get(Position position) throws HeatMapOutOfBoundsException {
        try {
            return map[position.x][position.y];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new HeatMapOutOfBoundsException();
        }
    }

    /**
     * Resets the values in the {@link HeatMap} to the provided value.
     *
     * @param to The value to reset the {@link HeatMap} to.
     */
    public void reset(int to) {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                map[x][y] = to;
            }
        }
    }

    /**
     * Resets the values in the {@link HeatMap} to zero.
     */
    public void reset() {
        reset(0);
    }

    /**
     * Returns the horisontal size of the {@link HeatMap}.
     *
     * @return The horisontal size of the {@link HeatMap}.
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * Returns the vertical size of the {@link HeatMap}.
     *
     * @return The vertical size of the {@link HeatMap}.
     */
    public int getSizeY() {
        return sizeY;
    }
}
