package r1.shooting.shooter;

import battleship.interfaces.Position;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
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

        List<Position> fireQueue = new ArrayList<>();

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

        Collections.shuffle(fireQueue);
        this.fireQueue = new ArrayDeque<>(fireQueue);
    }
    
    @Override
    public Position getFirePosition() {
        return fireQueue.poll();
//
//        int shootIncrement = 0;
//        try {
//            shootIncrement = memory.getCurrentEnemyFleet().getLongestShip() - 1;
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//        Set<Position> usedPositions = new HashSet<>(memory.getUsedPositions());
//        
//        for (int j = 0; j < 10; j++) {
//            for (int i = 19; i >= 0; i -= shootIncrement) {
//                if ((i - j) >= 0 && (i - j) < 10) {
//                    Position obj = new Position(i - j, j);
//                    
//                    if (usedPositions.contains(obj) && (i - j >= 0) && ((i - j) < 10)) {
//                        usedPositions.remove(obj);
//                        
//                    } else if (!usedPositions.contains(obj)) {
//                        
//                        int nextX = i - j;
//                        
//                        for (int k = 1; k < shootIncrement - 1; k++) {
//                            if (usedPositions.contains(new Position(nextX + shootIncrement - k, j))) {
//                                System.out.println(k);
//                                System.out.println(nextX);
//                                nextX = nextX - k;
//                                System.out.println(nextX);
//                                k = 0;
//                            }
//                        }
//                        if ((nextX) >= 0 && (nextX < 10)) {
//                            obj = new Position(nextX, j);
//                        }
//                    }
//                    
//                    usedPositions.add(obj);
//                }
//            }
//        }
//        
//        List<Position> pos = new ArrayList<>(usedPositions);
//        Collections.shuffle(pos);
//        return pos.get(0);
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
