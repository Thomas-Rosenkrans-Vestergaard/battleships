package r1.shooting.shooter;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.Queue;

public interface Shooter {

    public Queue<Position> getFireQueue();

    
    
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
    public void hitFeedBack(boolean hit, Fleet enemyShips);

    /**
     * Called when any {@link Shooter}s fire on the provided {@link Position}.
     *
     * @param position
     */
    void onFire(Position position);
    
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
}
