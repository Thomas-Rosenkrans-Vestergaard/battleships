package r1.heatmap;

import battleship.interfaces.Position;

/**
 *
 * @author Thomas
 */
public class HeatMapOutOfBoundsException extends Exception {
    private Position position;

    public HeatMapOutOfBoundsException(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
    
}
