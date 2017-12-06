package r1.heatmap;

import battleship.interfaces.Position;
import java.util.HashMap;
import r1.Area;
import r1.PositionedArea;

public class HeatMapVersion extends HashMap<Integer, Integer> {

    /**
     * The version number of the {@link HeatMapVersion}.
     */
    private int version;

    /**
     * The {@link HeatMap} the {@link HeatMapVersion} was created by.
     */
    private HeatMap heatMap;

    /**
     * Creates a new {@link HeatMapVersion}.
     *
     * @param version The version number of the {@link HeatMapVersion}.
     * @param heatMap The {@link HeatMap} the {@link HeatMapVersion} was created
     * by.
     */
    public HeatMapVersion(int version, HeatMap heatMap) {
        this.version = version;
        this.heatMap = heatMap;
    }

    /**
     * Finds the hottest {@link PositionedArea} for the provided
     * <code>search</code> {@link Area}.
     *
     * @param search The {@link Area} to position in the hottest area.
     * @return The hottest {@link PositionedArea} for the provided
     * <code>search</code> {@link Area}.
     */
    public PositionedArea getHottestArea(Area search) {
        return null;
    }

    /**
     * Finds the coldest {@link PositionedArea} for the provided
     * <code>search</code> {@link Area}.
     *
     * @param search The {@link Area} to position in the coldest area.
     * @return The coldest {@link PositionedArea} for the provided
     * <code>search</code> {@link Area}.
     */
    public PositionedArea getColdestArea(Area search) {
        return null;
    }

    /**
     * Finds the hottest {@link PositionedArea} for the provided
     * <code>search</code> {@link Area} excluding any provided
     * <code>exclude</code> {@link PositionedArea}s.
     *
     * @param search The {@link Area} to position in the hottest area.
     * @param exclude The {@link PositionedArea}(s) to exclude in the search.
     * @return The hottest {@link PositionedArea} for the provided
     * <code>search</code> {@link Area}.
     */
    public PositionedArea getHottestAreaExcluding(Area search, PositionedArea... exclude) {
        return null;
    }

    /**
     * Finds the coldest {@link PositionedArea} for the provided
     * <code>search</code> {@link Area} excluding any provided
     * <code>exclude</code> {@link PositionedArea}s.
     *
     * @param search The {@link Area} to position in the coldest area.
     * @param exclude The {@link PositionedArea}(s) to exclude in the search.
     * @return The coldest {@link PositionedArea} for the provided
     * <code>search</code> {@link Area}.
     */
    public PositionedArea getColdestAreaExclude(Area search, PositionedArea... exclude) {
        return null;
    }

    /**
     * Returns the hottest {@link Position} in the {@link HeatMapVersion}.
     *
     * @param in The {@link PositionedArea}(s) to search in. Searches the entire
     * {@link HeatMapVersion} if none are provided.
     * @return The hottest {@link Position}.
     */
    public Position getHottestPosition(PositionedArea... in) {
        return null;
    }

    /**
     * Returns the coldest {@link Position} in the {@link HeatMapVersion}.
     *
     * @param in The {@link PositionedArea}(s) to search in. Searches the entire
     * {@link HeatMapVersion} if none are provided.
     * @return The coldest {@link Position}.
     */
    public Position getColdestPosition(PositionedArea... in) {
        return null;
    }

    /**
     * Returns the hottest {@link Position} in the {@link HeatMapVersion}
     * excluding {@link PositionedArea}(s) provided to <code>exclude</code>.
     *
     * @param exclude The {@link PositionedArea}(s) to not seach in.
     * @return The hottest {@link Position}.
     */
    public Position getHottestPositionExclude(PositionedArea... exclude) {
        return null;
    }

    /**
     * Returns the coldest {@link Position} in the {@link HeatMapVersion}
     * excluding {@link PositionedArea}(s) provided to <code>exclude</code>.
     *
     * @param exclude The {@link PositionedArea}(s) to not seach in.
     * @return The coldest {@link Position}.
     */
    public Position getColdestExclude(PositionedArea... exclude) {
        return null;
    }
}
