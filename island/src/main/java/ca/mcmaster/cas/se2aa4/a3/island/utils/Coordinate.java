package ca.mcmaster.cas.se2aa4.a3.island.utils;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.Objects;

public class Coordinate {
    public final double x;
    public final double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean approxEquals(Coordinate other) {
        return Math.abs(x - other.x) < 0.01 && Math.abs(y - other.y) < 0.01;
    }

    public double distance(Coordinate other) {
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static Coordinate fromVertex(Structs.Vertex vertex) {
        return new Coordinate(vertex.getX(), vertex.getY());
    }
}
