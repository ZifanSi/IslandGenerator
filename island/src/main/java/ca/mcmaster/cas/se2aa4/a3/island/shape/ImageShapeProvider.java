package ca.mcmaster.cas.se2aa4.a3.island.shape;

import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImageShapeProvider extends ShapeProvider {
    private final double scale;
    private final BufferedImage image;
    private final HashMap<Coordinate, Boolean> cache = new HashMap<>();

    public ImageShapeProvider(double width, double height, String filePath) throws IOException {
        File file = new File(filePath);
        image = ImageIO.read(file);
        scale = width / image.getWidth();
    }

    @Override
    public boolean isLand(Coordinate coordinate) {
        coordinate = new Coordinate((int) (coordinate.x / scale), (int) (coordinate.y / scale));
        if (cache.containsKey(coordinate)) {
            return cache.get(coordinate);
        }
        try {
            int color = image.getRGB((int) coordinate.x, (int) coordinate.y);
            int red = (color & 0x00ff0000) >> 16;
            int green = (color & 0x0000ff00) >> 8;
            int blue = color & 0x000000ff;

            boolean result = red >= 10 || green >= 10 || blue >= 10;
            cache.put(coordinate, result);
            return result;
        } catch (Exception e) {
            return false;
        }
    }
}
