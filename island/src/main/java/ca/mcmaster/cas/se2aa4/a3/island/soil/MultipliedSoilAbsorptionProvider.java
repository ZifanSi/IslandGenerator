package ca.mcmaster.cas.se2aa4.a3.island.soil;

import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

public class MultipliedSoilAbsorptionProvider implements SoilAbsorptionProvider {
    private final SoilAbsorptionProvider soilAbsorptionProvider;
    private final double multiplier;

    public MultipliedSoilAbsorptionProvider(SoilAbsorptionProvider soilAbsorptionProvider, double multiplier) {
        this.soilAbsorptionProvider = soilAbsorptionProvider;
        this.multiplier = multiplier;
    }

    @Override
    public double getAbsorptionLevel(Coordinate coordinate) {
        return Math.min(1, soilAbsorptionProvider.getAbsorptionLevel(coordinate) * multiplier);
    }
}
