package r1.placement;

import battleship.interfaces.Position;
import r1.heatmap.HeatMap;
import r1.heatmap.HeatMapOutOfBoundsException;
import r1.heatmap.HeatMapVersion;
import r1.heatmap.NoActiveHeatMapVersionException;

public class PlacerComponentMemory {

    /**
     * The {@link HeatMap} watching the incomming fire.
     */
    private final HeatMap incomingHeatMap;

    /**
     * The number of rounds to be played.
     */
    private final int numberOfRounds;

    /**
     * The number of heat maps generated during a match.
     */
    private final int numberOfHeatMaps;

    /**
     * The size of the board on the x-axis.
     */
    private final int sizeX;

    /**
     * The size of the board on the y-axis.
     */
    private final int sizeY;

    /**
     * The current round number.
     */
    private int currentRound;

    /**
     * The number of shots still to be fired from the opponent.
     */
    private int remainingEnemyShots;

    /**
     * The version number of the {@link HeatMapVersion} currently in use.
     */
    private int currentHeatMapVersion = 0;

    public PlacerComponentMemory(HeatMap incomingHeatMap, int numberOfRounds, int numberOfHeatMaps, int sizeX, int sizeY) {
        this.incomingHeatMap = incomingHeatMap;
        this.numberOfRounds = numberOfRounds;
        this.numberOfHeatMaps = numberOfHeatMaps;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
    
    /**
     * Called at the beginning of each round.
     *
     * @param round int the current round number.
     */
    public void startRound(int round) {
        this.remainingEnemyShots = sizeX * sizeY;
        this.currentRound = round;
        if ((round - 1) % Math.max(1, numberOfRounds / numberOfHeatMaps) == 0) {
            this.currentHeatMapVersion = incomingHeatMap.makeVersion(true);
        }
    }

    /**
     * Called every time the enemy has fired a shot.
     *
     * The purpose of this method is to allow the AI to react to the enemy's
     * incoming fire and place his/her ships differently next round.
     *
     * @param pos Position of the enemy's shot
     */
    public void incoming(Position pos) {
        System.out.println("incomming " + pos);
        System.out.println(this);
        try {
            incomingHeatMap.addToValue(pos, remainingEnemyShots--);
        } catch (NoActiveHeatMapVersionException | HeatMapOutOfBoundsException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Called at the end of each round to let you know if you won or lost.
     * Compare your points with the enemy's to see who won.
     *
     * @param round int current round number.
     * @param points your points this round: 100 - number of shot used to sink
     * all of the enemy's ships.
     *
     * @param enemyPoints int enemy's points this round.
     */
    public void endRound(int round, int points, int enemyPoints) {
        System.out.println("endRound");
        System.out.println(this);
    }

    public HeatMap getIncomingHeatMap() {
        return incomingHeatMap;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public int getNumberOfHeatMaps() {
        return numberOfHeatMaps;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getRemainingNumberOfShots() {
        return remainingEnemyShots;
    }

    @Override
    public String toString() {
        return "PlacerMemory{" + "incomingHeatMap=" + incomingHeatMap + ", numberOfRounds=" + numberOfRounds + ", numberOfHeatMaps=" + numberOfHeatMaps + ", sizeX=" + sizeX + ", sizeY=" + sizeY + ", currentRound=" + currentRound + ", remainingEnemyShots=" + remainingEnemyShots + ", currentHeatMapVersion=" + currentHeatMapVersion + '}';
    }

}
