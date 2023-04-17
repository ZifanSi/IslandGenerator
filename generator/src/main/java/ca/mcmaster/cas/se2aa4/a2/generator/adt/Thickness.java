package ca.mcmaster.cas.se2aa4.a2.generator.adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;


public class Thickness implements PropertyADT {
    public int thickness;

    // hold thickness
    public Thickness(int thickness) {
        this.thickness = thickness;
    }

    public Structs.Property toProperty() {
        return Structs.Property.newBuilder().setKey("thickness").setValue(String.valueOf(thickness)).build();
    }
}
