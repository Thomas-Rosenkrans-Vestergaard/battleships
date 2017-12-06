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

    private PlacerMemory memory;
    private PlacerTactic heatMapPlacerTactic;
    private PlacerTactic randomPlacerTactic;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        HeatMap incomingHeatMap = new HeatMap(sizeX, sizeY);
        incomingHeatMap.makeVersion(true);
        int numberOfHeatMaps = (int) (5 * Math.pow(2, Math.log10((double) rounds) - 1));
        this.memory = new PlacerMemory(incomingHeatMap, rounds, numberOfHeatMaps, sizeX, sizeY);
        this.heatMapPlacerTactic = new HeatMapPlacerTactic(this.memory);
        this.randomPlacerTactic = new RandomPlacerTactic(this.memory);
    }

    @Override
    public void startRound(int round) {
        memory.startRound(round);
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        int currentRound = memory.getCurrentRound();
        int numberOfRounds = memory.getNumberOfRounds();
        int numberOfHeatMaps = memory.getNumberOfHeatMaps();

        if (currentRound <= (numberOfRounds / numberOfHeatMaps)) {
            randomPlacerTactic.placeShips(fleet, board);
        } else {
            heatMapPlacerTactic.placeShips(fleet, board);
        }
    }

    @Override
    public void incoming(Position pos) {
        memory.incoming(pos);
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        memory.endRound(round, points, enemyPoints);
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        try {
            System.out.println("endMatch");
            for (int x = 0; x < memory.getNumberOfHeatMaps(); x++) {
                System.out.println("map:" + x);
                System.out.println(memory.getIncomingHeatMap().getVersion(x));
            }
        } catch (UnknownHeatMapVersionException e) {
            throw new IllegalStateException(e);
        }
    }
}
