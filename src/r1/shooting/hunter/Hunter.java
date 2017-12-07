package r1.shooting.hunter;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.Queue;
import r1.shooting.ShooterComponent;
import r1.shooting.ShotFeedBack;

public interface Hunter {

    public enum State {
        ACTIVE,
        FINISHED,
    }

    public Queue<Position> getFireQueue();

    public void hitFeedBack(ShotFeedBack feedback);

    public void startRound(int round);

    public void endRound(int round, int points, int enemyPoints);

    public ShooterComponent getShooterComponent();
}
