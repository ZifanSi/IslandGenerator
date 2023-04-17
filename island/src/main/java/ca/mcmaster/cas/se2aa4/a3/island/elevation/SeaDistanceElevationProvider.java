package ca.mcmaster.cas.se2aa4.a3.island.elevation;

import ca.mcmaster.cas.se2aa4.a3.island.shape.ShapeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

public class SeaDistanceElevationProvider implements ElevationProvider {
    private final ShapeProvider shapeProvider;
    private final double maxHeight;

    public SeaDistanceElevationProvider(ShapeProvider shapeProvider, double maxHeight) {
        this.shapeProvider = shapeProvider;
        this.maxHeight = maxHeight;
    }

    @Override
    public double getElevation(Coordinate coordinate) {
        double distanceToSea = shapeProvider.nearestBorder(coordinate).distance(coordinate);
        return Math.min(Math.pow(distanceToSea, 1.8), maxHeight);
    }

    @Override
    public double getMaxElevation() {
        return maxHeight;
    }
}
