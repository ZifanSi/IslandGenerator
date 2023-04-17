package ca.mcmaster.cas.se2aa4.a3.island.lake;

import ca.mcmaster.cas.se2aa4.a3.island.utils.BorderProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

public abstract class LakeProvider extends BorderProvider {
    public abstract boolean isLake(Coordinate coordinate);

    @Override
    public boolean contains(Coordinate coordinate) {
        return isLake(coordinate);
    }
}
