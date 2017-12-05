/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1;

import battleship.interfaces.Position;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Skole
 */
public class HeatMapTest {

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
    public void testGet_int() {
        System.out.println("get");
        int version = 0;
        HeatMap instance = null;
        Map<Integer, Integer> expResult = null;
        Map<Integer, Integer> result = instance.get(version);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class HeatMap.
     */
    @Test
    public void testGet_int_int() {
        System.out.println("get");
        int from = 0;
        int to = 0;
        HeatMap instance = null;
        Map<Integer, Integer> expResult = null;
        Map<Integer, Integer> result = instance.get(from, to);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirst method, of class HeatMap.
     */
    @Test
    public void testGetFirst_0args() {
        System.out.println("getFirst");
        HeatMap instance = null;
        Map<Integer, Integer> expResult = null;
        Map<Integer, Integer> result = instance.getFirst();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirst method, of class HeatMap.
     */
    @Test
    public void testGetFirst_int() {
        System.out.println("getFirst");
        int n = 0;
        HeatMap instance = null;
        Map<Integer, Integer> expResult = null;
        Map<Integer, Integer> result = instance.getFirst(n);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLast method, of class HeatMap.
     */
    @Test
    public void testGetLast_0args() {
        System.out.println("getLast");
        HeatMap instance = null;
        Map<Integer, Integer> expResult = null;
        Map<Integer, Integer> result = instance.getLast();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLast method, of class HeatMap.
     */
    @Test
    public void testGetLast_int() {
        System.out.println("getLast");
        int n = 0;
        HeatMap instance = null;
        Map<Integer, Integer> expResult = null;
        Map<Integer, Integer> result = instance.getLast(n);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of version method, of class HeatMap.
     */
    @Test
    public void testVersion() {
        System.out.println("version");
        HeatMap instance = null;
        int expResult = 0;
        int result = instance.version();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of increment method, of class HeatMap.
     */
    @Test
    public void testIncrement() throws Exception {
        System.out.println("increment");
        Position position = null;
        HeatMap instance = null;
        instance.increment(position);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toIndex method, of class HeatMap.
     */
    @Test
    public void testToIndex() {
        System.out.println("toIndex");
        int x = 0;
        int y = 0;
        HeatMap instance = null;
        int expResult = 0;
        int result = instance.toIndex(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSizeX method, of class HeatMap.
     */
    @Test
    public void testGetSizeX() {
        System.out.println("getSizeX");
        HeatMap instance = null;
        int expResult = 0;
        int result = instance.getSizeX();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSizeY method, of class HeatMap.
     */
    @Test
    public void testGetSizeY() {
        System.out.println("getSizeY");
        HeatMap instance = null;
        int expResult = 0;
        int result = instance.getSizeY();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
