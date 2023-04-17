package ca.mcmaster.cas.se2aa4.a2.generator.adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PolygonADTest {
    final MeshADT mesh = new MeshADT();
    final ArrayList<VertexADT> vertices = new ArrayList<>();
    final ArrayList<SegmentADT> segments = new ArrayList<>();

    @Test
    public void getVerticesTest() {
        VertexADT a = mesh.getVertex(1, 1);
        VertexADT b = mesh.getVertex(2, 1);
        VertexADT c = mesh.getVertex(2, 2);
        VertexADT d = mesh.getVertex(1, 2);
        vertices.add(a);
        vertices.add(b);
        vertices.add(c);
        vertices.add(d);
        for (int i = 1; i < vertices.size(); i++) {
            segments.add(mesh.getSegment(vertices.get(i - 1), vertices.get(i)));
        }
        segments.add(mesh.getSegment(vertices.get(vertices.size() - 1), vertices.get(0)));

        PolygonADT polygon = new PolygonADT(mesh, segments, vertices, 4);

        assertEquals(1.5, polygon.centroid.x);
        assertEquals(1.5, polygon.centroid.y);
    }

    @Test
    public void getCentroidTest() {
        VertexADT a = mesh.getVertex(1, 1);
        VertexADT b = mesh.getVertex(2, 1);
        VertexADT c = mesh.getVertex(2, 2);
        VertexADT d = mesh.getVertex(1, 2);
        vertices.add(a);
        vertices.add(b);
        vertices.add(c);
        vertices.add(d);
        for (int i = 1; i < vertices.size(); i++) {
            segments.add(mesh.getSegment(vertices.get(i - 1), vertices.get(i)));
        }
        segments.add(mesh.getSegment(vertices.get(vertices.size() - 1), vertices.get(0)));

        PolygonADT polygon = new PolygonADT(mesh, segments, vertices, 4);
        VertexADT centroid = polygon.centroid;
        assertEquals(1.5, centroid.x);
        assertEquals(1.5, centroid.y);
    }

    @Test
    public void toPolygonTest() {
        MeshADT mesh = new MeshADT();
        VertexADT a = mesh.getVertex(1, 1);
        VertexADT b = mesh.getVertex(2, 1);
        VertexADT c = mesh.getVertex(2, 2);
        VertexADT d = mesh.getVertex(1, 2);
        ArrayList<VertexADT> vertices = new ArrayList<>();
        vertices.add(a);
        vertices.add(b);
        vertices.add(c);
        vertices.add(d);

        ArrayList<SegmentADT> segments = new ArrayList<>(vertices.size());
        for (int i = 1; i < vertices.size(); i++) {
            segments.add(mesh.getSegment(vertices.get(i - 1), vertices.get(i)));
        }
        segments.add(mesh.getSegment(vertices.get(vertices.size() - 1), vertices.get(0)));

        PolygonADT polygon = new PolygonADT(mesh, segments, vertices, 4);

        Structs.Polygon polygonBuilder = polygon.toPolygon();
        assertNotNull(polygonBuilder);
    }

}
