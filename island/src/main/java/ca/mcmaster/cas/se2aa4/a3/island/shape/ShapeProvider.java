package ca.mcmaster.cas.se2aa4.a3.island.shape;

import ca.mcmaster.cas.se2aa4.a3.island.utils.BorderProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

public abstract class ShapeProvider extends BorderProvider {
    public abstract boolean isLand(Coordinate coordinate);

    @Override
    public boolean contains(Coordinate coordinate) {
        return isLand(coordinate);
    }
}
