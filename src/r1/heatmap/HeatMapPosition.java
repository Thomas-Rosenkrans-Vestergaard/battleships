package r1.heatmap;

import r1.Position;

public class HeatMapPosition extends Position {

    private int heatValue;

    public HeatMapPosition(int x, int y, int heatValue) {
        super(x, y);
        this.heatValue = heatValue;
    }

    public int getHeatValue() {
        return heatValue;
    }
}
