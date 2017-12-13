package r1.shooting.shooter;

import battleship.interfaces.Position;
import r1.shooting.ShooterComponent;
import r1.shooting.ShotFeedback;

public interface Shooter {

    /**
     * Returns the parent {@link ShooterComponent}.
     *
     * @return The parent {@link ShooterComponent}.
     */
    public ShooterComponent getShooterComponent();

    public boolean canFire();
    
    /**
     * Returns the {@link Position}s to fire on.
     *
     * @return The {@link Position}s to fire on.
     */
    public Position getFirePosition();

    /**
     * Provides the {@link Shooter} with {@link ShotFeedback} on its last fired
     * shot.
     *
     * @param feedback The object carrying information on the shot.
     */
    public void onFeedBack(ShotFeedback feedback);

    /**
     * Called when other {@link Shooter}s from the same {@link ShooterComponent}
     * fires a shot.
     *
     * @param feedback The object carrying information on the shot.
     */
    void onSecondaryFeedBack(ShotFeedback feedback);

    /**
     * Called at the beginning of each round.
     *
     * @param round int the current round number.
     */
    public void startRound(int round);

    /**
     * Called at the end of each round.
     *
     * @param round int current round number.
     * @param points your points this round: 100 - number of shot used to sink
     * all of the enemy's ships.
     *
     * @param enemyPoints int enemy's points this round.
     */
    public void endRound(int round, int points, int enemyPoints);
}
