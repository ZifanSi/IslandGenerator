package ca.mcmaster.cas.se2aa4.a2.generator.adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeshADT {
    // logger
    private static final Logger logger = LogManager.getLogger(MeshADT.class);
    private final ArrayList<VertexADT> vertices = new ArrayList<>();
    private final ArrayList<SegmentADT> segments = new ArrayList<>();
    private final ArrayList<PolygonADT> polygons = new ArrayList<>();

    public MeshADT() {
    }

    public VertexADT getVertex(double x, double y) {
        logger.trace("getVertex x: {}, y: {}", x, y);
        for (VertexADT vertex : vertices) {
            if (vertex.x == x && vertex.y == y) {
                logger.trace("Vertex already exists");
                return vertex;
            }
        }

        logger.trace("Vertex does not exist, creating new one");
        VertexADT vertex = new VertexADT(x, y, vertices.size());
        vertices.add(vertex);
        return vertex;
    }

    public SegmentADT getSegment(VertexADT start, VertexADT end) {
        logger.trace("getSegment start: {}, end: {}", start, end);
        for (SegmentADT segment : segments) {
            if ((segment.start == start && segment.end == end) || (segment.end == start && segment.start == end)) {
                logger.trace("Segment already exists");
                return segment;
            }
        }

        logger.trace("Segment does not exist, creating new one");
        SegmentADT segment = new SegmentADT(start, end, segments.size());
        segments.add(segment);
        return segment;
    }

    public PolygonADT getPolygon(List<VertexADT> polygonVertices) {
        logger.trace("getPolygon polygonVertices: {}", polygonVertices);

        // use JTS convex hull to make sure the vertices are in the correct order
        Coordinate[] coordinates = new Coordinate[polygonVertices.size() + 1];
        for (int i = 0; i < polygonVertices.size(); i++) {
            coordinates[i] = new Coordinate(polygonVertices.get(i).x, polygonVertices.get(i).y);
        }
        coordinates[coordinates.length - 1] = coordinates[0];
        Polygon jtsPolygon = new GeometryFactory().createPolygon(coordinates);
        Coordinate[] convexCoordinates = jtsPolygon.convexHull().getCoordinates();
        polygonVertices = new ArrayList<>(polygonVertices.size());
        for (int i = 0; i < convexCoordinates.length - 1; i++) {
            Coordinate coordinate = convexCoordinates[i];
            polygonVertices.add(getVertex(coordinate.x, coordinate.y));
        }

        // create the polygon
        ArrayList<SegmentADT> polygonSegments = new ArrayList<>(polygonVertices.size());
        for (int i = 1; i < polygonVertices.size(); i++) {
            polygonSegments.add(getSegment(polygonVertices.get(i - 1), polygonVertices.get(i)));
        }
        polygonSegments.add(getSegment(polygonVertices.get(polygonVertices.size() - 1), polygonVertices.get(0)));

        PolygonADT polygon = new PolygonADT(this, polygonSegments, polygonVertices, polygons.size());
        polygons.add(polygon);

        return polygon;
    }

    public PolygonADT findPolygonByCentroid(double x, double y) {
        for (PolygonADT polygon : polygons) {
            if (polygon.centroid.x == x && polygon.centroid.y == y) {
                return polygon;
            }
        }
        return null;
    }

    public Structs.Mesh toMesh() {
        logger.trace("toMesh {} vertices, {} segments, {} polygons", vertices.size(), segments.size(), polygons.size());

        // calculate neighbours using triangulation
        DelaunayTriangulationBuilder triangulationBuilder = new DelaunayTriangulationBuilder();
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        for (PolygonADT polygon : polygons) {
            coordinates.add(new Coordinate(polygon.centroid.x, polygon.centroid.y));
        }
        triangulationBuilder.setSites(coordinates);
        GeometryCollection triangles = (GeometryCollection) triangulationBuilder.getTriangles(new GeometryFactory());
        for (int i = 0; i < triangles.getNumGeometries(); i++) {
            Geometry triangle = triangles.getGeometryN(i);
            Coordinate[] triangleCoordinates = triangle.getCoordinates();

            Coordinate a = triangleCoordinates[0];
            Coordinate b = triangleCoordinates[1];
            Coordinate c = triangleCoordinates[2];
            PolygonADT polygonA = findPolygonByCentroid(a.x, a.y);
            PolygonADT polygonB = findPolygonByCentroid(b.x, b.y);
            PolygonADT polygonC = findPolygonByCentroid(c.x, c.y);
            polygonA.neighbours.add(polygonB);
            polygonA.neighbours.add(polygonC);
            polygonB.neighbours.add(polygonA);
            polygonB.neighbours.add(polygonC);
            polygonC.neighbours.add(polygonA);
            polygonC.neighbours.add(polygonB);
        }

        // convert to protobuf
        Structs.Mesh.Builder builder = Structs.Mesh.newBuilder();
        for (VertexADT vertexADT : vertices) {
            builder.addVertices(vertexADT.toVertex());
        }
        for (SegmentADT segmentADT : segments) {
            builder.addSegments(segmentADT.toSegment());
        }
        for (PolygonADT polygonADT : polygons) {
            builder.addPolygons(polygonADT.toPolygon());
        }
        return builder.build();
    }
}