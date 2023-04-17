package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VoronoiDotGenTest {
    @Test
    public void meshIsNotNull() {
        DotGen generator = new VoronoiDotGen(500, 500, 20, 2,2,10);
        Structs.Mesh aMesh = generator.generateMesh();
        assertNotNull(aMesh);
    }
}
