/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Skole
 */
public class PositionedAreaTest {
    
    public PositionedAreaTest() {
    }

    /**
     * Test of overlaps method, of class PositionedArea.
     */
    @Test
    public void testOverlaps() {
        PositionedArea a = new PositionedArea(2, 2, new Position(0, 0));
        PositionedArea b = new PositionedArea(2, 2, new Position(2, 0));
        PositionedArea c = new PositionedArea(2, 2, new Position(0, 2));
        PositionedArea d = new PositionedArea(2, 2, new Position(2, 2));
        PositionedArea e = new PositionedArea(2, 2, new Position(1, 1));
        
        assertFalse(a.overlaps(b));
        assertFalse(a.overlaps(c));
        assertFalse(a.overlaps(d));
        
        assertFalse(b.overlaps(a));
        assertFalse(b.overlaps(c));
        assertFalse(b.overlaps(d));
        
        assertFalse(c.overlaps(a));
        assertFalse(c.overlaps(b));
        assertFalse(c.overlaps(d));
        
        assertFalse(d.overlaps(a));
        assertFalse(d.overlaps(b));
        assertFalse(d.overlaps(c));
        
        assertTrue(e.overlaps(a));
        assertTrue(e.overlaps(b));
        assertTrue(e.overlaps(c));
        assertTrue(e.overlaps(d));
        assertTrue(e.overlaps(e));
    }

    /**
     * Test of getPosition method, of class PositionedArea.
     */
    @Test
    public void testGetPosition() {
    }

    /**
     * Test of toString method, of class PositionedArea.
     */
    @Test
    public void testToString() {
    }
    
}
