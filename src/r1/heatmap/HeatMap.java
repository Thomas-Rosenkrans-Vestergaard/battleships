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
    
    private final int numberOfPositions;

    /**
     * The default value of the values in the {@link HeatMap}.
     */
    private final int defaultValue;

    /**
     * The {@link HeatMapVersion} iterations of the {@link HeatMap}.
     */
    private List<HeatMapVersion> versions;

    /**
     * The internal storage.
     */
    private List<Map<Integer, Integer>> maps;

    /**
     * The map currently being edited.
     */
    private int activeVersion = -1;

    /**
     * Creates a new {@link HeatMap}. The default value of the positions in the
     * {@link HeatMap} are set the zero. The increment value is set to one.
     *
     * @param sizeX The horisontal size of the {@link HeatMap}.
     * @param sizeY The vertical size of the {@link HeatMap}.
     */
    public HeatMap(int sizeX, int sizeY) {
        this(sizeX, sizeY, 0);
    }

    /**
     * Creates a new {@link HeatMap}.
     *
     * @param sizeX The horisontal size of the {@link HeatMap}.
     * @param sizeY The vertical size of the {@link HeatMap}.
     * @param defaultValue The default values of the positions in the
     * {@link HeatMap}.
     * @param changeValue The amount incremented when the
     * {@link HeatMap#put(r1.Position)} method is called.
     */
    public HeatMap(int sizeX, int sizeY, int defaultValue) {

        if (sizeX < 1) {
            throw new IllegalArgumentException("sizeX cannot be less than 1.");
        }

        if (sizeY < 1) {
            throw new IllegalArgumentException("sizeY cannot be less than 1.");
        }

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numberOfPositions = sizeX * sizeY;
        this.defaultValue = defaultValue;
        this.versions = new ArrayList<>();
        this.maps = new ArrayList<>();
    }

    /**
     * Creates a new version of the {@link HeatMap}.
     *
     * @param active Whether or not to set the new version to active.
     * @return The index identifying the new version of the {@link HeatMap}. The
     * version indices are zero-indexed.
     */
    public int makeVersion(boolean active) {

        int newVersionNumber = createVersion();
        if (active) {
            this.activeVersion = newVersionNumber;
        }

        return newVersionNumber;
    }

    /**
     * Sets the current version using the provided version number.
     *
     * @param versionNumber The version number to set as the current version.
     * @throws UnknownHeatMapVersionException When the provided
     * <code>versionNumber</code> doesn't match a {@link HeatMapVersion} in the
     * {@link HeatMap}.
     */
    public void setActiveVersion(int versionNumber) throws UnknownHeatMapVersionException {
        if (versions.get(versionNumber) == null) {
            throw new UnknownHeatMapVersionException(versionNumber);
        }

        this.activeVersion = versionNumber;
    }

    /**
     * Returns the active version number.
     *
     * @return The active version number.
     * @throws NoActiveHeatMapVersionException When no active version has been
     * set.
     */
    public int getActiveVersionNumber() throws NoActiveHeatMapVersionException {
        if (activeVersion == -1) {
            throw new NoActiveHeatMapVersionException();
        }
        return activeVersion;
    }

    /**
     * Returns the active {@link HeatMapVersion}.
     *
     * @return The active {@link HeatMapVersion}.
     * @throws NoActiveHeatMapVersionException When no {@link HeatMapVersion}
     * has been set to active.
     */
    public HeatMapVersion getActiveVersion() throws NoActiveHeatMapVersionException {
        if (activeVersion == -1) {
            throw new NoActiveHeatMapVersionException();
        }

        return versions.get(activeVersion);
    }

    /**
     * Returns the {@link HeatMapVersion} with the provided
     * <code>versionNumber</code>.
     *
     * @param versionNumber The <code>versionNumber</code> of the
     * {@link HeatMapVersion} to return.
     * @return The {@link HeatMapVersion} with the provided
     * <code>versionNumber</code>.
     * @throws UnknownHeatMapVersionException When a {@link HeatMapVersion} with
     * the provided <code>versionNumber</code> doesn't exist in the
     * {@link HeatMap}.
     */
    public HeatMapVersion getVersion(int versionNumber) throws UnknownHeatMapVersionException {

        if (versionNumber < 0 || versionNumber >= versions.size()) {
            throw new UnknownHeatMapVersionException(versionNumber);
        }

        return versions.get(versionNumber);
    }

    /**
     * Returns the first {@link HeatMapVersion} in the {@link HeatMap}.
     *
     * @return The first {@link HeatMapVersion} in the {@link HeatMap}.
     */
    public HeatMapVersion getFirstVersion() {
        return versions.get(0);
    }

    /**
     * Returns the last {@link HeatMapVersion} in the {@link HeatMap}.
     *
     * @return The last {@link HeatMapVersion} in the {@link HeatMap}.
     */
    public HeatMapVersion getLastVersion() {
        return versions.get(versions.size() - 1);
    }

    /**
     * Merges and returns all the {@link HeatMapVersion}s in the
     * {@link HeatMap}.
     *
     * @return The merged {@link HeatMapView}.
     */
    public HeatMapView merge() {
        return createHeatMapView(0, versions.size());
    }

    /**
     * Merges and returns all the {@link HeatMapVersion}s between
     * <code>fromVersionNumber</code> and <code>toVersionNumber</code>.
     *
     * @param fromVersionNumber The version to start at. The version number is
     * zero indexed.
     * @param toVersionNumber The version to end at (not inclusive).
     * @return The merged {@link HeatMapView}.
     */
    public HeatMapView mergeRange(int fromVersionNumber, int toVersionNumber) {
        return createHeatMapView(fromVersionNumber, toVersionNumber);
    }

    /**
     * Merges and returns the first <code>n</code> versions of the
     * {@link HeatMap}.
     *
     * @param n The number of versions to merge and return.
     * @return The merged versions.
     */
    public HeatMapView mergeFirst(int n) {
        return createHeatMapView(0, Math.max(n, versions.size() + 1));
    }

    /**
     * Merges and returns the last <code>n</code> versions of the
     * {@link HeatMap}.
     *
     * @param n The number of versions to merge and return.
     * @return The merged versions.
     */
    public HeatMapView mergeLast(int n) {
        return createHeatMapView(Math.max(0, versions.size() - n), versions.size());
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
    private HeatMapView createHeatMapView(int from, int to) {
        Map<Integer, Integer> result = new HashMap<>();
        for (int x = (from < 0) ? 0 : from; x < maps.size() && x < to - 1; x++) {
            for (int index = 0; index < sizeX * sizeY; index++) {
                result.put(index, result.getOrDefault(index, 0) + maps.get(x).get(index));
            }
        }

        return new HeatMapView(this, result);
    }

    /**
     * Creates a new map filled with the default value of the {@link HeatMap}.
     *
     * @return The new map.
     */
    private int createVersion() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < sizeX * sizeY; i++) {
            map.put(i, defaultValue);
        }

        int versionNumber = versions.size();
        HeatMapVersion version = new HeatMapVersion(versionNumber, this, map);

        this.maps.add(map);
        this.versions.add(version);

        return versionNumber;
    }

    /**
     * Increments the current version of the {@link HeatMap} at the provided
     * position.
     *
     * @param position The position
     * @param value The value.
     * @throws NoActiveHeatMapVersionException
     */
    public void addToValue(Position position, int value) throws NoActiveHeatMapVersionException, HeatMapOutOfBoundsException {

        if (activeVersion == -1) {
            throw new NoActiveHeatMapVersionException();
        }

        int index = position.y * sizeX + position.x;
        if (index < 0 || index >= numberOfPositions) {
            throw new HeatMapOutOfBoundsException(position);
        }

        Map<Integer, Integer> currentMap = maps.get(activeVersion);
        currentMap.put(index, currentMap.get(index) + value);
    }

    public void setToValue(Position position, int value) throws NoActiveHeatMapVersionException, HeatMapOutOfBoundsException {
        if (activeVersion == -1) {
            throw new NoActiveHeatMapVersionException();
        }

        int index = position.y * sizeX + position.x;
        if(index < 0 || index >= numberOfPositions){
            throw new HeatMapOutOfBoundsException(position);
        }
        
        Map<Integer, Integer> currentMap = maps.get(activeVersion);
        currentMap.put(index, value);
    }
    
    public int getValue(Position position) throws NoActiveHeatMapVersionException, HeatMapOutOfBoundsException {
        if (activeVersion == -1) {
            throw new NoActiveHeatMapVersionException();
        }

        int index = position.y * sizeX + position.x;
        if (index < 0 || index >= numberOfPositions) {
            throw new HeatMapOutOfBoundsException(position);
        }

        Map<Integer, Integer> currentMap = maps.get(activeVersion);
        return currentMap.get(index);
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

    public int getNumberOfVersions() {
        return versions.size();
    }
}
