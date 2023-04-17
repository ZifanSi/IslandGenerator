package ca.mcmaster.cas.se2aa4.a3.island.utils;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;
import java.util.Objects;

public class Segment {
    public final Coordinate start;
    public final Coordinate end;

    public Segment(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    public boolean approxEquals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Segment rhs = (Segment) obj;
        return start.approxEquals(rhs.start) && end.approxEquals(rhs.end) || start.approxEquals(rhs.end) && end.approxEquals(rhs.start);
    }

    public Coordinate center() {
        return new Coordinate((start.x + end.x) / 2, (start.y + end.y) / 2);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Segment rhs = (Segment) obj;
        return start.equals(rhs.start) && end.equals(rhs.end) || start.equals(rhs.end) && end.equals(rhs.start);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end) + Objects.hash(end, start);
    }

    static public Segment fromSegment(Structs.Segment segment, List<Structs.Vertex> vertices) {
        return new Segment(Coordinate.fromVertex(vertices.get(segment.getV1Idx())), Coordinate.fromVertex(vertices.get(segment.getV2Idx())));
    }
}
