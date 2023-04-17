package ca.mcmaster.cas.se2aa4.a2.generator.adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.Random;

public class Color implements PropertyADT {
    public int red;
    public int green;
    public int blue;
    public int alpha;

    public Color(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public Structs.Property toProperty() {
        return Structs.Property.newBuilder().setKey("rgb_color").setValue(toString()).build();
    }

    public static Color random() {
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public static Color average(Color a, Color b) {
        return new Color(
                (a.red + b.red) / 2,
                (a.green + b.green) / 2,
                (a.blue + b.blue) / 2,
                (a.alpha + b.alpha) / 2
        );
    }

    @Override
    public String toString() {
        return red + "," + green + "," + blue + "," + alpha;
    }
}