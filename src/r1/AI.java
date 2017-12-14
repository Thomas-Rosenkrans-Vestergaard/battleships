package r1;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Board;
import r1.heatmap.HeatMap;
import r1.placement.PlacerComponent;
import r1.placement.PlacerComponentImplementation;
import r1.placement.PlacerComponentMemory;
import r1.shooting.ShooterComponent;
import r1.shooting.ShooterComponentImplementation;
import r1.shooting.ShooterComponentMemory;

public class AI implements BattleshipsPlayer {

    private PlacerComponent placerComponent;
    private ShooterComponent shooterComponent;

    /**
     * Called in the beginning of each match to inform about the number of
     * rounds being played.
     *
     * @param rounds The number of rounds played in a match.
     * @param ships The ships in each round.
     * @param sizeX The horizontal size of the board.
     * @param sizeY The vertical size of the board.
     */
    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        try {
            this.placerComponent = buildPlacerComponent(rounds, ships, sizeX, sizeY);
            this.shooterComponent = buildShooterComponent(rounds, ships, sizeX, sizeY);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        try {
            placerComponent.placeShips(fleet, board);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void incoming(Position pos) {
        try {
            placerComponent.incoming(pos);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        try {
            return shooterComponent.getFireCoordinates(enemyShips);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        try {
            shooterComponent.hitFeedBack(hit, enemyShips);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Called at the beginning of each round.
     *
     * @param round int the current round number.
     */
    @Override
    public void startRound(int round) {
        try {
            System.out.println("startRound:" + round);
            System.out.println("placer=" + placerComponent);
            System.out.println("shootercomponent=" + shooterComponent);
            placerComponent.startRound(round);
            shooterComponent.startRound(round);
        } catch (Exception e) {
            System.out.println("E");
            e.printStackTrace();
            System.exit(0);
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
    @Override
    public void endRound(int round, int points, int enemyPoints) {
        try {
            placerComponent.endRound(round, points, enemyPoints);
            shooterComponent.endRound(round, points, enemyPoints);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Called at the end of a match (that usually last 1000 rounds) to let you
     * know how many losses, victories and draws you scored.
     *
     * @param won int the number of victories in this match.
     * @param lost int the number of losses in this match.
     * @param draw int the number of draws in this match.
     */
    @Override
    public void endMatch(int won, int lost, int draw) {
        try {
            placerComponent.endMatch(won, lost, draw);
            shooterComponent.endMatch(won, lost, draw);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private PlacerComponent buildPlacerComponent(int rounds, Fleet ships, int sizeX, int sizeY) {
        HeatMap incomingHeatMap = new HeatMap(sizeX, sizeY);
        incomingHeatMap.makeVersion(true);
        int numberOfHeatMaps = (int) Math.max(1, (5 * Math.pow(2, Math.log10(rounds) - 1)));
        PlacerComponentMemory placerComponentMemory = new PlacerComponentMemory(incomingHeatMap, rounds, numberOfHeatMaps, sizeX, sizeY);
        return new PlacerComponentImplementation(placerComponentMemory);
    }

    private ShooterComponent buildShooterComponent(int rounds, Fleet ships, int sizeX, int sizeY) {
        ShooterComponentMemory shooterComponentMemory = new ShooterComponentMemory(rounds, ships, sizeX, sizeY);
        return new ShooterComponentImplementation(shooterComponentMemory);
    }
}
