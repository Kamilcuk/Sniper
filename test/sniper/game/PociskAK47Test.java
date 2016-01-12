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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author deser
 */
public class PociskAK47Test {
    
    public PociskAK47Test() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test przemieszczania się pocisku AK47
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        PociskAK47 instance = new PociskAK47(10, 10, "0",new Point2D(20,10), 0);
        assertEquals(20, instance.node.getTranslateX(),0.0);
        assertEquals(10, instance.node.getTranslateY(),0.0);
        instance.update();
        assertEquals(20, instance.node.getTranslateX(),0.0);
        assertEquals(0, instance.node.getTranslateY(),0.0);
        
        
        PociskAK47 instance2 = new PociskAK47(10, 10, "0",new Point2D(10,20), 90);
        assertEquals(10, instance2.node.getTranslateX(),0.0);
        assertEquals(20, instance2.node.getTranslateY(),0.0);
        instance2.update();
        assertEquals(20, instance2.node.getTranslateX(),0.0);
        assertEquals(20, instance2.node.getTranslateY(),0.0);
    }
}
