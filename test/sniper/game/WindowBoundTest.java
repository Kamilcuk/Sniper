/*
 * Copyright (C) 2015 Kamil Cukrowski
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
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kamil Cukrowski
 */
public class WindowBoundTest {
	
	public WindowBoundTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of setResolution method, of class WindowBound.
	 */
	@Test
	public void testSetResolution() {
		System.out.println("setResolution");
		Point2D expResult = WindowBound.getResolution();
		WindowBound.setResolution(null);
		Point2D result = WindowBound.getResolution();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getResolution method, of class WindowBound.
	 */
	@Test
	public void testGetResolution() {
		System.out.println("getResolution");
		Point2D expResult = new Point2D(100, 100);
		Point2D result = WindowBound.getResolution();
		WindowBound.setResolution(expResult);
		assertEquals(expResult, result);
	}

	/**
	 * Test of jakBliskoCollide method, of class WindowBound.
	 */
	@Test
	public void testJakBliskoCollide() {
		System.out.println("jakBliskoCollide");
		Sprite other = null;
		WindowBound instance = new WindowBound();
		double expResult = Double.POSITIVE_INFINITY;
		double result = instance.jakBliskoCollide(other);
		assertEquals(expResult, result, 0.0);
		
		WindowBound.setResolution(new Point2D(100, 100));
		
		other = (Sprite) new TestingSprite(new Point2D(50, 50));
		assertEquals(50, instance.jakBliskoCollide(other), 0.0);
		
		other = (Sprite) new TestingSprite(new Point2D(-50, 50));
		assertEquals(-50, instance.jakBliskoCollide(other), 0.0);
		
		other = (Sprite) new TestingSprite(new Point2D(150, 50));
		assertEquals(-50, instance.jakBliskoCollide(other), 0.0);
		
		other = (Sprite) new TestingSprite(new Point2D(50, -50));
		assertEquals(-50, instance.jakBliskoCollide(other),  0.0);
		
		other = (Sprite) new TestingSprite(new Point2D(50, 150));
		assertEquals(-50, instance.jakBliskoCollide(other),  0.0);
		
		other = (Sprite) new TestingSprite(new Point2D(-50, -50));
		assertEquals(-Math.sqrt(50*50+50*50), instance.jakBliskoCollide(other), 0.0);
		
		other = (Sprite) new TestingSprite(new Point2D(150, 150));
		assertEquals(-Math.sqrt(50*50+50*50), instance.jakBliskoCollide(other), 0.0);
		
		other = (Sprite) new TestingSprite(new Point2D(150, -50));
		assertEquals(-Math.sqrt(50*50+50*50), instance.jakBliskoCollide(other), 0.0);
		
		other = (Sprite) new TestingSprite(new Point2D(-50, 150));
		assertEquals(-Math.sqrt(50*50+50*50)-4, instance.jakBliskoCollide(other), 0.0);
	}
}
