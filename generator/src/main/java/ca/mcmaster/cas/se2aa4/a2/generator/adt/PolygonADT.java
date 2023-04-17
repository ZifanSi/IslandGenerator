package ca.mcmaster.cas.se2aa4.a2.generator.adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.List;

public class PolygonADT {
    final List<PolygonADT> neighbours = new ArrayList<>();
    final List<SegmentADT> segments;
    final int id;
    public final VertexADT centroid;

    PolygonADT(MeshADT mesh, List<SegmentADT> segments, List<VertexADT> vertices, int id) {
        this.id = id;
        this.segments = segments;
        double x = 0;
        double y = 0;
        for (VertexADT vertex : vertices) {
            x += vertex.x;
            y += vertex.y;
        }
        centroid = mesh.getVertex(x / vertices.size(), y / vertices.size());
        centroid.centroid=true;
    }

    // Method to calculate
    public Structs.Polygon toPolygon() {
        Structs.Polygon.Builder builder = Structs.Polygon.newBuilder();
        for (PolygonADT neighbour : neighbours) {
            builder.addNeighborIdxs(neighbour.id);
        }

        builder.setCentroidIdx(centroid.id);
        for (SegmentADT segment : this.segments) {
            builder.addSegmentIdxs(segment.id);
        }

        return builder.build();
    }

    @Override
    public String toString() {
        return "PolygonADT{" +
                "id=" + id +
                ", centroid=" + centroid +
                '}';
    }
}
