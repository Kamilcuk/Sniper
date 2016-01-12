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
public class PociskSniperTest {
    
    public PociskSniperTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test tworzenia i odświerzania obiektu typu PociskSniper
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        PociskSniper instance = new PociskSniper(10, 10, "0", new Point2D(1,2), 90);
        assertEquals(10, instance.getBulletAttack(),0.0);
        instance.update();
        assertEquals(0, instance.getBulletAttack(),0.0);
    }

    
}
