package ca.mcmaster.cas.se2aa4.a3.island.color;

import ca.mcmaster.cas.se2aa4.a3.island.biome.BiomeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.lake.LakeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.river.RiverProvider;
import ca.mcmaster.cas.se2aa4.a3.island.shape.ShapeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Segment;

public class IslandColorProvider implements ColorProvider {
    private final ShapeProvider shapeProvider;
    private final LakeProvider lakeProvider;
    private final RiverProvider riverProvider;
    private final BiomeProvider biomeProvider;

    public IslandColorProvider(ShapeProvider shapeProvider, LakeProvider lakeProvider, RiverProvider riverProvider, BiomeProvider biomeProvider) {
        this.shapeProvider = shapeProvider;
        this.lakeProvider = lakeProvider;
        this.riverProvider = riverProvider;
        this.biomeProvider = biomeProvider;
    }

    public Color getPolygonColor(Coordinate coordinate) {
        boolean isLand = shapeProvider.isLand(coordinate);
        boolean isLake = lakeProvider.isLake(coordinate);
        if (isLand && !isLake) {
            return biomeProvider.getBiome(coordinate);
        } else {
            return new Color(150, 210, 255);
        }
    }

    public Color getSegmentColor(Segment segment) {
        if (riverProvider.getRiverFlow(segment) > 0) {
            return new Color(150, 210, 255);
        }
        return new Color(0, 0, 0, 0);
    }

    public int getSegmentThickness(Segment segment) {
        return riverProvider.getRiverFlow(segment);
    }
}
