package ca.mcmaster.cas.se2aa4.a3.island.soil;

import ca.mcmaster.cas.se2aa4.a3.island.aquifer.AquiferProvider;
import ca.mcmaster.cas.se2aa4.a3.island.lake.LakeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.river.RiverProvider;
import ca.mcmaster.cas.se2aa4.a3.island.shape.ShapeProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Segment;

public class BasicSoilAbsorptionProvider implements SoilAbsorptionProvider {
    private final ShapeProvider shapeProvider;
    private final LakeProvider lakeProvider;
    private final AquiferProvider aquiferProvider;
    private final RiverProvider riverProvider;

    public BasicSoilAbsorptionProvider(ShapeProvider shapeProvider, LakeProvider lakeProvider, AquiferProvider aquiferProvider, RiverProvider riverProvider) {
        this.shapeProvider = shapeProvider;
        this.lakeProvider = lakeProvider;
        this.aquiferProvider = aquiferProvider;
        this.riverProvider = riverProvider;
    }

    public double getAbsorptionLevel(Coordinate coordinate) {
        if (shapeProvider.isLand(coordinate)) {
            double absorptionLevel = 0;

            Coordinate nearestSea = shapeProvider.nearestBorder(coordinate);
            double distance = coordinate.distance(nearestSea);
            absorptionLevel += 1 / ((distance + 4) * 0.3);

            Coordinate nearestLake = lakeProvider.nearestBorder(coordinate);
            distance = coordinate.distance(nearestLake);
            absorptionLevel += 1 / ((distance + 4) * 0.3);

            Segment nearestRiver = riverProvider.nearestRiver(coordinate);
            if (nearestRiver != null) {
                distance = riverProvider.nearestRiver(coordinate).center().distance(coordinate);
                absorptionLevel += 1 / ((distance + 4) * 0.15 * riverProvider.getRiverFlow(nearestRiver));
            }

            if (aquiferProvider.isAquifer(coordinate)) {
                absorptionLevel += 0.5;
            } else {
                Coordinate nearestAquifer = aquiferProvider.nearestBorder(coordinate);
                distance = coordinate.distance(nearestAquifer);
                absorptionLevel += 1 / ((distance + 4) * 0.5);
            }

            return Math.min(1, absorptionLevel);
        } else {
            return 1;
        }
    }
}
