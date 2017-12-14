package r1;

import java.util.List;
import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.Collection;

public class PositionedArea extends Area {

    /**
     * The {@link Position} of the {@link PositionedArea}. The {@link Position}
     * is the lowest-left square of the area.
     */
    public final Position position;

    private Collection<r1.Position> positions;

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

    public boolean overlaps(PositionedArea other) {
        return this.position.x < other.position.x + other.sizeX && this.position.x + sizeX > other.position.x && this.position.y < other.position.y + other.sizeY && this.position.y + sizeY > other.position.y;
    }

    public boolean overlaps(List<PositionedArea> others) {
        for (PositionedArea other : others) {
            if (this.overlaps(other)) {
                return true;
            }
        }

        return false;
    }

    public boolean contains(PositionedArea other) {
        Position otherPosition = other.getPosition();

        return position.x <= otherPosition.x && position.y <= otherPosition.y && position.x + sizeX >= otherPosition.x + other.sizeX && position.y + sizeY >= otherPosition.y + other.sizeY;
    }

    public boolean containsAny(Iterable<? extends Position> positions) {
        for (Position position : positions) {
            if (this.position.x <= position.x && this.position.y <= position.y && this.position.x + this.sizeX >= position.x + 1 && this.position.y + this.sizeY >= position.y + 1) {
                return true;
            }
        }

        return false;
    }

    public boolean contains(Position other) {
        return position.x <= other.x && position.y <= other.y && position.x + sizeX <= other.x && position.y + sizeY <= other.y;
    }

    /**
     * Returns the position anchoring the {@link PositionedArea}.
     *
     * @return The position anchoring the {@link PositionedArea}.
     */
    public Position getPosition() {
        return position;
    }

    public Collection<? extends r1.Position> getPositions() {

        if (this.positions == null) {
            this.positions = new ArrayList<>();
            for (int x = this.position.x; x < this.position.x + this.sizeX; x++) {
                for (int y = this.position.y; y < this.position.y + this.sizeY; y++) {
                    positions.add(new r1.Position(x, y));
                }
            }
        }

        return positions;
    }

    @Override
    public String toString() {
        return "PositionedArea{" + "position=" + position + ", sizeX=" + sizeX + ", sizeY=" + sizeY + '}';
    }
}
