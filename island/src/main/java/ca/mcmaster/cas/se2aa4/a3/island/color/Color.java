package ca.mcmaster.cas.se2aa4.a3.island.color;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class Color {
    final int red;
    final int green;
    final int blue;
    public int alpha;

    public Color(int red, int green, int blue, int alpha) {
        // clamp values from 0-255
        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));
        alpha = Math.max(0, Math.min(255, alpha));

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public Color(int red, int green, int blue) {
        this(red, green, blue, 255);
    }

    public Color(int gray) {
        this(gray, gray, gray);
    }

    public String toString() {
        return red + "," + green + "," + blue + "," + alpha;
    }

    public Structs.Property toProperty() {
        return Structs.Property.newBuilder().setKey("rgb_color").setValue(toString()).build();
    }

    public static Color interpolate(Color a, Color b, double ratio) {
        int red = (int) (a.red + (b.red - a.red) * ratio);
        int green = (int) (a.green + (b.green - a.green) * ratio);
        int blue = (int) (a.blue + (b.blue - a.blue) * ratio);
        int alpha = (int) (a.alpha + (b.alpha - a.alpha) * ratio);
        return new Color(red, green, blue, alpha);
    }
}
