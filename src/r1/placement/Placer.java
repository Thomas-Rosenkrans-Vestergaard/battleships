package r1.placement;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;

public interface Placer {

    /**
     * Called in the beginning of each match to inform about the number of
     * rounds being played.
     *
     * @param rounds The number of rounds played in a match.
     * @param ships The ships in each round.
     * @param sizeX The horizontal size of the board.
     * @param sizeY The vertical size of the board.
     */
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY);

    /**
     * The method called when its time for the AI to place ships on the board
     * (at the beginning of each round).
     *
     * The Ship object to be placed MUST be taken from the Fleet given (do not
     * create your own Ship objects!).
     *
     * A ship is placed by calling the board.placeShip(..., Ship ship, ...) for
     * each ship in the fleet (see board interface for details on placeShip()).
     *
     * A player is not required to place all the ships. Ships placed outside the
     * board or on top of each other are wrecked.
     *
     * @param fleet Fleet all the ships that a player should place.
     * @param board Board the board were the ships must be placed.
     */
    public void placeShips(Fleet fleet, Board board);

    /**
     * Called every time the enemy has fired a shot.
     *
     * The purpose of this method is to allow the AI to react to the enemy's
     * incoming fire and place his/her ships differently next round.
     *
     * @param pos Position of the enemy's shot
     */
    public void incoming(Position pos);

    /**
     * Called at the beginning of each round.
     *
     * @param round int the current round number.
     */
    public void startRound(int round);

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
    public void endRound(int round, int points, int enemyPoints);

    /**
     * Called at the end of a match (that usually last 1000 rounds) to let you
     * know how many losses, victories and draws you scored.
     *
     * @param won int the number of victories in this match.
     * @param lost int the number of losses in this match.
     * @param draw int the number of draws in this match.
     */
    public void endMatch(int won, int lost, int draw);
}
