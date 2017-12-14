package r1.shooting.shooter;

import battleship.interfaces.Position;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import r1.heatmap.HeatMap;
import r1.heatmap.HeatMapView;
import r1.shooting.ShooterComponent;
import r1.shooting.ShooterComponentMemory;
import r1.shooting.ShotFeedback;

public class DiagonalsShooter implements Shooter {

    private final ShooterComponent shooterComponent;
    private final ShooterComponentMemory memory;
    private Queue<Position> fireQueue;

    public DiagonalsShooter(ShooterComponent component, ShooterComponentMemory memory) {
        this.shooterComponent = component;
        this.memory = memory;
    }

    @Override
    public boolean canFire() {
        return true;
    }

    @Override
    public void startRound(int round) {

        this.fireQueue = new ArrayDeque<>();

        for (int i = 4; i > 0; i -= 2) {
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
    public Position getFirePosition() {
        try {
            HeatMapView view = this.memory.getDensityView();
            System.out.println(view);
            return view.getHottestPosition();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    @Override
    public void onFeedBack(ShotFeedback feedback) {
        System.out.println("DiagonalsShooter:onFeedBack=");
        System.out.println("currentFeedBack=" + feedback);
        if (feedback.wasHit() && !feedback.sunkShip()) {
            Shooter hunter = new Hunter(this.shooterComponent, memory, feedback.getPosition());
            shooterComponent.pushShooter(hunter);
        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {

    }

    @Override
    public void onSecondaryFeedBack(ShotFeedback feedback) {

    }

    public ShooterComponentMemory getMemory() {
        return memory;
    }

    public ShooterComponent getShooterComponent() {
        return shooterComponent;
    }
}
