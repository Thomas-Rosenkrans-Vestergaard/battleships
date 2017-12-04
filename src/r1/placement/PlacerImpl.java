package r1.placement;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.logging.Level;
import java.util.logging.Logger;
import r1.HeatMap;
import r1.HeatMapOutOfBoundsException;

public class PlacerImpl implements Placer
{
    private HeatMap incomingHeatMap;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY)
    {
        this.incomingHeatMap =  new HeatMap(sizeX,sizeY);
    }

    @Override
    public void placeShips(Fleet fleet, Board board)
    {

        int numberOfShips = fleet.getNumberOfShips();

        for (int i = 0; i <= numberOfShips; i++)
        {
            Ship ship = fleet.getShip(i);
            Position pstn = new Position(0, i);
            board.placeShip(pstn, ship, false);
             
            System.out.println(pstn.toString());
        }
        
    }

    @Override
    public void incoming(Position pos)
    {
        try
        {
            incomingHeatMap.put(pos);
        } catch (HeatMapOutOfBoundsException ex)
        {
            Logger.getLogger(PlacerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void startRound(int round)
    {
    }

    @Override
    public void endRound(int round, int points, int enemyPoints)
    {
    }

    @Override
    public void endMatch(int won, int lost, int draw)
    {
    }
}
