package r1.heatmap;

import r1.Position;
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
