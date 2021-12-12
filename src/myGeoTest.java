import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class myGeoTest {
    myGeo g = new myGeo(1, 2, 3);
    myGeo def = new myGeo();

    @Test
    void x() {
        assertEquals(1, g.x());
        assertEquals(0, def.x());
    }

    @Test
    void y() {
        assertEquals(2, g.y());
        assertEquals(0, def.y());
    }

    @Test
    void z() {
        assertEquals(3, g.z());
        assertEquals(0, def.z());
    }

    @Test
    void distance() {
        double eps = 0.000001;
        myGeo a = new myGeo(7, 4, 3);
        myGeo b = new myGeo(17, 6, 2);
        double dist = 10.246951;
        assertTrue(Math.abs(a.distance(b) - dist) < eps);
    }
}