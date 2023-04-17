package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.generator.adt.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class SquareDotGen implements DotGen {
    private static final Logger logger = LogManager.getLogger(SquareDotGen.class);
    private final int width;
    private final int height;
    private final int squareSize;
    private final int segmentThickness;
    private final int vertexThickness;

    public SquareDotGen(int width, int height, int squareSize, int segmentThickness, int vertexThickness) {
        // log all parameters in one line
        logger.trace("SquareDotGen width: {}, height: {}, squareSize: {}, segmentThickness: {}, vertexThickness: {}", width, height, squareSize, segmentThickness, vertexThickness);

        this.width = width;
        this.height = height;
        this.squareSize = squareSize;
        this.segmentThickness = segmentThickness;
        this.vertexThickness = vertexThickness;
    }

    public Structs.Mesh generateMesh() {
        MeshADT mesh = new MeshADT();

        // Create all the vertices
        for (double x = 0; x <= width; x += squareSize) {
            for (double y = 0; y <= height; y += squareSize) {
                VertexADT vertex = mesh.getVertex(x, y);
                vertex.color = Color.random();
            }
        }
        logger.trace("Created all vertices");

        // Create all the polygons
        for (double x = 0; x < width; x += squareSize) {
            for (double y = 0; y < height; y += squareSize) {
                // build vertex
                // a b
                // d c
                VertexADT a = mesh.getVertex(x, y);
                VertexADT b = mesh.getVertex(x + squareSize, y);
                VertexADT c = mesh.getVertex(x + squareSize, y + squareSize);
                VertexADT d = mesh.getVertex(x, y + squareSize);
                a.thickness = new Thickness(vertexThickness);
                b.thickness = new Thickness(vertexThickness);
                c.thickness = new Thickness(vertexThickness);
                d.thickness = new Thickness(vertexThickness);

                ArrayList<VertexADT> vertices = new ArrayList<>(4);
                vertices.add(a);
                vertices.add(b);
                vertices.add(c);
                vertices.add(d);

                // Segment: line change thickness
                SegmentADT segmentAB = mesh.getSegment(a, b);
                SegmentADT segmentBC = mesh.getSegment(b, c);
                SegmentADT segmentCD = mesh.getSegment(c, d);
                SegmentADT segmentDA = mesh.getSegment(d, a);
                segmentAB.thickness = new Thickness(segmentThickness);
                segmentAB.color = Color.average(a.color, b.color);
                segmentBC.thickness = new Thickness(segmentThickness);
                segmentBC.color = Color.average(b.color, c.color);
                segmentCD.thickness = new Thickness(segmentThickness);
                segmentCD.color = Color.average(c.color, d.color);
                segmentDA.thickness = new Thickness(segmentThickness);
                segmentDA.color = Color.average(d.color, a.color);

                PolygonADT polygon = mesh.getPolygon(vertices);
                polygon.centroid.thickness = new Thickness(vertexThickness);
                polygon.centroid.color = Color.random();

                logger.trace("Created square: {}", polygon);
            }
        }

        return mesh.toMesh();
    }
}