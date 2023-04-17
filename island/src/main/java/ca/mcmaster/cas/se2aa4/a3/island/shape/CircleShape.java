package ca.mcmaster.cas.se2aa4.a3.island.shape;

import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

public class CircleShape extends ShapeProvider {
    private final double x;
    private final double y;
    private final double radius;

    public CircleShape(double width, double height, double radius) {
        this.x = width / 2;
        this.y = height / 2;
        this.radius = radius;
    }

    @Override
    public boolean isLand(Coordinate coordinate) {
        return Math.pow(coordinate.x - this.x, 2) + Math.pow(coordinate.y - this.y, 2) <= Math.pow(radius, 2);
    }
}
