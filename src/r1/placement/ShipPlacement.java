package r1.placement;

import battleship.interfaces.Position;

public class ShipPlacement {

    /**
     * Ship rotation.
     */
    public enum Rotation {
        VERTICAL,
        HORIZONTAL,
    }

    /**
     * The length of the ship to be placed.
     */
    private int shipLength;

    /**
     * The position of the ship to be placed. The position is the bottom-most
     * square when the rotation of the ship is vertical. The position is the
     * left-most square when the rotation of the ship is horizontal.
     */
    private Position position;

    /**
     * The rotation of the ship to be placed.
     */
    private Rotation rotation;

    /**
     * Creates a new {@link ShipPlacement}.
     *
     * @param shipLength The length of the ship to be placed.
     * @param position The position of the ship to be placed. The position is
     * the bottom-most square when the rotation of the ship is vertical. The
     * position is the left-most square when the rotation of the ship is
     * horizontal.
     * @param rotation The rotation of the ship to be placed.
     */
    public ShipPlacement(int shipLength, Position position, Rotation rotation) {
        this.shipLength = shipLength;
        this.position = position;
        this.rotation = rotation;
    }

    /**
     * The the length of the ship to be placed.
     *
     * @return The length of the ship to be placed.
     */
    public int getShipLength() {
        return shipLength;
    }

    /**
     * Returns the position of the ship to be placed.
     *
     * @return The position of the ship to be placed.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Returns the rotation of the ship to be placed.
     *
     * @return The rotation of the ship to be placed.
     */
    public Rotation getRotation() {
        return rotation;
    }
}
