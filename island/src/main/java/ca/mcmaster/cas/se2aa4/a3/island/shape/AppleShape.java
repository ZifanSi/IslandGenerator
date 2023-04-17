package ca.mcmaster.cas.se2aa4.a3.island.shape;

import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

public class AppleShape extends ShapeProvider {
    private final double x;
    private final double y;
    private final double radius;

    public AppleShape(double width, double height, double radius) {
        this.x = width / 2;
        this.y = height / 2;
        this.radius = radius;
    }

    public boolean isLand(Coordinate coordinate) {
        double originX = coordinate.x - this.x;
        double originY = coordinate.y - this.y;

        boolean f1 = Math.pow((originX + 0.4 * radius), 2) + Math.pow((originY), 2) <= Math.pow(0.6 * radius, 2);
        boolean f2 = Math.pow((originX - 0.4 * radius), 2) + Math.pow(originY, 2) <= Math.pow(0.6 * radius, 2);
        boolean f3 = originY >= 0 && Math.pow(originX, 2) + Math.pow(originY, 2) <= Math.pow(radius, 2);
        boolean f4 = Math.pow((originX - 1.1 * radius), 2) + Math.pow(originY, 2) >= Math.pow(0.5 * radius, 2);
        boolean f5 = Math.pow((originX - 0.35 * radius), 2) + Math.pow((originY + 0.8 * radius), 2) <= Math.pow(0.4 * radius, 2);
        boolean f6 = Math.pow((originX), 2) + Math.pow((originY + 1.1 * radius), 2) <= Math.pow(0.4 * radius, 2);

        return ((f1 || f2 || f3) && f4) || (f5 && f6);
    }
}
