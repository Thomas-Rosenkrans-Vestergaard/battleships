package r1.heatmap;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import r1.Area;
import battleship.interfaces.Position;
import r1.PositionedArea;

public class HeatMapView {

    private final HeatMap heatMap;

    /**
     * The size of the x-axis on the {@link HeatMap}.
     */
    private final int sizeX;

    /**
     * The size of the y-axis on the {@link HeatMap}.
     */
    private final int sizeY;

    /**
     * The internal storage of the {@link HeatMapVersion}.
     */
    private Map<Integer, Integer> map;

    /**
     * Creates a new {@link HeatMapVersion}.
     *
     * @param version The version number of the {@link HeatMapVersion}.
     * @param heatMap The {@link HeatMap} the {@link HeatMapVersion} was created
     * by.
     * @param map The internal storage of the {@link HeatMapVersion}.
     */
    public HeatMapView(HeatMap heatMap, Map<Integer, Integer> map) {
        this.heatMap = heatMap;
        this.sizeX = heatMap.getSizeX();
        this.sizeY = heatMap.getSizeY();
        this.map = map;
    }

    /**
     * Finds the hottest {@link PositionedArea} for the provided
     * <code>search</code> {@link Area}.
     *
     * @param search The {@link Area} to position in the hottest area.
     * @return The hottest {@link PositionedArea} for the provided
     * <code>search</code> {@link Area}.
     */
    public HeatMapArea getHottestArea(Area search) {
        int maxX = 0;
        int maxY = 0;
        double maxAvg = Double.MIN_VALUE;
        for (int xAnchor = 0; xAnchor <= sizeX - search.sizeX; xAnchor++) {
            for (int yAnchor = 0; yAnchor <= sizeY - search.sizeY; yAnchor++) {
                double currentValue = getAverageValue(xAnchor, yAnchor, search.sizeX, search.sizeY);
                if (currentValue > maxAvg) {
                    maxAvg = currentValue;
                    maxX = xAnchor;
                    maxY = yAnchor;
                }
            }
        }

        return new HeatMapArea(maxAvg, search.sizeX, search.sizeY, new Position(maxX, maxY));
    }

