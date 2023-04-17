package ca.mcmaster.cas.se2aa4.a2.generator.adt;


import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


public class MeshADTest {
    @Test
    public void getVertexTest(){
        MeshADT vertex = new MeshADT();
        VertexADT expected = vertex.getVertex(2,6);
        assertEquals(expected.x, 2);
        assertEquals(expected.y, 6);
    }
    @Test
    public void getSegmentTest(){
        VertexADT vertexStart = new VertexADT(1,2,3);
        VertexADT vertexEnd = new VertexADT(4,5,6);
        MeshADT segment = new MeshADT();
        SegmentADT expected = segment.getSegment(vertexStart, vertexEnd);
        assertEquals(expected.start, vertexStart);
        assertEquals(expected.end, vertexEnd);
    }
    @Test
    public void getPolygonTest(){
        MeshADT mesh = new MeshADT();
        VertexADT a = mesh.getVertex(1, 1);
        VertexADT b = mesh.getVertex(2, 1);
        VertexADT c = mesh.getVertex(2, 2);
        VertexADT d = mesh.getVertex(1, 2);
        ArrayList<VertexADT> polygonVertices = new ArrayList<>(4);
        polygonVertices.add(a);
        polygonVertices.add(b);
        polygonVertices.add(c);
        polygonVertices.add(d);
        PolygonADT expected = mesh.getPolygon(polygonVertices);
        assertEquals(1.5,expected.centroid.x);
        assertEquals(1.5,expected.centroid.y);
    }
    @Test
    public void toMeshTest(){
        ArrayList<PolygonADT> polygons = new ArrayList<>();
        MeshADT mesh = new MeshADT();
        VertexADT a = mesh.getVertex(1, 1);
        VertexADT b = mesh.getVertex(2, 1);
        VertexADT c = mesh.getVertex(2, 2);
        VertexADT d = mesh.getVertex(1, 2);
        ArrayList<VertexADT> polygonVertices1 = new ArrayList<>(4);
        polygonVertices1.add(a);
        polygonVertices1.add(b);
        polygonVertices1.add(c);
        polygonVertices1.add(d);
        PolygonADT ploygon1 = mesh.getPolygon(polygonVertices1);
        polygons.add(ploygon1);
        VertexADT e = mesh.getVertex(2, 2);
        VertexADT f = mesh.getVertex(3, 2);
        VertexADT g = mesh.getVertex(3, 3);
        VertexADT h = mesh.getVertex(2, 3);
        ArrayList<VertexADT> polygonVertices2 = new ArrayList<>(4);
        polygonVertices2.add(a);
        polygonVertices2.add(b);
        polygonVertices2.add(c);
        polygonVertices2.add(d);
        PolygonADT ploygon2 = mesh.getPolygon(polygonVertices2);
        polygons.add(ploygon2);

        ArrayList<VertexADT> vertices = new ArrayList<>();
        vertices.add(a);
        vertices.add(b);
        vertices.add(c);
        vertices.add(d);
        vertices.add(e);
        vertices.add(f);
        vertices.add(g);
        vertices.add(h);

        ArrayList<SegmentADT> segments = new ArrayList<>(vertices.size());

        Structs.Mesh meshBuild = mesh.toMesh();
        assertNotNull(meshBuild);
    }
}
