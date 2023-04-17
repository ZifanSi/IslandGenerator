package ca.mcmaster.cas.se2aa4.a3.island.elevation;

import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;
import ca.mcmaster.cas.se2aa4.a3.island.utils.PerlinNoise;

public class NoiseElevationProvider implements ElevationProvider {
    private final PerlinNoise noiseGenerator;
    private final double amplitude;
    private final ElevationProvider elevationProvider;

    public NoiseElevationProvider(ElevationProvider elevationProvider, double amplitude, long seed) {
        this.elevationProvider = elevationProvider;
        this.noiseGenerator = new PerlinNoise((int) seed);
        this.amplitude = amplitude;
    }

    @Override
    public double getElevation(Coordinate coordinate) {
        return elevationProvider.getElevation(coordinate) + (noiseGenerator.noise(coordinate.x / 3, coordinate.y / 3) * amplitude);
    }

    @Override
    public double getMaxElevation() {
        return elevationProvider.getMaxElevation() + amplitude;
    }
}
