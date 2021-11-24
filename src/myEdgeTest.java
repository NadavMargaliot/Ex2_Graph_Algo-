import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class myEdgeTest {
    myEdge def = new myEdge(2,3,13);
    myEdge a = new myEdge(1 , 4, 8.5 , 1 , "abc");

    @Test
    void getSrc() {
        assertEquals(1 , a.getSrc());
        assertEquals(2 , def.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(4 , a.getDest());
        assertEquals(3 , def.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(8.5 , a.getWeight());
        assertEquals(13 , def.getWeight());
        assertEquals(21.5 , def.getWeight() + a.getWeight());

    }

    @Test
    void getInfo() {
        assertNull( def.getInfo());
        assertEquals("abc" , a.getInfo());
    }

    @Test
    void setInfo() {
        assertEquals("abc" , a.getInfo());
        a.setInfo("change");
        assertEquals("change" , a.getInfo());

    }

    @Test
    void getTag() {
        assertEquals(1 , a.getTag());
        assertEquals(0 , def.getTag());
    }

    @Test
    void setTag() {
        assertEquals(1 , a.getTag());
        a.setTag(18);
        assertEquals(18 , a.getTag());
    }
}