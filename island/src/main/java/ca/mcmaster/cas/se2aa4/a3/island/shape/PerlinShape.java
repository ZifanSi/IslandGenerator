package ca.mcmaster.cas.se2aa4.a3.island.shape;

import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;
import ca.mcmaster.cas.se2aa4.a3.island.utils.PerlinNoise;

import java.util.HashMap;

public class PerlinShape extends ShapeProvider {
    private final PerlinNoise noiseGenerator;
    private final HashMap<Coordinate, Boolean> cache = new HashMap<>();

    public PerlinShape(long seed) {
        this.noiseGenerator = new PerlinNoise((int) seed);
    }

    @Override
    public boolean isLand(Coordinate coordinate) {
        if (cache.containsKey(coordinate)) {
            return cache.get(coordinate);
        }
        boolean value = noiseGenerator.noise(coordinate.x / 75, coordinate.y / 75) < 0;
        cache.put(coordinate, value);
        return value;
    }
}
