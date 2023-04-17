package ca.mcmaster.cas.se2aa4.a3.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.color.Color;
import ca.mcmaster.cas.se2aa4.a3.island.color.ColorProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Segment;

import java.util.ArrayList;
import java.util.List;

public class IslandGenerator {
    private final ColorProvider colorProvider;

    public IslandGenerator(ColorProvider colorProvider) {
        this.colorProvider = colorProvider;
    }

    public Structs.Mesh generate(Structs.Mesh mesh) {
        Structs.Mesh.Builder builder = Structs.Mesh.newBuilder();
        List<Structs.Vertex> vertices = mesh.getVerticesList();
        builder.addAllVertices(vertices);

        List<Structs.Segment> segments = new ArrayList<>();
        for (Structs.Segment meshSegment : mesh.getSegmentsList()) {
            Segment segment = Segment.fromSegment(meshSegment, vertices);
            Color color = colorProvider.getSegmentColor(segment);
            int thickness = colorProvider.getSegmentThickness(segment);
            Structs.Segment.Builder segmentBuilder = Structs.Segment.newBuilder(meshSegment);
            segmentBuilder.clearProperties();
            segmentBuilder.addProperties(color.toProperty());
            segmentBuilder.addProperties(Structs.Property.newBuilder().setKey("thickness").setValue(String.valueOf(thickness)).build());
            segments.add(segmentBuilder.build());
        }
        builder.addAllSegments(segments);

        List<Structs.Polygon> polygons = new ArrayList<>();
        for (Structs.Polygon polygon : mesh.getPolygonsList()) {
            int centroidId = polygon.getCentroidIdx();
            Structs.Vertex centroid = vertices.get(centroidId);
            Color color = colorProvider.getPolygonColor(Coordinate.fromVertex(centroid));
            Structs.Polygon.Builder polygonBuilder = Structs.Polygon.newBuilder(polygon);
            polygonBuilder.clearProperties();
            polygonBuilder.addProperties(color.toProperty());
            polygons.add(polygonBuilder.build());
        }
        builder.addAllPolygons(polygons);
        return builder.build();
    }
}
