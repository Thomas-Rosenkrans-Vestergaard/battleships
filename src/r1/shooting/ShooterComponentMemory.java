package r1.shooting;

import battleship.interfaces.Position;
import java.util.HashSet;
import java.util.Set;

public class ShooterComponentMemory {

    public final int sizeX;
    public final int sizeY;
    private Set<Position> usedPositions = new HashSet<>();

    public ShooterComponentMemory(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    
    
    public boolean hasBeenFiredAt(Position position) {
        return usedPositions.contains(position);
    }

    public void onFire(Position position) {
        usedPositions.add(position);
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
    
    
}
