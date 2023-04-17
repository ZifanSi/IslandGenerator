package ca.mcmaster.cas.se2aa4.a2.generator.adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class SegmentADT {
    public final VertexADT start;
    public final VertexADT end;
    public Color color;
    // field of thickness
    public Thickness thickness;
    final int id;

    SegmentADT(VertexADT start, VertexADT end, int id) {
        this.start = start;
        start.segments.add(this);
        this.end = end;
        end.segments.add(this);
        this.id = id;
    }

    public Structs.Segment toSegment() {
        Structs.Segment.Builder builder = Structs.Segment.newBuilder()
                .setV1Idx(start.id)
                .setV2Idx(end.id);
        if (color != null) {
            builder.addProperties(color.toProperty());
        }
        // tell io, or io will not store in .mesh
        if (thickness != null) {
            builder.addProperties(thickness.toProperty());
        }
        return builder.build();
    }

    @Override
    public String toString() {
        return "SegmentADT{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
