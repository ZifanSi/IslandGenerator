package ca.mcmaster.cas.se2aa4.a3.island.aquifer;

import ca.mcmaster.cas.se2aa4.a3.island.shape.ShapeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CircleAquiferProvider extends AquiferProvider {

    private record Aquifer(Coordinate center, double radius) {
    }

    private final List<Aquifer> aquifers = new ArrayList<>();

    public CircleAquiferProvider(double width, double height, ShapeProvider shapeProvider, int aquiferCount, long seed) {
        Random random = new Random(seed);
        while (aquifers.size() < aquiferCount) {
            double x = random.nextDouble() * width;
            double y = random.nextDouble() * height;
            if (shapeProvider.isLand(new Coordinate(x, y))) {
                aquifers.add(new Aquifer(new Coordinate(x, y), random.nextDouble() * (width / 8)));
            }
        }
    }

    @Override
    public boolean isAquifer(Coordinate coordinate) {
        for (Aquifer aquifer : aquifers) {
            if (coordinate.distance(aquifer.center) < aquifer.radius) {
                return true;
            }
        }
        return false;
    }
}
