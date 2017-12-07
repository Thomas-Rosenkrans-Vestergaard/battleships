package r1.placement;

import r1.placement.placer.HeatMapPlacer;
import r1.placement.placer.RandomPlacer;
import r1.placement.placer.Placer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;

public class PlacerComponentImplementation implements PlacerComponent {

    /**
     * The memory shared by all {@link Placer} instances.
     */
    private PlacerComponentMemory memory;

    /**
     * A {@link Placer} that takes into account the shooting pattern of the
     * enemy.
     */
    private final Placer heatMapPlacerTactic;

    /**
     * A {@link Placer} that places ships at random.
     */
    private final Placer randomPlacerTactic;

    /**
     * Creates a new {@link PlacerComponentImplementation}.
     *
     * @param memory The memory used by the
     * {@link PlacerComponentImplementation} and child {@link Placer} instances.
     */
    public PlacerComponentImplementation(PlacerComponentMemory memory) {
        this.memory = memory;
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

        System.out.println("placeShips");
        System.out.println("currentRound="+currentRound);
        System.out.println("numberOfRounds="+numberOfRounds);
        System.out.println("numberOfHeatMaps="+numberOfHeatMaps);
        
        if (currentRound <= (numberOfRounds / numberOfHeatMaps)) {
            randomPlacerTactic.placeShips(fleet, board);
            return;
        }

        heatMapPlacerTactic.placeShips(fleet, board);
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

    }
}
