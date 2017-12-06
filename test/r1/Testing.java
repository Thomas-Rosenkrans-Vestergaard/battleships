package r1;

import org.junit.Test;
import r1.heatmap.HeatMap;

/**
 *
 * @author Thomas
 */
public class Testing {
    
    @Test
    public void test() throws Exception {

        
        HeatMap hm = new HeatMap(10, 10);
        hm.makeVersion(true);
        
        

        

        //3 Matrix 
        for (int j = 0; j < 10; j++) //j svarer til sizeY
        {
            
            for (int i = 19; i >= 0; i -= 3) //i svarer til 2'sizeX
            {
                if ((i - j) >= 0 && (i - j) < 10) {
                    hm.setToValue(new Position(i-j, j), 3);
                }
            }
            
        }

        //2 Matrix
        for (int j = 0; j < 10; j++) //j svarer til sizeY
        {
            for (int i = 19; i >= 0; i -= 2) //i svarer til 2'sizeX
            {
                if ((i - j) >= 0 && (i - j) < 10) {
                   // hm.setToValue(new Position(i - j, j), 2);
                }
            }
            
        }
        
        //4 Matrix
        for (int j = 0; j < 10; j++) //j svarer til sizeY
        {
            for (int i = 19; i >= 0; i -= 4) //i svarer til 2'sizeX
            {
                if ((i - j) >= 0 && (i - j) < 10) {
                    hm.setToValue(new Position(i - j, j), 4);
                }
            }

        }
        
        System.out.println(hm.getActiveVersion());
        
    }
}
