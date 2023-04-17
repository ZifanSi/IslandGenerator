package ca.mcmaster.cas.se2aa4.a2.generator.adt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ColorTest {
    @Test
    public void colorAverageTest(){
        Color color1 = new Color(1,2,3,4);
        Color color2 = new Color(4,5,6,8);
        Color expected = Color.average(color1, color2);
        assertEquals(expected.red, 2);
        assertEquals(expected.green, 3);
        assertEquals(expected.blue, 4);
        assertEquals(expected.alpha, 6);
    }

}
