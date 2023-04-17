package ca.mcmaster.cas.se2aa4.a3.island.lake;

import ca.mcmaster.cas.se2aa4.a3.island.shape.ShapeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class EllipseLakeProvider extends LakeProvider {
    private record Lake(Coordinate center, double shortRadius, double longRadius) {
    }

    private final List<Lake> lakes = new ArrayList<>();

    public EllipseLakeProvider(double width, double height, ShapeProvider shapeProvider, int lakeCount) {
        while (lakes.size() < lakeCount) {
            Coordinate center = new Coordinate(Math.random() * width, Math.random() * height);
            double shortRadius = Math.random() * (height / 8);
            double longRadius = Math.random() * (width / 8);
            if (shapeProvider.isLand(center) && shapeProvider.nearestBorder(center).distance(center) > (longRadius + 50)) {
                lakes.add(new Lake(center, shortRadius, longRadius));
            }
        }
    }

    public boolean isLake(Coordinate coordinate) {
        for (Lake lake : lakes) {
            double originX = coordinate.x - lake.center.x;
            double originY = coordinate.y - lake.center.y;
            if (Math.pow((originX / lake.shortRadius), 2) + Math.pow((originY / lake.longRadius), 2) <= 1) {
                return true;
            }
        }
        return false;
    }
}
