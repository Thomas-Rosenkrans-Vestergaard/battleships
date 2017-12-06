package r1.placement;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import r1.Area;
import r1.PositionedArea;
import r1.heatmap.HeatMap;
import r1.heatmap.HeatMapArea;
import r1.heatmap.HeatMapVersion;
import r1.heatmap.HeatMapView;
import r1.heatmap.NoActiveHeatMapVersionException;
import r1.heatmap.UnknownHeatMapVersionException;

public class PlacerImpl implements Placer {

    /**
     * The {@link HeatMap} watching the incomming fire.
     */
    private HeatMap incomingHeatMap;

    /**
     * The current round number.
     */
    private int currentRound;

    /**
     * The number of rounds to be played.
     */
    private int numberOfRounds;

    /**
     * The number of heat maps generated during a match.
     */
    private int numberOfHeatMaps;

    /**
     * The number of shots still to be fired from the opponent.
     */
    private int remainingNumberOfShots;

    /**
     * The size of the board on the x-axis.
     */
    private int sizeX;

    /**
     * The size of the board on the y-axis.
     */
    private int sizeY;

    /**
     * The current version of the {@link HeatMap}.
     */
    private int currentHeatMapVersion;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        this.incomingHeatMap = new HeatMap(sizeX, sizeY);
        this.incomingHeatMap.makeVersion(true);
        this.numberOfRounds = rounds;
        this.numberOfHeatMaps = (int) (5 * Math.pow(2, Math.log10((double) rounds) - 1));
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    public void startRound(int round) {
        System.out.println("startRound:" + round);
        System.out.println("numberOfRounds:" + numberOfRounds);
        System.out.println("numberOfHeatMaps:" + numberOfHeatMaps);
        this.remainingNumberOfShots = sizeX * sizeY;
        this.currentRound = round;
        if ((round - 1) % (numberOfRounds / numberOfHeatMaps) == 0) {
            this.currentHeatMapVersion = incomingHeatMap.makeVersion(true);
            System.out.println("currentHeatMapVersion:" + this.currentHeatMapVersion);
        }
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {

        try {

            int numberOfShips = fleet.getNumberOfShips();

            if (currentRound <= (numberOfRounds / numberOfHeatMaps)) {
                for (int i = 0; i < numberOfShips; i++) {
                    Ship ship = fleet.getShip(i);
                    Position pstn = new Position(0, i);
                    board.placeShip(pstn, ship, false);
                }
                return;
            }

            HeatMapView version = incomingHeatMap.mergeLast(3);
            List<PositionedArea> usedAreas = new ArrayList<>();

            System.out.println("PLACEMENT");
            System.out.println("numberOfVersions:" + incomingHeatMap.getNumberOfVersions());
            System.out.println("currentVersion:" + incomingHeatMap.getActiveVersionNumber());
            System.out.println(version.toString());
            for (int i = 0; i < numberOfShips; i++) {

                Ship currentShip = fleet.getShip(i);

                Area horizontalArea = new Area(currentShip.size(), 1);
                Area verticalArea = new Area(1, currentShip.size());

                HeatMapArea horizontalPositionedArea = version.getColdestAreaExclude(horizontalArea, usedAreas);
                HeatMapArea verticalPositionedArea = version.getColdestAreaExclude(verticalArea, usedAreas);

                if (horizontalPositionedArea.getAverageHeat() < verticalPositionedArea.getAverageHeat()) {
                    board.placeShip(horizontalPositionedArea.getPosition(), currentShip, false);
                    usedAreas.add(horizontalPositionedArea);
                } else {
                    board.placeShip(verticalPositionedArea.getPosition(), currentShip, true);
                    usedAreas.add(verticalPositionedArea);
                }
            }
        } catch (NoActiveHeatMapVersionException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void incoming(Position pos) {
        try {
            incomingHeatMap.setChangeValue(remainingNumberOfShots--);
            System.out.println("INCOMING " + pos);
            System.out.println(incomingHeatMap.getActiveVersionNumber());

            incomingHeatMap.increment(pos);
            
        } catch (NoActiveHeatMapVersionException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

        try {
            System.out.println("endRound");
            System.out.println("0,0:" + incomingHeatMap.getLastVersion().getValue(new r1.Position(0, 0)));
            System.out.println(round);
            System.out.println(numberOfRounds / numberOfHeatMaps);
            System.out.println("currentVersionNumber:" + incomingHeatMap.getActiveVersionNumber());
            System.out.println("numberOfVersions " + incomingHeatMap.getNumberOfVersions());
            if (round != 1 && round % (numberOfRounds / numberOfHeatMaps) == 0) {
                System.out.println("Output");
                HeatMapVersion view = incomingHeatMap.getLastVersion();
                System.out.println(view.getClass());
                System.out.println(view.toString());
                System.out.println(view.getVersion());
            }

        } catch (NoActiveHeatMapVersionException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        try {
            System.out.println("endMatch");
            for (int x = 0; x < numberOfHeatMaps; x++) {
                System.out.println("map:" + x);
                System.out.println(incomingHeatMap.getVersion(x));
            }
        } catch (UnknownHeatMapVersionException e) {
            throw new IllegalStateException(e);
        }
    }
}
