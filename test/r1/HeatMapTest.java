package r1;

import battleship.interfaces.Position;
import org.junit.Test;
import static org.junit.Assert.*;

public class HeatMapTest {

    @Test
    public void testConstructorTwoArgs() throws Exception {
        HeatMap map = new HeatMap(4, 6);
        assertEquals(4, map.getSizeX());
        assertEquals(6, map.getSizeY());
        assertEquals(0, map.get(new Position(0, 0)));
        map.put(new Position(0, 0));
        assertEquals(1, map.get(new Position(0, 0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorTwoArgsThrowsIllegalArgumentException() throws Exception {
        new HeatMap(0, 0);
    }

    @Test
    public void testConstructorThreeArgs() throws Exception {
        HeatMap map = new HeatMap(4, 6, 5);
        assertEquals(4, map.getSizeX());
        assertEquals(6, map.getSizeY());
        assertEquals(5, map.get(new Position(0, 0)));
        map.put(new Position(0, 0));
        assertEquals(6, map.get(new Position(0, 0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThreeArgsThrowsIllegalArgumentException() throws Exception {
        new HeatMap(1, 0, 0, 1);
    }

    @Test
    public void testConstructorFourArgs() throws Exception {
        HeatMap map = new HeatMap(4, 6, 5, 2);
        assertEquals(4, map.getSizeX());
        assertEquals(6, map.getSizeY());
        assertEquals(5, map.get(new Position(0, 0)));
        map.put(new Position(0, 0));
        assertEquals(7, map.get(new Position(0, 0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorFourArgsThrowsIllegalArgumentException() throws Exception {
        new HeatMap(1, -7, 0, 1);
    }

    @Test
    public void testPut() throws Exception {
        HeatMap map = new HeatMap(1, 1);
        assertEquals(0, map.get(new Position(0, 0)));
        map.put(new Position(0, 0));
        assertEquals(1, map.get(new Position(0, 0)));
    }

    @Test
    public void testPutNonDefaultIncrement() throws Exception {
        HeatMap map = new HeatMap(1, 1, 0, 5);
        assertEquals(0, map.get(new Position(0, 0)));
        map.put(new Position(0, 0));
        assertEquals(5, map.get(new Position(0, 0)));
    }

    @Test(expected = HeatMapOutOfBoundsException.class)
    public void testPutThrowsHeatMapOutOfBoundsException() throws Exception {
        HeatMap map = new HeatMap(1, 1, 0, 5);
        map.get(new Position(-1, 0));
    }

    @Test
    public void testGet() throws Exception {
        HeatMap map = new HeatMap(1, 1);
        assertEquals(0, map.get(new Position(0, 0)));
        map.put(new Position(0, 0));
        assertEquals(1, map.get(new Position(0, 0)));
    }

    @Test(expected = HeatMapOutOfBoundsException.class)
    public void testGetThrowsHeatMapOutOfBoundsException() throws Exception {
        HeatMap map = new HeatMap(1, 1, 0, 5);
        map.get(new Position(4, 5));
    }

    @Test
    public void testReset() throws Exception {
        HeatMap map = new HeatMap(2, 2, 5);

        assertEquals(5, map.get(new Position(0, 0)));
        assertEquals(5, map.get(new Position(0, 1)));
        assertEquals(5, map.get(new Position(1, 0)));
        assertEquals(5, map.get(new Position(1, 1)));

        map.reset();

        assertEquals(0, map.get(new Position(0, 0)));
        assertEquals(0, map.get(new Position(0, 1)));
        assertEquals(0, map.get(new Position(1, 0)));
        assertEquals(0, map.get(new Position(1, 1)));
    }

    @Test
    public void testResetTo() throws Exception {
        HeatMap map = new HeatMap(2, 2, 5);

        assertEquals(5, map.get(new Position(0, 0)));
        assertEquals(5, map.get(new Position(0, 1)));
        assertEquals(5, map.get(new Position(1, 0)));
        assertEquals(5, map.get(new Position(1, 1)));

        map.reset(10);

        assertEquals(10, map.get(new Position(0, 0)));
        assertEquals(10, map.get(new Position(0, 1)));
        assertEquals(10, map.get(new Position(1, 0)));
        assertEquals(10, map.get(new Position(1, 1)));
    }
}
