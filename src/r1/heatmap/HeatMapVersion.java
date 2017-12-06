package r1.heatmap;

import r1.Position;
import java.util.HashMap;
import java.util.Map;
import r1.Area;
import r1.PositionedArea;

public class HeatMapVersion extends HeatMapView {

    /**
     * The version number of the {@link HeatMapVersion}.
     */
    private final int version;

    public HeatMapVersion(int version, HeatMap heatMap, Map<Integer, Integer> map) {
        super(heatMap, map);
        this.version = version;
    }

    public int getVersion() {
        return version;
    }
}
