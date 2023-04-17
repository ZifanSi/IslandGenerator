import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.generator.SquareDotGen;
import ca.mcmaster.cas.se2aa4.a2.generator.TriangleDotGen;
import ca.mcmaster.cas.se2aa4.a2.generator.VoronoiDotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    private static final Option GRID = new Option("g", "grid", false, "create a grid mesh");
    private static final Option IRREGULAR = new Option("i", "irregular", false, "create an irregular mesh");
    private static final Option TRIANGLE = new Option("t", "triangle", false, "create an triangle mesh");

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        PrintWriter pw = new PrintWriter(System.out);
        formatter.printUsage(pw, 100, "java -jar generator.jar sample.mesh [options] width height squareSize/sitesCount segmentThickness vertexThickness (relaxation)");
        formatter.printOptions(pw, 100, options, 2, 5);
        pw.close();
    }

    public static void main(String[] args) throws IOException {
        Options options = new Options();
        options.addOption(GRID);
        options.addOption(IRREGULAR);
        options.addOption(TRIANGLE);

        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.getArgList().size() < 1) {
                printHelp(options);
                System.exit(-1);
            }

            int width = 0;
            int height = 0;
            int squareSize = 0;
            int segmentThickness = 0;
            int vertexThickness = 0;
            int relaxation = 0;
            try {
                width = Integer.parseInt(cmd.getArgList().get(1));
                height = Integer.parseInt(cmd.getArgList().get(2));
                squareSize = Integer.parseInt(cmd.getArgList().get(3));
                segmentThickness = Integer.parseInt(cmd.getArgList().get(4));
                vertexThickness = Integer.parseInt(cmd.getArgList().get(5));
                if (cmd.hasOption("-i")) {
                    relaxation = Integer.parseInt(cmd.getArgList().get(6));
                    if (relaxation < 1) {
                        throw new IllegalArgumentException("Relaxation must > 0");
                    }
                }
            } catch (Exception q) {
                printHelp(options);
                q.printStackTrace();
                System.exit(-1);
            }

            DotGen generator;
            if (cmd.hasOption("-g")) {
                generator = new SquareDotGen(width, height, squareSize, segmentThickness, vertexThickness);
            } else if (cmd.hasOption("-i")) {
                generator = new VoronoiDotGen(width, height, squareSize, segmentThickness, vertexThickness, relaxation);
            } else if (cmd.hasOption("-t")) {
                generator = new TriangleDotGen(width, height, squareSize, segmentThickness, vertexThickness);
            } else {
                printHelp(options);
                return;
            }

            Mesh mesh = generator.generateMesh();
            MeshFactory factory = new MeshFactory();
            factory.write(mesh, args[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
