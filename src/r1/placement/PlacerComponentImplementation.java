package r1.placement;

import r1.placement.placer.HeatMapPlacer;
import r1.placement.placer.RandomPlacer;
import r1.placement.placer.Placer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import r1.heatmap.HeatMap;
import r1.heatmap.UnknownHeatMapVersionException;

public class PlacerComponentImplementation implements PlacerComponent {

    /**
     * The memory shared by all {@link Placer} instances.
     */
    private PlacerComponentMemory memory;
    
    /**
     * A {@link Placer} that takes into account the shooting pattern of the enemy.
     */
    private Placer heatMapPlacerTactic;
    
    /**
     * A {@link Placer} that places ships at random.
     */
    private Placer randomPlacerTactic;

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        HeatMap incomingHeatMap = new HeatMap(sizeX, sizeY);
        incomingHeatMap.makeVersion(true);
        int numberOfHeatMaps = (int) Math.min(1, (5 * Math.pow(2, Math.log10((double) rounds) - 1)));
        this.memory = new PlacerComponentMemory(incomingHeatMap, rounds, numberOfHeatMaps, sizeX, sizeY);
        this.heatMapPlacerTactic = new HeatMapPlacer(this.memory);
        this.randomPlacerTactic = new RandomPlacer(this.memory);
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
