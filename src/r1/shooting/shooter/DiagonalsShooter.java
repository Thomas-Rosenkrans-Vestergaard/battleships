package r1.shooting.shooter;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import r1.shooting.ShooterComponent;
import r1.shooting.ShooterComponentMemory;
import r1.shooting.ShotFeedBack;
import r1.shooting.hunter.DefaultHunter;
import r1.shooting.hunter.Hunter;
import r1.shooting.hunter.HunterReport;

public class DiagonalsShooter implements Shooter {

    private ShooterComponent shooterComponent;
    private ShooterComponentMemory memory;
    private Queue<Position> fireQueue;

    public DiagonalsShooter(ShooterComponent component, ShooterComponentMemory memory) {
        this.shooterComponent = component;
        this.memory = memory;
    }

    @Override
    public Queue<Position> getFireQueue() {
        return fireQueue;
    }

    @Override
    public void startRound(int round) {

        fireQueue = new ArrayDeque<Position>();

        for (int i = 4; i > 0; i--) {
            for (int x = 0; x < memory.sizeX; x++) {
                for (int y = 0; y < memory.sizeY; y++) {
                    if ((x - y) % i == 0) {
                        Position position = new Position(x, y);
                        if (!fireQueue.contains(position)) {
                            fireQueue.add(position);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

    }

    @Override
    public void hitFeedBack(ShotFeedBack feedBack) {
        if (feedBack.wasHit()) {
            Hunter hunter = new DefaultHunter(this, feedBack.getPosition());
            shooterComponent.onHunterActivated(hunter);
        }
    }

    @Override
    public void onFire(Position position) {

    }

    public ShooterComponentMemory getMemory() {
        return memory;
    }

    public ShooterComponent getShooterComponent() {
        return shooterComponent;
    }
}
