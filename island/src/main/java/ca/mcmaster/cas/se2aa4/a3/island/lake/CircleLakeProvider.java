package ca.mcmaster.cas.se2aa4.a3.island.lake;

import ca.mcmaster.cas.se2aa4.a3.island.shape.ShapeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CircleLakeProvider extends LakeProvider {
    private record Lake(Coordinate center, double radius) {
    }

    private final List<Lake> lakes = new ArrayList<>();

    public CircleLakeProvider(double width, double height, ShapeProvider shapeProvider, int lakeCount, long seed) {
        Random random = new Random(seed);
        int i = 0;
        while (lakes.size() < lakeCount) {
            Coordinate center = new Coordinate(random.nextDouble() * width, random.nextDouble() * height);
            double radius = random.nextDouble() * (width / 8);
            if (shapeProvider.isLand(center) && radius > 10 && shapeProvider.nearestBorder(center).distance(center) > (radius + 50)) {
                lakes.add(new Lake(center, radius));
            }
            i++;
            if (i > lakeCount * 10) {
                break;
            }
        }
    }

    public boolean isLake(Coordinate coordinate) {
        for (Lake lake : lakes) {
            if (coordinate.distance(lake.center) < lake.radius) {
                return true;
            }
        }
        return false;
    }
}
