package ca.mcmaster.cas.se2aa4.a3.island.biome;

import ca.mcmaster.cas.se2aa4.a3.island.color.Color;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.ElevationProvider;
import ca.mcmaster.cas.se2aa4.a3.island.shape.ShapeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.soil.SoilAbsorptionProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

public class  BasicBiomeProvider implements BiomeProvider {
    private final ElevationProvider elevationProvider;
    private final SoilAbsorptionProvider soilAbsorptionProvider;
    private final ShapeProvider shapeProvider;

    public BasicBiomeProvider(ElevationProvider elevationProvider, SoilAbsorptionProvider soilAbsorptionProvider, ShapeProvider shapeProvider) {
        this.elevationProvider = elevationProvider;
        this.soilAbsorptionProvider = soilAbsorptionProvider;
        this.shapeProvider = shapeProvider;
    }

    @Override
    public Color getBiome(Coordinate coordinate) {
        double distanceToSea = shapeProvider.nearestBorder(coordinate).distance(coordinate);
        double elevation = elevationProvider.getElevation(coordinate);
        double temperature = -0.0062 * elevation + 32;
        double absorption = soilAbsorptionProvider.getAbsorptionLevel(coordinate);

        if (temperature < 0) {
            return new Color(230, 230, 230);
        }

        if (distanceToSea < 30 && elevation < 500) {
            return new Color(239, 247, 126);
        }

        if (temperature > 10 && absorption > 0.3) {
            return new Color(14, 179, 25);
        }

        return new Color(115, 66, 2);
    }
}
