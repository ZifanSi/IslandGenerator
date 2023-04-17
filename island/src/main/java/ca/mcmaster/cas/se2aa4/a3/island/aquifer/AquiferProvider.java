package ca.mcmaster.cas.se2aa4.a3.island.aquifer;

import ca.mcmaster.cas.se2aa4.a3.island.utils.BorderProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

public abstract class AquiferProvider extends BorderProvider {
    public abstract boolean isAquifer(Coordinate coordinate);

    @Override
    public boolean contains(Coordinate coordinate) {
        return isAquifer(coordinate);
    }
}
