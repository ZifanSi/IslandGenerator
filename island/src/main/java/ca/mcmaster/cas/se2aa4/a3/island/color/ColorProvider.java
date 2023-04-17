package ca.mcmaster.cas.se2aa4.a3.island.color;

import ca.mcmaster.cas.se2aa4.a3.island.utils.Coordinate;
import ca.mcmaster.cas.se2aa4.a3.island.utils.Segment;

public interface ColorProvider {
    Color getPolygonColor(Coordinate coordinate);

    Color getSegmentColor(Segment segment);

    int getSegmentThickness(Segment segment);
}