    /**
     * Finds the coldest {@link PositionedArea} for the provided
     * <code>search</code> {@link Area}.
     *
     * @param search The {@link Area} to position in the coldest area.
     * @return The coldest {@link PositionedArea} for the provided
     * <code>search</code> {@link Area}.
     */
    public HeatMapArea getColdestArea(Area search) {
        int minX = 0;
        int minY = 0;
        double minAvg = Double.MAX_VALUE;
        for (int xAnchor = 0; xAnchor <= sizeX - search.sizeX; xAnchor++) {
            for (int yAnchor = 0; yAnchor <= sizeY - search.sizeY; yAnchor++) {
                double currentValue = getAverageValue(xAnchor, yAnchor, search.sizeX, search.sizeY);
                if (currentValue < minAvg) {
                    minAvg = currentValue;
                    minX = xAnchor;
                    minY = yAnchor;
                }
            }
        }

        return new HeatMapArea(minAvg, search.sizeX, search.sizeY, new Position(minX, minY));
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
    public HeatMapArea getHottestAreaExcluding(Area search, List<PositionedArea> exclude) {
        int maxX = 0;
        int maxY = 0;
        double maxAvg = Double.MIN_VALUE;
        for (int xAnchor = 0; xAnchor <= sizeX - search.sizeX; xAnchor++) {
            for (int yAnchor = 0; yAnchor <= sizeY - search.sizeY; yAnchor++) {
                PositionedArea possible = new PositionedArea(search.sizeX, search.sizeY, new Position(xAnchor, yAnchor));
                if (!possible.overlaps(exclude)) {
                    double currentValue = getAverageValue(xAnchor, yAnchor, search.sizeX, search.sizeY);
                    if (currentValue > maxAvg) {
                        maxAvg = currentValue;
                        maxX = xAnchor;
                        maxY = yAnchor;
                    }
                }
            }
        }

        return new HeatMapArea(maxAvg, search.sizeX, search.sizeY, new Position(maxX, maxY));
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
    public HeatMapArea getColdestAreaExclude(Area search, List<PositionedArea> exclude) {
        int minX = 0;
        int minY = 0;
        double minAvg = Double.MAX_VALUE;
        for (int xAnchor = 0; xAnchor <= sizeX - search.sizeX; xAnchor++) {
            for (int yAnchor = 0; yAnchor <= sizeY - search.sizeY; yAnchor++) {
                PositionedArea possible = new PositionedArea(search.sizeX, search.sizeY, new Position(xAnchor, yAnchor));
                if (!possible.overlaps(exclude)) {
                    double currentValue = getAverageValue(xAnchor, yAnchor, search.sizeX, search.sizeY);
                    if (currentValue < minAvg) {
                        minAvg = currentValue;
                        minX = xAnchor;
                        minY = yAnchor;
                    }
                }
            }
        }

        return new HeatMapArea(minAvg, search.sizeX, search.sizeY, new Position(minX, minY));
    }

    public Position getHottestPosition() {
        int numberOfPositions = sizeX * sizeY;
        int hottestIndex = -1;
        int hottestValue = Integer.MIN_VALUE;
        for (int currentIndex = 0; currentIndex < numberOfPositions; currentIndex++) {
            int currentValue = map.get(currentIndex);
            if (currentValue > hottestValue) {
                hottestIndex = currentIndex;
                hottestValue = currentValue;
            }
        }

        return new Position(hottestIndex % sizeX, hottestIndex / sizeX);
    }

    /**
     * Returns the hottest {@link Position} in the {@link HeatMapVersion}.
     *
     * @param in The {@link PositionedArea}(s) to search in. Searches the entire
     * {@link HeatMapVersion} if none are provided.
     * @return The hottest {@link Position}.
     */
    public Position getHottestPosition(List<Position> in) {

        int hottestIndex = -1;
        int hottestValue = Integer.MIN_VALUE;
        for (Position currentPosition : in) {
            int currentIndex = currentPosition.y * sizeX + currentPosition.x;
            int currentValue = map.get(currentIndex);
            if (currentValue > hottestValue) {
                hottestIndex = currentIndex;
                hottestValue = currentValue;
            }
        }

        return new Position(hottestIndex % sizeX, hottestIndex / sizeX);
    }

    /**
     * Returns the coldest {@link Position} in the {@link HeatMapVersion}.
     *
     * @param in The {@link PositionedArea}(s) to search in. Searches the entire
     * {@link HeatMapVersion} if none are provided.
     * @return The coldest {@link Position}.
     */
    public Position getColdestPosition() {
        int numberOfPositions = sizeX * sizeY;
        int coldestIndex = -1;
        int coldestValue = Integer.MAX_VALUE;
        for (int currentIndex = 0; currentIndex < numberOfPositions; currentIndex++) {
            int currentValue = map.get(currentIndex);
            if (currentValue < coldestValue) {
                coldestIndex = currentIndex;
                coldestValue = currentValue;
            }
        }

        return new Position(coldestIndex % sizeX, coldestIndex / sizeX);
    }

    /**
     * Returns the coldest {@link Position} in the {@link HeatMapVersion}.
     *
     * @param in The {@link PositionedArea}(s) to search in. Searches the entire
     * {@link HeatMapVersion} if none are provided.
     * @return The coldest {@link Position}.
     */
    public Position getColdestPosition(List<Position> in) {

        int coldestIndex = -1;
        int coldestValue = Integer.MAX_VALUE;
        for (Position currentPosition : in) {
            int currentIndex = currentPosition.y * sizeX + currentPosition.x;
            int currentValue = map.get(currentIndex);
            if (currentValue < coldestValue) {
                coldestIndex = currentIndex;
                coldestValue = currentValue;
            }
        }

        return new Position(coldestIndex % sizeX, coldestIndex / sizeX);
    }

    public Position getHottestExclude(List<Position> excludes) {
        Set<Integer> excludedIndexes = new HashSet<>();
        for (Position exclude : excludes) {
            excludedIndexes.add(sizeX * exclude.y + exclude.x);
        }

        int numberOfPositions = sizeX * sizeY;
        int hottestIndex = -1;
        int hottestValue = Integer.MIN_VALUE;
        for (int currentIndex = 0; currentIndex < numberOfPositions; currentIndex++) {
            if (!excludedIndexes.contains(currentIndex)) {
                int currentValue = map.get(currentIndex);
                if (currentValue > hottestValue) {
                    hottestIndex = currentIndex;
                    hottestValue = currentValue;
                }
            }
        }

        return new Position(hottestIndex % sizeX, hottestIndex / sizeX);
    }

    /**
     * Returns the coldest {@link Position} in the {@link HeatMapVersion}
     * excluding {@link PositionedArea}(s) provided to <code>exclude</code>.
     *
     * @param exclude The {@link PositionedArea}(s) to not seach in.
     * @return The coldest {@link Position}.
     */
    public Position getColdestExclude(List<Position> excludes) {
        Set<Integer> excludedIndexes = new HashSet<>();
        for (Position exclude : excludes) {
            excludedIndexes.add(sizeX * exclude.y + exclude.x);
        }

        int numberOfPositions = sizeX * sizeY;
        int coldestIndex = -1;
        int coldestValue = Integer.MAX_VALUE;
        for (int currentIndex = 0; currentIndex < numberOfPositions; currentIndex++) {
            if (!excludedIndexes.contains(currentIndex)) {
                int currentValue = map.get(currentIndex);
                if (currentValue < coldestValue) {
                    coldestIndex = currentIndex;
                    coldestValue = currentValue;
                }
            }
        }

        return new Position(coldestIndex % sizeX, coldestIndex / sizeX);
    }

    /**
     * Returns the avarage heat of the area bounded by the provided integers.
     *
     * @param positionX The x-component of the anchor of the area.
     * @param positionY The y-component of the ancher of the area.
     * @param areaSizeX The size of the area on the x-axis.
     * @param areaSizeY The size of the area on the y-axis.
     * @return The avarage heat of the area bounded by the provided integers.
     */
    private double getAverageValue(int positionX, int positionY, int areaSizeX, int areaSizeY) {
        int sum = 0;
        int xEnd = positionX + areaSizeX;
        int yEnd = positionY + areaSizeY;
        for (int x = positionX; x < xEnd; x++) {
            for (int y = positionY; y < yEnd; y++) {
                sum += map.get(y * sizeX + x);
            }
        }

        return (double) sum / (areaSizeX * areaSizeY);
    }

    
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = sizeY - 1; y >= 0; y--) {
            for (int x = 0; x < sizeX; x++) {
                builder.append(String.format("%6d", map.get(y * sizeX + x)));
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    public HeatMap getHeatMap() {
        return heatMap;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getValue(Position position) {
        return map.get(sizeX * position.y + position.x);
    }
}
