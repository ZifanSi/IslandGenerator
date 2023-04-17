import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.ColorProviderFactory;
import ca.mcmaster.cas.se2aa4.a3.island.IslandGenerator;
import ca.mcmaster.cas.se2aa4.a3.island.color.ColorProvider;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Segment;
import org.apache.commons.cli.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final Option SHAPE = new Option("s", "shape", true, "");
    private static final Option ELEVATION = new Option("e", "elevation", true, "");
    private static final Option ELEVATION_NOISE = new Option("n", "elevationNoise", true, "");
    private static final Option MAX_LAKES = new Option("l", "maxLakes", true, "");
    private static final Option MAX_RIVERS = new Option("r", "maxRivers", true, "");
    private static final Option MAX_AQUIFERS = new Option("a", "maxAquifers", true, "");
    private static final Option SOIL_ABSORPTION_FACTOR = new Option("f", "soilAbsorptionFactor", true, "");
    private static final Option SEED = new Option("d", "seed", true, "");
    private static final Option HEATMAP = new Option("h", "heatmap", true, "");

    public static void main(String[] args) throws IOException {
        //java -jar island.jar -i input.mesh -o lagoon.mesh --mode lagoon
        Options options = new Options();
        options.addOption(SHAPE);
        options.addOption(ELEVATION);
        options.addOption(ELEVATION_NOISE);
        options.addOption(MAX_LAKES);
        options.addOption(MAX_RIVERS);
        options.addOption(MAX_AQUIFERS);
        options.addOption(SOIL_ABSORPTION_FACTOR);
        options.addOption(SEED);
        options.addOption(HEATMAP);

        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.getArgList().size() < 2) {
                System.out.println("Invalid arguments");
                System.exit(-1);
            }

            String shape = "apple";
            String elevation = "seaDistance";
            double elevationNoise = 100;
            int maxLakes = 4;
            int maxRivers = 20;
            int maxAquifers = 4;
            double soilAbsorptionFactor = 1;
            long seed = new Random().nextLong();
            String heatmap = "";

            if (cmd.hasOption(SHAPE.getOpt())) {
                shape = cmd.getOptionValue(SHAPE.getOpt());
            }
            if (cmd.hasOption(ELEVATION.getOpt())) {
                elevation = cmd.getOptionValue(ELEVATION.getOpt());
            }
            if (cmd.hasOption(ELEVATION_NOISE.getOpt())) {
                elevationNoise = Double.parseDouble(cmd.getOptionValue(ELEVATION_NOISE.getOpt()));
            }
            if (cmd.hasOption(MAX_LAKES.getOpt())) {
                maxLakes = Integer.parseInt(cmd.getOptionValue(MAX_LAKES.getOpt()));
            }
            if (cmd.hasOption(MAX_RIVERS.getOpt())) {
                maxRivers = Integer.parseInt(cmd.getOptionValue(MAX_RIVERS.getOpt()));
            }
            if (cmd.hasOption(MAX_AQUIFERS.getOpt())) {
                maxAquifers = Integer.parseInt(cmd.getOptionValue(MAX_AQUIFERS.getOpt()));
            }
            if (cmd.hasOption(SOIL_ABSORPTION_FACTOR.getOpt())) {
                soilAbsorptionFactor = Double.parseDouble(cmd.getOptionValue(SOIL_ABSORPTION_FACTOR.getOpt()));
            }
            if (cmd.hasOption(SEED.getOpt())) {
                seed = Long.parseLong(cmd.getOptionValue(SEED.getOpt()));
            }
            if (cmd.hasOption(HEATMAP.getOpt())) {
                heatmap = cmd.getOptionValue(HEATMAP.getOpt());
            }

            Structs.Mesh mesh = new MeshFactory().read(args[0]);
            double width = Double.MIN_VALUE;
            double height = Double.MIN_VALUE;
            for (Structs.Vertex v : mesh.getVerticesList()) {
                width = (Double.compare(width, v.getX()) < 0 ? v.getX() : width);
                height = (Double.compare(height, v.getY()) < 0 ? v.getY() : height);
            }

            List<Segment> segments = new ArrayList<>();
            for (Structs.Segment s : mesh.getSegmentsList()) {
                segments.add(Segment.fromSegment(s, mesh.getVerticesList()));
            }

            ColorProvider colorProvider = ColorProviderFactory.createColorProvider(width, height, shape, elevation, elevationNoise, maxLakes, segments, maxRivers, maxAquifers, soilAbsorptionFactor, seed, heatmap);

            IslandGenerator islandGenerator = new IslandGenerator(colorProvider);
            mesh = islandGenerator.generate(mesh);
            mesh.writeTo(new FileOutputStream(args[1]));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
