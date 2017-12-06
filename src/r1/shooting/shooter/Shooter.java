package r1.shooting.shooter;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;

public interface Shooter {

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
    public Position getFireCoordinates(Fleet enemyShips);

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
}
