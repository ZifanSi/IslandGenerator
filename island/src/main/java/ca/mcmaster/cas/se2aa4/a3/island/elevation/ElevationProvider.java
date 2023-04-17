package ca.mcmaster.cas.se2aa4.a3.island.elevation;

import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

public interface ElevationProvider {
    double getElevation(Coordinate coordinate);

    double getMaxElevation();
}
