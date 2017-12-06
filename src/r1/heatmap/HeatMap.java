package r1.heatmap;

import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeatMap {

    /**
     * The horisontal size of the {@link HeatMap}.
     */
    private final int sizeX;

    /**
     * The vertical size of the {@link HeatMap}.
     */
    private final int sizeY;

    /**
     * The default value of the values in the {@link HeatMap}.
     */
    private final int defaultValue;

    /**
     * The amount incremented when the {@link HeatMap#put(r1.Position)} method
     * is called.
     */
    private final int increment;

    /**
     * The {@link HeatMapVersion} iterations of the {@link HeatMap}.
     */
    private List<HeatMapVersion> versions;

    /**
     * The internal storage.
     */
    private List<HashMap<Integer, Integer>> maps;

    /**
     * The map currently being edited.
     */
    private int currentVersion = -1;

    /**
     * Creates a new {@link HeatMap}. The default value of the positions in the
     * {@link HeatMap} are set the zero. The increment value is set to one.
     *
     * @param sizeX The horisontal size of the {@link HeatMap}.
     * @param sizeY The vertical size of the {@link HeatMap}.
     */
    public HeatMap(int sizeX, int sizeY) {
        this(sizeX, sizeY, 0, 1);
    }

    /**
     * Creates a new {@link HeatMap}. The increment value is set to one.
     *
     * @param sizeX The horisontal size of the {@link HeatMap}.
     * @param sizeY The vertical size of the {@link HeatMap}.
     * @param defaultValue The default values of the positions in the
     * {@link HeatMap}.
     */
    public HeatMap(int sizeX, int sizeY, int defaultValue) {
        this(sizeX, sizeY, defaultValue, 1);
    }

    /**
     * Creates a new {@link HeatMap}.
     *
     * @param sizeX The horisontal size of the {@link HeatMap}.
     * @param sizeY The vertical size of the {@link HeatMap}.
     * @param defaultValue The default values of the positions in the
     * {@link HeatMap}.
     * @param increment The amount incremented when the
     * {@link HeatMap#put(r1.Position)} method is called.
     */
    public HeatMap(int sizeX, int sizeY, int defaultValue, int increment) {

        if (sizeX < 1) {
            throw new IllegalArgumentException("sizeX cannot be less than 1.");
        }

        if (sizeY < 1) {
            throw new IllegalArgumentException("sizeY cannot be less than 1.");
        }

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.defaultValue = defaultValue;
        this.increment = increment;
        this.versions = new ArrayList<>();
    }

    /**
     * Creates a new version of the {@link HeatMap}.
     *
     * @return The index identifying the new version of the {@link HeatMap}. The
     * version indices are zero-indexed.
     */
    public int makeVersion() {
        this.currentVersion++;
        versions.add(createVersion());
        return this.currentVersion;
    }

    public void setCurrentVersion(int versionNumber) throws UnknownHeatMapVersionException {
        if (versions.get(versionNumber) == null) {
            throw new UnknownHeatMapVersionException(versionNumber);
        }

        this.currentVersion = versionNumber;
    }

    public int getCurrentVersion() {
        return currentVersion;
    }

    public HeatMapVersion getVersion(int versionNumber) throws UnknownHeatMapVersionException {
        HeatMapVersion version = versions.get(versionNumber);
        if (version == null) {
            throw new UnknownHeatMapVersionException(versionNumber);
        }

        return version;
    }

    /**
     * Merges and returns all the versions of the of the {@link HeatMap}.
     *
     * @return The merged versions of the {@link HeatMap}.
     */
    public Map<Integer, Integer> get() {
        return mergeMaps(0, versions.size());
    }

    /**
     * Returns the {@link HeatMap} with the provided version number.
     *
     * @param version The version number. The version number is zero-indexed.
     * @return The {@link HeatMap}.
     */
    public Map<Integer, Integer> get(int version) {
        return versions.get(version);
    }

    /**
     * Merges and returns all the versions between <code>from</code> and
     * <code>to</code>.
     *
     * @param from The version to start at. The version number is zero indexed.
     * @param to The version to end at (not inclusive). The version number is
     * zero indexed.
     * @return The merged {@link HeatMap}. The combined maps from
     * <code>from</code> and up to (but not including) <code>to</code>.
     */
    public Map<Integer, Integer> get(int from, int to) {
        return mergeMaps(from, to);
    }

    /**
     * Returns the first version of the {@link HeatMap}.
     *
     * @return The first version of the {@link HeatMap}.
     */
    public Map<Integer, Integer> getFirst() {
        return versions.get(0);
    }

    /**
     * Merges and returns the first <code>n</code> versions of the
     * {@link HeatMap}.
     *
     * @param n The number of versions to merge and return.
     * @return The merged versions.
     */
    public Map<Integer, Integer> getFirst(int n) {
        return mergeMaps(0, n);
    }

    /**
     * Returns the last version of the {@link HeatMap}.
     *
     * @return The last version of the {@link HeatMap}.
     */
    public Map<Integer, Integer> getLast() {
        return versions.get(currentVersion);
    }

    /**
     * Merges and returns the last <code>n</code> versions of the
     * {@link HeatMap}.
     *
     * @param n The number of versions to merge and return.
     * @return The merged versions.
     */
    public Map<Integer, Integer> getLast(int n) {
        return mergeMaps(currentVersion, currentVersion + 1);
    }

    /**
     * Merges and returns all the versions between <code>from</code> and
     * <code>to</code>.
     *
     * @param from The version to start at. The version number is zero indexed.
     * @param to The version to end at (not inclusive). The version number is
     * zero indexed.
     * @return The merged {@link HeatMap}. The combined maps from
     * <code>from</code> and up to (but not including) <code>to</code>.
     */
    private Map<Integer, Integer> mergeMaps(int from, int to) {
        Map<Integer, Integer> result = new HashMap<>();
        for (int x = from; x < versions.size() && x < to - 1; x++) {
            for (int index = 0; index < sizeX * sizeY; index++) {
                result.put(index, result.getOrDefault(index, 0) + versions.get(x).get(index));
            }
        }

        return result;
    }

    /**
     * Creates a new map filled with the default value of the {@link HeatMap}.
     *
     * @return The new map.
     */
    private Map<Integer, Integer> createVersion() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < sizeX * sizeY; i++) {
            map.put(i, defaultValue);
        }

        return map;
    }

    /**
     * Increments the current version of the {@link HeatMap} at the provided
     * position.
     *
     * @param position The position
     * @throws HeatMapOutOfBoundsException
     */
    public void increment(Position position) throws HeatMapOutOfBoundsException {
        int index = position.y * sizeX + position.x;
        Map<Integer, Integer> currentMap = versions.get(currentVersion);
        if (currentMap == null) {
            throw new HeatMapOutOfBoundsException();
        }
        currentMap.put(index, currentMap.get(index) + increment);
    }

    /**
     * Returns an index using the provided coordinates.
     *
     * @param x The x component.
     * @param y The y component.
     * @return The index.
     */
    public int toIndex(int x, int y) {
        return y * sizeX + x;
    }

    /**
     * Returns the horisontal size of the {@link HeatMap}.
     *
     * @return The horisontal size of the {@link HeatMap}.
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * Returns the vertical size of the {@link HeatMap}.
     *
     * @return The vertical size of the {@link HeatMap}.
     */
    public int getSizeY() {
        return sizeY;
    }
}
