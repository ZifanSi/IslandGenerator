package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.generator.adt.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class VoronoiDotGen implements DotGen {
    // logger
    private static final Logger logger = LogManager.getLogger(VoronoiDotGen.class);
    private final int width;
    private final int height;
    private final int sitesCount;
    private final int segmentThickness;
    private final int vertexThickness;
    private final int relaxations;

    public VoronoiDotGen(int width, int height, int sitesCount, int segmentThickness, int vertexThickness, int relaxations) {
        // log all parameters in one line
        logger.trace("VoronoiDotGen width: {}, height: {}, sitesCount: {}, segmentThickness: {}, vertexThickness: {}, relaxations: {}", width, height, sitesCount, segmentThickness, vertexThickness, relaxations);
        this.width = width;
        this.height = height;
        this.sitesCount = sitesCount;
        this.segmentThickness = segmentThickness;
        this.vertexThickness = vertexThickness;
        this.relaxations = relaxations;
    }

    private GeometryCollection generateVoronoi(Collection<Coordinate> sites) {
        VoronoiDiagramBuilder vdb = new VoronoiDiagramBuilder();

        vdb.setSites(sites);

        GeometryFactory gf = new GeometryFactory();

        GeometryCollection diagram = (GeometryCollection) vdb.getDiagram(gf);
        Polygon clip = new GeometryFactory().createPolygon(new Coordinate[]{
                new Coordinate(0, 0),
                new Coordinate(width, 0),
                new Coordinate(width, height),
                new Coordinate(0, height),
                new Coordinate(0, 0)
        });

        return (GeometryCollection) diagram.intersection(clip);
    }

    private List<Coordinate> applyRelaxation(GeometryCollection voronoiDiagram) {
        List<Coordinate> newCoordinates = new ArrayList<>();
        for (int j = 0; j < voronoiDiagram.getNumGeometries(); j++) {
            Polygon polygon = (Polygon) voronoiDiagram.getGeometryN(j);
            Coordinate centroid = polygon.getCentroid().getCoordinate();
            newCoordinates.add(centroid);
        }
        return newCoordinates;
    }

    private void convertJTSMeshToADT(GeometryCollection diagram, MeshADT mesh) {
        for (int i = 0; i < diagram.getNumGeometries(); i++) {
            Polygon polygon = (Polygon) diagram.getGeometryN(i);
            ArrayList<VertexADT> vertices = new ArrayList<>();
            Coordinate[] polygonCoordinates = polygon.getCoordinates();
            for (int j = 0; j < polygonCoordinates.length - 1; j++) {
                Coordinate coordinate = polygonCoordinates[j];
                vertices.add(mesh.getVertex(coordinate.x, coordinate.y));
                mesh.getVertex(coordinate.x, coordinate.y).thickness = new Thickness(vertexThickness);
            }

            for (int j = 0; j < vertices.size(); j++) {
                SegmentADT segmentADT = mesh.getSegment(vertices.get(j), vertices.get((j + 1) % vertices.size()));
                segmentADT.thickness = new Thickness(segmentThickness);
            }

            PolygonADT polygonADT = mesh.getPolygon(vertices);
            polygonADT.centroid.thickness = new Thickness(vertexThickness);
        }
    }

    @Override
    public Structs.Mesh generateMesh() {
        List<Coordinate> sites = new ArrayList<>();
        Random random = new Random(0);
        for (int i = 0; i < sitesCount; i++) {
            double x = random.nextDouble() * width;
            double y = random.nextDouble() * height;
            sites.add(new Coordinate(x, y));
        }
        logger.trace("Generated random sites");

        GeometryCollection diagram = null;
        for (int i = 0; i < relaxations; i++) {
            diagram = generateVoronoi(sites);
            sites = applyRelaxation(diagram);
            logger.trace("Applied relaxation {}", i+1);
        }

        MeshADT mesh = new MeshADT();
        convertJTSMeshToADT(diagram, mesh);
        return mesh.toMesh();
    }
}
