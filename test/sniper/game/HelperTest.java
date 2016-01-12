/*
 * Copyright (C) 2016 deser
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package sniper.game;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author deser
 */
public class HelperTest {
    
    public HelperTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of overrideImage method, of class Helper.
     */
    

    /**
     * Czy liczba losowa z przedziału 0,0 jest z przedziału 0,0
     * Test of Rnd method, of class Helper.
     */
    @Test
    public void testRnd_int_int() {
        System.out.println("Rnd");
        int low = 0;
        int high = 0;
        int expResult = 0;
        int result = Helper.Rnd(low, high);
        assertEquals("bieda",expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * czy losuje zero jezeli 0,0
     * Test of Rnd method, of class Helper.
     */
    @Test
    public void testRnd_int() {
        System.out.println("Rnd");
        int limit = 0;
        int expResult = 0;
        int result = Helper.Rnd(limit);
        assertEquals(result, expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * czy dobry kat
     * Test of GetAngleOfLineBetweenTwoPoints method, of class Helper.
     */
    @Test
    public void testGetAngleOfLineBetweenTwoPoints() {
        System.out.println("GetAngleOfLineBetweenTwoPoints");
        Point2D p1 = new Point2D(2,1);
        Point2D p2 = new Point2D(1,1);
               double expResult = 0.0;
        double result = Helper.GetAngleOfLineBetweenTwoPoints(p1, p2);
        assertEquals(expResult, result, 90);
        // TODO review the generated test code and remove the default call to fail.
       
    }
    
}
