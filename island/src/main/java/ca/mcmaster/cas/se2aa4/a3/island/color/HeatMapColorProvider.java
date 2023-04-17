package ca.mcmaster.cas.se2aa4.a3.island.color;

import ca.mcmaster.cas.se2aa4.a3.island.shape.ShapeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Segment;

abstract public class HeatMapColorProvider implements ColorProvider {
    private final double minValue;
    private final double maxValue;
    private final Color minColor;
    private final Color maxColor;
    private final ShapeProvider shapeProvider;

    public HeatMapColorProvider(ShapeProvider shapeProvider, double minValue, double maxValue, Color minColor, Color maxColor) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.minColor = minColor;
        this.maxColor = maxColor;
        this.shapeProvider = shapeProvider;
    }

    public HeatMapColorProvider(ShapeProvider shapeProvider, double minValue, double maxValue) {
        this(shapeProvider, minValue, maxValue, new Color(255), new Color(0, 0, 255));
    }

    abstract public double getPolygonValue(Coordinate coordinate);

    public Color getPolygonColor(Coordinate coordinate) {
        if (shapeProvider.isLand(coordinate)) {
            double value = getPolygonValue(coordinate);
            if (value > maxValue) {
                return maxColor;
            } else if (value < minValue) {
                return minColor;
            }
            double ratio = (value - minValue) / (maxValue - minValue);
            return Color.interpolate(minColor, maxColor, ratio);
        } else {
            return new Color(255);
        }
    }

    public Color getSegmentColor(Segment segment) {
        return new Color(0, 0, 0, 0);
    }

    public int getSegmentThickness(Segment segment) {
        return 0;
    }
}
