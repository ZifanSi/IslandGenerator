package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class MeshDump {
    private static final Logger logger = LogManager.getLogger(MeshDump.class);

    public void dump(String fileName) throws IOException {
        MeshFactory factory = new MeshFactory();
        Mesh aMesh = factory.read(fileName);
        this.dump(aMesh);
    }

    public void dump(Mesh aMesh) {
        List<Vertex> vertices = aMesh.getVerticesList();
        logger.info("|Vertices| = " + vertices.size());
        for (Vertex v : vertices){
            StringBuffer line = new StringBuffer();
            line.append(String.format("(%.2f,%.2f)", v.getX(), v.getY()));
            line.append(" [");
            for (Property p : v.getPropertiesList()) {
                line.append(String.format("%s -> %s, ", p.getKey(), p.getValue()));
            }
            line.append("]");
            logger.info(line);
        }
    }
}
