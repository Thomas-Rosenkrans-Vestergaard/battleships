package r1.placement;

import battleship.interfaces.Position;
import battleship.interfaces.Ship;

public class ShipPlacement {

    /**
     * Ship rotation.
     */
    public enum Rotation {
        VERTICAL,
        HORIZONTAL;

        public boolean toBoolean() {
            return this == Rotation.VERTICAL;
        }
    }

    private Ship ship;

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
     * @param ship
     * @param position The position of the ship to be placed. The position is
     * the bottom-most square when the rotation of the ship is vertical. The
     * position is the left-most square when the rotation of the ship is
     * horizontal.
     * @param rotation The rotation of the ship to be placed.
     */
    public ShipPlacement(Ship ship, Position position, Rotation rotation) {
        this.ship = ship;
        this.position = position;
        this.rotation = rotation;
    }

    /**
     * Creates a new {@link ShipPlacement}.
     *
     * @param ship
     * @param position The position of the ship to be placed. The position is
     * the bottom-most square when the rotation of the ship is vertical. The
     * position is the left-most square when the rotation of the ship is
     * horizontal.
     * @param rotation The rotation of the ship to be placed.
     */
    public ShipPlacement(Ship ship, Position position, boolean rotation) {
        this(ship, position, rotation ? Rotation.VERTICAL : Rotation.HORIZONTAL);
    }

    public Ship getShip() {
        return ship;
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
