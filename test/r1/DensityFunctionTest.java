/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1;

import org.junit.Test;
import static org.junit.Assert.*;
import r1.heatmap.HeatMap;
import r1.heatmap.HeatMapView;

/**
 *
 * @author Skole
 */
public class DensityFunctionTest {
    
    public DensityFunctionTest() {
    }

    /**
     * Test of addBlocker method, of class DensityFunction.
     */
    @Test
    public void testAddBlocker_biPosition() {
    }

    /**
     * Test of addBlocker method, of class DensityFunction.
     */
    @Test
    public void testAddBlocker_rPosition() {
    }

    /**
     * Test of addBlocker method, of class DensityFunction.
     */
    @Test
    public void testAddBlocker_PositionedArea() {
    }

    /**
     * Test of addBlocker method, of class DensityFunction.
     */
    @Test
    public void testAddBlocker_int_int() {
    }

    /**
     * Test of addShip method, of class DensityFunction.
     */
    @Test
    public void testAddShip() {
    }

    /**
     * Test of calculate method, of class DensityFunction.
     */
    @Test
    public void testCalculate() throws Exception {
        DensityCalculator df = new DensityCalculator();
        df.addShip(2);
        df.addShip(3);
        df.addShip(3);
        df.addShip(4);
        df.addShip(5);
        df.addBlocker(5, 5);
        HeatMap hm = new HeatMap(10, 10);
        hm.makeVersion(true);
        df.calculate(hm);
        System.out.println(hm.getActiveVersion());
    }
    
}
