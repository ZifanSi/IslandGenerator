package ca.mcmaster.cas.se2aa4.a3.island.biome;

import ca.mcmaster.cas.se2aa4.a3.island.color.Color;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

public interface BiomeProvider {
    Color getBiome(Coordinate coordinate);
}
