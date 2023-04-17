package ca.mcmaster.cas.se2aa4.a3.island.elevation;

import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

public class MountainElevationProvider implements ElevationProvider {
    private final double centerX;
    private final double centerY;
    private final double radius;
    private final double mountainHeight;

    public MountainElevationProvider(double width, double height, double radius, double mountainHeight) {
        this.centerX = width / 2;
        this.centerY = height / 2;
        this.radius = radius;
        this.mountainHeight = mountainHeight;
    }

    public double getElevation(Coordinate coordinate) {
        double distance = Math.sqrt(Math.pow(coordinate.x - centerX, 2) + Math.pow(coordinate.y - centerY, 2));
        if (distance > radius) {
            return 0;
        }
        if (radius - distance < 50) {
            return (radius - distance) / 4;
        }
        return mountainHeight * (1 - distance / radius);
    }

    @Override
    public double getMaxElevation() {
        return mountainHeight;
    }
}
