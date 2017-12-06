package r1.placement;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.logging.Level;
import java.util.logging.Logger;
import r1.heatmap.HeatMap;
import r1.heatmap.HeatMapOutOfBoundsException;

public class PlacerImpl implements Placer {

    private HeatMap incomingHeatMap;
    private int sizeX;
    private int sizeY;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        this.incomingHeatMap = new HeatMap(sizeX, sizeY);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {

        
        
        int numberOfShips = fleet.getNumberOfShips();

<<<<<<< Updated upstream
        for (int i = 0; i < numberOfShips; i++)
        {
=======
        for (int i = 0; i <= numberOfShips; i++) {
>>>>>>> Stashed changes
            Ship ship = fleet.getShip(i);
            Position pstn = new Position(sizeX - ship.size(), sizeY - i - 1);
            board.placeShip(pstn, ship, false);
        }
    }

    @Override
<<<<<<< Updated upstream
    public void incoming(Position pos)
    {
        try
        {
            incomingHeatMap.increment(pos);
        } catch (HeatMapOutOfBoundsException ex)
        {
=======
    public void incoming(Position pos) {
        try {
            incomingHeatMap.put(pos);
        } catch (HeatMapOutOfBoundsException ex) {
>>>>>>> Stashed changes
            Logger.getLogger(PlacerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void startRound(int round) {
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
    }
}
