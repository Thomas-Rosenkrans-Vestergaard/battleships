package r1.placement.placer;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.List;
import r1.Area;
import r1.PositionedArea;
import r1.heatmap.HeatMap;
import r1.heatmap.HeatMapArea;
import r1.heatmap.HeatMapView;
import r1.heatmap.NoActiveHeatMapVersionException;
import r1.placement.PlacerComponentMemory;

public class HeatMapPlacer implements Placer {

    private final PlacerComponentMemory memory;

    public HeatMapPlacer(PlacerComponentMemory memory) {
        this.memory = memory;
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {

        int currentRound = memory.getCurrentRound();
        int numberOfRounds = memory.getNumberOfRounds();
        int numberOfHeatMaps = memory.getNumberOfHeatMaps();
        HeatMap incomingHeatMap = memory.getIncomingHeatMap();

        int numberOfShips = fleet.getNumberOfShips();

        if (currentRound <= (numberOfRounds / numberOfHeatMaps)) {
            for (int i = 0; i < numberOfShips; i++) {
                Ship ship = fleet.getShip(i);
                Position pstn = new Position(0, i);
                board.placeShip(pstn, ship, false);
            }
            return;
        }

        HeatMapView version = incomingHeatMap.mergeLast(2);
        List<PositionedArea> usedAreas = new ArrayList<>();

        System.out.println("HeatMapPlacerTactic::placeShips");
        System.out.println(memory);
        System.out.println(version);

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
    }
}
