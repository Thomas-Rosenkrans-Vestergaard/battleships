package r1;

public class PositionedArea extends Area {

    /**
     * The {@link Position} of the {@link PositionedArea}. The {@link Position}
     * is the lowest-left square of the area.
     */
    private Position position;

    /**
     * Creates a new {@link PositionedArea}.
     *
     * @param sizeX The size of the {@link Area} on the x-axis.
     * @param sizeY The size of the {@link Area} on the y-axis.
     * @param position The {@link Position} of the {@link PositionedArea}. The
     * {@link Position} is the lowest-left square of the area.
     */
    public PositionedArea(int sizeX, int sizeY, Position position) {
        super(sizeX, sizeY);
        this.position = position;
    }

    /**
     * Returns the position anchoring the {@link PositionedArea}.
     *
     * @return The position anchoring the {@link PositionedArea}.
     */
    public Position getPosition() {
        return position;
    }
}
