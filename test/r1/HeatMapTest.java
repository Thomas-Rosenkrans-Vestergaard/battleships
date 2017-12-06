/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r1;

import r1.heatmap.HeatMap;
import battleship.interfaces.Position;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Skole
 */
public class HeatMapTest {

    @Test
    public void test() throws Exception {
        HeatMap hm = new HeatMap(3, 3);
        hm.makeVersion(true);
        hm.increment(new Position(0, 0));
        hm.increment(new Position(0, 2));
        hm.increment(new Position(2, 2));
        PositionedArea result = hm.getVersion(0).getColdestArea(new Area(2, 2));
        System.out.println(result);
    }
    
    private void print(Map<Integer, Integer> map) {
        for (int x = 0; x < map.size(); x++) {
            System.out.format("%6d", map.get(x));
            if (x % 10 == 9) {
                System.out.println("\n");
            }
        }
        System.out.println("");
    } 
}
