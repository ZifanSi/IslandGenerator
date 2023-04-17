package ca.mcmaster.cas.se2aa4.a3.island;

import ca.mcmaster.cas.se2aa4.a3.island.aquifer.AquiferProvider;
import ca.mcmaster.cas.se2aa4.a3.island.aquifer.CircleAquiferProvider;
import ca.mcmaster.cas.se2aa4.a3.island.biome.BasicBiomeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.biome.BiomeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.color.ColorProvider;
import ca.mcmaster.cas.se2aa4.a3.island.color.HeatMapColorProvider;
import ca.mcmaster.cas.se2aa4.a3.island.color.IslandColorProvider;
import ca.mcmaster.cas.se2aa4.a3.island.elevation.*;
import ca.mcmaster.cas.se2aa4.a3.island.lake.CircleLakeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.lake.LakeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.river.FlowingRiverProvider;
import ca.mcmaster.cas.se2aa4.a3.island.river.RiverProvider;
import ca.mcmaster.cas.se2aa4.a3.island.shape.*;
import ca.mcmaster.cas.se2aa4.a3.island.soil.BasicSoilAbsorptionProvider;
import ca.mcmaster.cas.se2aa4.a3.island.soil.MultipliedSoilAbsorptionProvider;
import ca.mcmaster.cas.se2aa4.a3.island.soil.SoilAbsorptionProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Segment;

import java.io.IOException;
import java.util.List;

public class ColorProviderFactory {
    public static ColorProvider createColorProvider(
            double width,
            double height,
            String shape,
            String elevation,
            double elevationNoise,
            int maxLakes,
            List<Segment> segments,
            int maxRivers,
            int maxAquifers,
            double soilAbsorptionFactor,
            long seed,
            String heatmap
    ) throws IOException {
        ShapeProvider shapeProvider = switch (shape) {
            case "apple" -> new AppleShape(width, height, width * 0.4);
            case "circle" -> new CircleShape(width, height, width * 0.4);
            case "perlin" -> new PerlinShape(seed);
            default -> new ImageShapeProvider(width, height, shape);
        };

        ElevationProvider elevationProvider = switch (elevation) {
            case "flatland" -> new FlatLandElevationProvider(1000, 100, shapeProvider);
            case "mountain" -> new MountainElevationProvider(width, height, width * 0.4, 6000);
            case "seaDistance" -> new SeaDistanceElevationProvider(shapeProvider, 6000);
            default -> throw new IllegalArgumentException("Unknown elevation type: " + elevation);
        };
        ElevationProvider noiseElevationProvider = new NoiseElevationProvider(elevationProvider, elevationNoise, seed);

        LakeProvider lakeProvider = new CircleLakeProvider(width, height, shapeProvider, maxLakes, seed);
        AquiferProvider aquiferProvider = new CircleAquiferProvider(width, height, shapeProvider, maxAquifers, seed);
        RiverProvider riverProvider = new FlowingRiverProvider(segments, shapeProvider, lakeProvider, noiseElevationProvider, maxRivers, seed);
        SoilAbsorptionProvider soilAbsorptionProvider = new BasicSoilAbsorptionProvider(shapeProvider, lakeProvider, aquiferProvider, riverProvider);
        SoilAbsorptionProvider multipliedSoilAbsorptionProvider = new MultipliedSoilAbsorptionProvider(soilAbsorptionProvider, soilAbsorptionFactor);
        BiomeProvider biomeProvider = new BasicBiomeProvider(noiseElevationProvider, multipliedSoilAbsorptionProvider, shapeProvider);

        return switch (heatmap) {
            case "humidity" -> new HeatMapColorProvider(shapeProvider, 0, 1) {
                @Override
                public double getPolygonValue(Coordinate coordinate) {
                    return multipliedSoilAbsorptionProvider.getAbsorptionLevel(coordinate);
                }
            };
            case "elevation" -> new HeatMapColorProvider(shapeProvider, 0, noiseElevationProvider.getMaxElevation()) {
                @Override
                public double getPolygonValue(Coordinate coordinate) {
                    return noiseElevationProvider.getElevation(coordinate);
                }
            };
            default -> new IslandColorProvider(
                    shapeProvider,
                    lakeProvider,
                    riverProvider,
                    biomeProvider
            );
        };
    }
}
