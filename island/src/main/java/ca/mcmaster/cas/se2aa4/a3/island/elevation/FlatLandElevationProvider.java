package ca.mcmaster.cas.se2aa4.a3.island.elevation;

import ca.mcmaster.cas.se2aa4.a3.island.shape.ShapeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

public class FlatLandElevationProvider implements ElevationProvider {
    private final double maxHeight;
    private final double slope;
    private final ShapeProvider shapeProvider;

    public FlatLandElevationProvider(double maxHeight, double slope, ShapeProvider shapeProvider) {
        this.maxHeight = maxHeight;
        this.slope = slope;
        this.shapeProvider = shapeProvider;
    }

    @Override
    public double getElevation(Coordinate coordinate) {
        return Math.min(maxHeight, shapeProvider.nearestBorder(coordinate).distance(coordinate) * slope);
    }

    @Override
    public double getMaxElevation() {
        return maxHeight;
    }
}
