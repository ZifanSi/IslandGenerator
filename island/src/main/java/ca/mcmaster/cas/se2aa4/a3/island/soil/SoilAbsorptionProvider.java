package ca.mcmaster.cas.se2aa4.a3.island.soil;

import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

public interface SoilAbsorptionProvider {
    // 0: completely dry, 1: lake/sea
    double getAbsorptionLevel(Coordinate coordinate);
}
