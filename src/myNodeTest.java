import api.GeoLocation;

import static org.junit.jupiter.api.Assertions.*;

class myNodeTest {
    myNode a = new myNode();
    myNode b = new myNode(4);
    GeoLocation g = new myGeo(5, 7, 9);
    myNode c = new myNode(5, "abd", 1, g);

    @org.junit.jupiter.api.Test
    void getKey() {
        assertEquals(4, b.getKey());
        assertEquals(0, a.getKey());
        assertEquals(5, c.getKey());
    }

    @org.junit.jupiter.api.Test
    void getLocation() {
        GeoLocation def = new myGeo(0, 0, 0);
        assertEquals(def.x(), a.getLocation().x());
        assertEquals(def.y(), a.getLocation().y());
        assertEquals(def.z(), a.getLocation().z());
        GeoLocation tmp = new myGeo(5, 7, 9);
        assertEquals(tmp.x(), c.getLocation().x());
        assertEquals(tmp.y(), c.getLocation().y());
        assertEquals(tmp.z(), c.getLocation().z());

    }

    @org.junit.jupiter.api.Test
    void setLocation() {
        GeoLocation change = new myGeo(6, 7, 8);
        GeoLocation def = new myGeo(0, 0, 0);
        assertNotEquals(change.x(), a.getLocation().x());
        assertNotEquals(change.y(), a.getLocation().y());
        assertNotEquals(change.z(), a.getLocation().z());
        assertEquals(def.x(), a.getLocation().x());
        assertEquals(def.y(), a.getLocation().y());
        assertEquals(def.z(), a.getLocation().z());
        a.setLocation(change);
        assertEquals(change.x(), a.getLocation().x());
        assertEquals(change.y(), a.getLocation().y());
        assertEquals(change.z(), a.getLocation().z());

    }

    @org.junit.jupiter.api.Test
    void getWeight() {

    }

    @org.junit.jupiter.api.Test
    void setWeight() {
    }

    @org.junit.jupiter.api.Test
    void getInfo() {
        assertNull(a.getInfo());
    }

    @org.junit.jupiter.api.Test
    void setInfo() {
        a.setInfo("hello");
        assertEquals("hello", a.getInfo());
        assertNotEquals("Hello", a.getInfo());
    }

    @org.junit.jupiter.api.Test
    void getTag() {
        assertEquals(0, a.getTag());
    }

    @org.junit.jupiter.api.Test
    void setTag() {
        a.setTag(8);
        assertEquals(8, a.getTag());
    }
}