package r1;

public class Area {

    /**
     * The size of the {@link Area} on the x-axis.
     */
    public final int sizeX;

    /**
     * The size of the {@link Area} on the y-axis.
     */
    public final int sizeY;

    /**
     * Creates a new {@link Area}.
     *
     * @param sizeX The size of the {@link Area} on the x-axis.
     * @param sizeY The size of the {@link Area} on the y-axis.
     */
    public Area(int sizeX, int sizeY) {

        if (sizeX < 1) {
            throw new IllegalArgumentException("sizeX must be positive.");
        }

        if (sizeY < 1) {
            throw new IllegalArgumentException("sizeY must be positive.");
        }

        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    /**
     * Returns the size of the {@link Area} on the x-axis.
     *
     * @return The size of the {@link Area} on the x-axis.
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * Returns the size of the {@link Area} on the y-axis.
     *
     * @return The size of the {@link Area} on the y-axis.
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * Returns the area of the {@link Area}.
     *
     * @return The area of the {@link Area}.
     */
    public int getArea() {
        return sizeX * sizeY;
    }
}
