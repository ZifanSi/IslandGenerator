package ca.mcmaster.cas.se2aa4.a2.generator.adt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VertexADTest {
    VertexADT vertex = new VertexADT(1, 1, 4);
    @Test
    public void toVertexTest(){
        assertNotNull(vertex.toVertex());
    }
}
