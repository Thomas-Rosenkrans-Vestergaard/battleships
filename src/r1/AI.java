/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package r1;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Board;
import battleship.interfaces.Ship;
import java.util.Arrays;
import java.util.Random;
import r1.placement.Placer;
import r1.shooting.Shooter;

/**
 *
 * @author Tobias
 */
public class AI implements BattleshipsPlayer {

    private Placer placer;
    private Shooter shooter;

    /**
     * Creates a new {@link AI}.
     */
    public AI(Placer placer, Shooter shooter) {
        this.placer = placer;
        this.shooter = shooter;
    }

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
        placer.startMatch(rounds, ships, sizeX, sizeY);
        shooter.startMatch(rounds, ships, sizeX, sizeY);
    }

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
    @Override
    public void placeShips(Fleet fleet, Board board) {
        placer.placeShips(fleet, board);
    }

    /**
     * Called every time the enemy has fired a shot.
     *
     * The purpose of this method is to allow the AI to react to the enemy's
     * incoming fire and place his/her ships differently next round.
     *
     * @param pos Position of the enemy's shot
     */
    @Override
    public void incoming(Position pos) {
        placer.incoming(pos);
    }

    /**
     * Called by the Game application to get the Position of your shot.
     * hitFeedBack(...) is called right after this method.
     *
     * @param enemyShips Fleet the enemy's ships. Compare this to the Fleet
     * supplied in the hitFeedBack(...) method to see if you have sunk any
     * ships.
     *
     * @return Position of you next shot.
     */
    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        return shooter.getFireCoordinates(enemyShips);
    }

    /**
     * Called right after getFireCoordinates(...) to let your AI know if you hit
     * something or not.
     *
     * Compare the number of ships in the enemyShips with that given in
     * getFireCoordinates in order to see if you sunk a ship.
     *
     * @param hit boolean is true if your last shot hit a ship. False otherwise.
     * @param enemyShips Fleet the enemy's ships.
     */
    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        shooter.hitFeedBack(hit, enemyShips);
    }

    /**
     * Called at the beginning of each round.
     *
     * @param round int the current round number.
     */
    @Override
    public void startRound(int round) {
        placer.startRound(round);
        shooter.startRound(round);
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
        placer.endRound(round, points, enemyPoints);
        shooter.endRound(round, points, enemyPoints);
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
        placer.endMatch(won, lost, draw);
        shooter.endMatch(won, lost, draw);
    }
}
