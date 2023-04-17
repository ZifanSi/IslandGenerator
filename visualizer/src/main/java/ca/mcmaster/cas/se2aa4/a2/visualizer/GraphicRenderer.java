package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GraphicRenderer {
    private static final Logger logger = LogManager.getLogger(GraphicRenderer.class);

    public void render(Mesh mesh, Graphics2D canvas) {
        boolean isDebug = Objects.equals(System.getProperty("env"), "debug");
        if (isDebug) {
            logger.info("Debug mode detected");
        }

        List<Vertex> vertices = mesh.getVerticesList();
        // draw all vertices
        for (Vertex v : vertices) {
            logger.trace("drawing vertex: {}", v);
            double centre_x = v.getX() - (extractThickness(v.getPropertiesList()) / 2.0d);
            double centre_y = v.getY() - (extractThickness(v.getPropertiesList()) / 2.0d);

            // set centroid color to red if in debug mode
            if (isDebug && extractIsCentroid(v.getPropertiesList())) {
                canvas.setColor(new Color(255, 0, 0));
            } else {
                canvas.setColor(extractColor(v.getPropertiesList()));
            }

            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, extractThickness(v.getPropertiesList()), extractThickness(v.getPropertiesList()));
            canvas.fill(point);
        }


        for (Structs.Polygon polygon : mesh.getPolygonsList()) {
            canvas.setColor(extractColor(polygon.getPropertiesList()));
            List<Integer> segmentIds = polygon.getSegmentIdxsList();
            int[] vertex_x = new int[segmentIds.size()];
            int[] vertex_y = new int[segmentIds.size()];
            List<Vertex> verticesList = new ArrayList<>();
            Vertex pointer;
            if (mesh.getVertices(mesh.getSegments(segmentIds.get(1)).getV1Idx()) ==
                    mesh.getVertices(mesh.getSegments(segmentIds.get(0)).getV1Idx()) ||
                    mesh.getVertices(mesh.getSegments(segmentIds.get(1)).getV2Idx()) ==
                            mesh.getVertices(mesh.getSegments(segmentIds.get(0)).getV1Idx())) {
                verticesList.add(mesh.getVertices(mesh.getSegments(segmentIds.get(0)).getV1Idx()));
                pointer = mesh.getVertices(mesh.getSegments(segmentIds.get(0)).getV1Idx());
            } else {
                verticesList.add(mesh.getVertices(mesh.getSegments(segmentIds.get(0)).getV2Idx()));
                pointer = mesh.getVertices(mesh.getSegments(segmentIds.get(0)).getV2Idx());
            }
            for (int i = 1; i < segmentIds.size(); i++) {
                if (mesh.getVertices(mesh.getSegments(segmentIds.get(i)).getV1Idx()) == pointer) {
                    verticesList.add(mesh.getVertices(mesh.getSegments(segmentIds.get(i)).getV2Idx()));
                    pointer = mesh.getVertices(mesh.getSegments(segmentIds.get(i)).getV2Idx());
                } else {
                    verticesList.add(mesh.getVertices(mesh.getSegments(segmentIds.get(i)).getV1Idx()));
                    pointer = mesh.getVertices(mesh.getSegments(segmentIds.get(i)).getV1Idx());
                }
            }
            for (int i = 0; i < verticesList.size(); i++) {
                Vertex v = verticesList.get(i);
                vertex_x[i] = (int) v.getX();
                vertex_y[i] = (int) v.getY();
            }
            canvas.fillPolygon(vertex_x, vertex_y, verticesList.size());
        }

        // draw all segments
        for (Segment segment : mesh.getSegmentsList()) {
            logger.trace("drawing segment: {}", segment);
            Vertex a = vertices.get(segment.getV1Idx());
            Vertex b = vertices.get(segment.getV2Idx());

            // thickness
            Stroke stroke = new BasicStroke(extractThickness(segment.getPropertiesList()), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            canvas.setStroke(stroke);

            // set segment color to black if in debug mode
            if (isDebug) {
                canvas.setColor(new Color(0, 0, 0));
            } else {
                canvas.setColor(extractColor(segment.getPropertiesList()));
            }

            Line2D line = new Line2D.Double(a.getX(), a.getY(), b.getX(), b.getY());
            canvas.draw(line);
        }
    }

    private Color extractColor(List<Property> properties) {
        String val = getProperty(properties, "rgb_color");
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        int alpha = Integer.parseInt(raw[3]);
        return new Color(red, green, blue, alpha);
    }

    // extract thickness
    // prop [color: [red,green,blue,alpha]
    //     [Thickness: [int]]
    private int extractThickness(List<Property> properties) {
        String val = getProperty(properties, "thickness");
        if (val == null) {
            return 1;
        }
        return Integer.parseInt(val);
    }

    // extract centroid; [True]
    private boolean extractIsCentroid(List<Property> properties) {
        String val = getProperty(properties, "centroid");
        if (val == null) {
            return false;
        }

        return val.equals("true");
    }

    private String getProperty(List<Property> properties, String key) {
        for (Property p : properties) {
            if (p.getKey().equals(key)) {
                return p.getValue();
            }
        }
        return null;
    }
}
