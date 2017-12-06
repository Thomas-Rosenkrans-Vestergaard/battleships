/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1.heatmap;

import org.junit.Test;
import static org.junit.Assert.*;
import r1.Position;

/**
 *
 * @author Skole
 */
public class HeatMapTest {

    /**
     * Test of makeVersion method, of class HeatMap.
     */
    @Test
    public void testMakeVersion() {
        HeatMap hm = new HeatMap(2, 2);
        assertEquals(0, hm.makeVersion(true));
        assertEquals(0, hm.getActiveVersionNumber());
        assertEquals(1, hm.makeVersion(false));
        assertEquals(0, hm.getActiveVersionNumber());
    }

    /**
     * Test of setCurrentVersion method, of class HeatMap.
     */
    @Test
    public void testSetCurrentVersionNumber() throws Exception {
        HeatMap hm = new HeatMap(2, 2);

        hm.makeVersion(false);
        hm.makeVersion(false);

        HeatMapVersion a = hm.getVersion(0);
        HeatMapVersion b = hm.getVersion(1);

        hm.setActiveVersion(0);
        hm.increment(new Position(0, 0));

        assertEquals(1, a.getValue(new Position(0, 0)));
        assertEquals(0, b.getValue(new Position(0, 0)));

        hm.setActiveVersion(1);
        hm.increment(new Position(0, 0));

        assertEquals(1, a.getValue(new Position(0, 0)));
        assertEquals(1, b.getValue(new Position(0, 0)));
    }

    /**
     * Test of getCurrentVersion method, of class HeatMap.
     */
    @Test
    public void testGetCurrentVersion() {

    }

    /**
     * Test of getVersion method, of class HeatMap.
     */
    @Test
    public void testGetVersion() throws Exception {

    }

    /**
     * Test of get method, of class HeatMap.
     */
    @Test
    public void testGet_0args() {
    }

    /**
     * Test of get method, of class HeatMap.
     */
    @Test
    public void testGet_int() throws Exception {
        HeatMap hm = new HeatMap(2, 2);

        hm.makeVersion(false);
        hm.makeVersion(false);

        assertSame(hm.getVersion(0), hm.get(0));
        assertSame(hm.getVersion(1), hm.get(1));
    }

    /**
     * Test of get method, of class HeatMap.
     */
    @Test
    public void testGet_int_int() {
        
    }

    /**
     * Test of getFirst method, of class HeatMap.
     */
    @Test
    public void testGetFirst_0args() {
    }

    /**
     * Test of getFirst method, of class HeatMap.
     */
    @Test
    public void testGetFirst_int() throws Exception {
        HeatMap hm = new HeatMap(2, 2);
        hm.makeVersion(false);
        hm.makeVersion(false);

        HeatMapVersion a = hm.getVersion(0);
        assertSame(a, hm.getFirstVersion());
    }

    /**
     * Test of getLast method, of class HeatMap.
     */
    @Test
    public void testGetLast_0args() throws Exception {
        HeatMap hm = new HeatMap(2, 2);
        hm.makeVersion(false);
        hm.makeVersion(false);

        HeatMapVersion a = hm.getVersion(1);
        assertSame(a, hm.getLastVersion());
    }

    /**
     * Test of getLast method, of class HeatMap.
     */
    @Test
    public void testGetLast_int() {
    }

    /**
     * Test of increment method, of class HeatMap.
     */
    @Test
    public void testIncrement() throws Exception {
    }

    /**
     * Test of toIndex method, of class HeatMap.
     */
    @Test
    public void testToIndex() {
    }

    /**
     * Test of getSizeX method, of class HeatMap.
     */
    @Test
    public void testGetSizeX() {
    }

    /**
     * Test of getSizeY method, of class HeatMap.
     */
    @Test
    public void testGetSizeY() {
    }

    /**
     * Test of getIncrement method, of class HeatMap.
     */
    @Test
    public void testGetIncrement() {
    }

    /**
     * Test of setIncrement method, of class HeatMap.
     */
    @Test
    public void testSetIncrement() {
    }

}
