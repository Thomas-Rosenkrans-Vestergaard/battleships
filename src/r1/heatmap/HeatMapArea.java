package r1.heatmap;

import battleship.interfaces.Position;
import r1.PositionedArea;

public class HeatMapArea extends PositionedArea {

    private double averageHeat;

    public HeatMapArea(double average, int sizeX, int sizeY, Position position) {
        super(sizeX, sizeY, position);

        this.averageHeat = average;
    }

    public double getAverageHeat() {
        return averageHeat;
    }
}
