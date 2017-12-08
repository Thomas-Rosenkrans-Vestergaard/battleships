/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1;

import battleship.interfaces.Board;
import battleship.interfaces.Ship;
import org.junit.Test;
import static org.junit.Assert.*;
import r1.Position.Direction;

/**
 *
 * @author Skole
 */
public class PositionTest {

    public PositionTest() {
    }

    /**
     * Test of distance method, of class Position.
     */
    @Test
    public void testDistanceTo() {

        Position a = new Position(1, 1);
        Position b = new Position(4, 1);
        assertEquals(3, a.distanceTo(b));
        assertEquals(3, b.distanceTo(a));
    }

    @Test
    public void testDistanceToError() {
        Position a = new Position(0, 0);
        Position b = new Position(1, 1);
        assertEquals(-1, a.distanceTo(b));
        assertEquals(-1, b.distanceTo(a));
    }

    @Test
    public void testDistanceToCenter() {
        Position a = new Position(5, 5);
        Position b = new Position(5, 5);
        assertEquals(0, a.distanceTo(b));
        assertEquals(0, b.distanceTo(a));
        assertEquals(0, a.distanceTo(a));
    }

    /**
     * Test of isVertical method, of class Position.
     */
    @Test
    public void testIsVertical() {
        Position a = new Position(0, 0);
        Position b = new Position(1, 0);
        Position c = new Position(0, 1);

        assertTrue(a.isVertical(c));
        assertTrue(c.isVertical(a));

        assertFalse(a.isVertical(b));
        assertFalse(b.isVertical(a));

        assertFalse(b.isVertical(c));
        assertFalse(c.isVertical(b));
    }

    /**
     * Test of isHorizontal method, of class Position.
     */
    @Test
    public void testIsHorizontal() {
        Position a = new Position(0, 0);
        Position b = new Position(1, 0);
        Position c = new Position(0, 1);

        assertTrue(a.isHorizontal(b));
        assertTrue(b.isHorizontal(a));

        assertFalse(a.isHorizontal(c));
        assertFalse(c.isHorizontal(a));

        assertFalse(b.isHorizontal(c));
        assertFalse(c.isHorizontal(b));
    }

    /**
     * Test of sharesLine method, of class Position.
     */
    @Test
    public void testSharesLine() {
        Position a = new Position(0, 0);
        Position b = new Position(1, 0);
        Position c = new Position(0, 1);

        assertTrue(a.sharesLine(b));
        assertTrue(b.sharesLine(a));

        assertTrue(a.sharesLine(c));
        assertTrue(c.sharesLine(a));

        assertFalse(b.sharesLine(c));
        assertFalse(c.sharesLine(b));
    }

    /**
     * Test of getDirectionTo method, of class Position.
     */
    @Test
    public void testGetDirectionTo() {
        Position center = new Position(2, 2);
        Position top = new Position(2, 3);
        Position bottom = new Position(2, 1);
        Position left = new Position(1, 2);
        Position right = new Position(3, 2);

        assertEquals(Direction.TOP, center.getDirectionTo(top));
        assertEquals(Direction.BOTTOM, top.getDirectionTo(center));

        assertEquals(Direction.LEFT, center.getDirectionTo(left));
        assertEquals(Direction.RIGHT, left.getDirectionTo(center));

        assertEquals(Direction.RIGHT, center.getDirectionTo(right));
        assertEquals(Direction.LEFT, right.getDirectionTo(left));

        assertEquals(Direction.BOTTOM, center.getDirectionTo(bottom));
        assertEquals(Direction.TOP, bottom.getDirectionTo(center));

        assertEquals(Direction.CENTER, center.getDirectionTo(center));
        assertEquals(Direction.CENTER, right.getDirectionTo(right));
    }

    /**
     * Test of toIndex method, of class Position.
     */
    @Test
    public void testToIndex() {
        Position a = new Position(0, 0);
        Position b = new Position(1, 0);
        Position c = new Position(9, 0);
        Position d = new Position(0, 9);
        Position e = new Position(9, 9);

        assertEquals(0, a.toIndex(10));
        assertEquals(1, b.toIndex(10));
        assertEquals(9, c.toIndex(10));
        assertEquals(90, d.toIndex(10));
        assertEquals(99, e.toIndex(10));

    }
    
    @Test
    public void testEquals(){
        assertEquals(new battleship.interfaces.Position(2,6), new battleship.interfaces.Position(2, 6));
    }

    @Test
    public void testInside() throws Exception {
        PositionedArea area = new PositionedArea(5, 5, new Position(0, 0));
        
        assertTrue(new Position(0, 0).inside(area));
        assertTrue(new Position(0, 4).inside(area));
        assertTrue(new Position(4, 4).inside(area));
        assertTrue(new Position(4, 0).inside(area));

        assertFalse(new Position(-1, -1).inside(area));
        assertFalse(new Position(0, 5).inside(area));
        assertFalse(new Position(5, 5).inside(area));
        assertFalse(new Position(5, 0).inside(area));
    }
}
