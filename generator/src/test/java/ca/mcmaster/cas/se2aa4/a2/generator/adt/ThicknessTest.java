package ca.mcmaster.cas.se2aa4.a2.generator.adt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ThicknessTest {
    @Test
    public void toProperty(){
        Thickness thickness = new Thickness(1);
        assertNotNull(thickness.toProperty());
    }
}
