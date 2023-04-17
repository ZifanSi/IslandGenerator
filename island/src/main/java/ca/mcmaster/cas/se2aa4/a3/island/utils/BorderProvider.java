package ca.mcmaster.cas.se2aa4.a3.island.utils;

public abstract class BorderProvider {
    public abstract boolean contains(Coordinate coordinate);

    public Coordinate nearestBorder(Coordinate coordinate) {
        boolean current = contains(coordinate);
        for (int radius = 10; ; radius += 10) {
            double circumference = 2 * Math.PI * radius;
            double interval = 360 / (circumference / 10);
            for (double angle = 0; angle < 360; angle += interval) {
                Coordinate candidate = new Coordinate(
                        coordinate.x + radius * Math.cos(Math.toRadians(angle)),
                        coordinate.y + radius * Math.sin(Math.toRadians(angle))
                );
                if (contains(candidate) != current) {
                    return candidate;
                }
            }
            if (radius > 500) {
                return new Coordinate(0, 0);
            }
        }
    }
}
