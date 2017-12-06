package r1.heatmap;

public class UnknownHeatMapVersionException extends Exception {
    private int unknownVersion;

    public UnknownHeatMapVersionException(int unknownVersion) {
        this.unknownVersion = unknownVersion;
    }

    public void setUnknownVersion(int unknownVersion) {
        this.unknownVersion = unknownVersion;
    }
}
