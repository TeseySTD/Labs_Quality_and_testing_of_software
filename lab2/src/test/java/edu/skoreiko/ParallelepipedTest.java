package edu.skoreiko;

import org.junit.Test;

import static junit.framework.TestCase.*;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab2
 * @class ParallelepipedTest
 * @since 06.03.2025 - 15.44
 */
public class ParallelepipedTest {

    @Test
    public void testVolume() {
        Parallelepiped p = new Parallelepiped(2, 3, 4);
        assertEquals("Volume should be 24", 24.0, p.volume());
    }

    @Test
    public void testSurfaceArea() {
        Parallelepiped p = new Parallelepiped(2, 3, 4);
        assertEquals("Surface area should be 52", 52.0, p.surfaceArea());
    }

    @Test
    public void testSpaceDiagonal() {
        Parallelepiped p = new Parallelepiped(1, 2, 2);
        assertEquals("Space diagonal should be 3",3, p.spaceDiagonal(), 0.0001);
    }

    @Test
    public void testIsCube() {
        Parallelepiped cube = new Parallelepiped(3, 3, 3);
        Parallelepiped nonCube = new Parallelepiped(3, 4, 5);
        assertTrue( "Should be a cube", cube.isCube());
        assertFalse("Should not be a cube", nonCube.isCube());
    }

    @Test
    public void testScale() {
        Parallelepiped p = new Parallelepiped(2, 3, 4);
        p.scale(2);

        assertEquals("Length should be scaled to 4",4, p.getLength(), 0.0001);
        assertEquals( "Width should be scaled to 6", 6, p.getWidth(), 0.0001);
        assertEquals("Height should be scaled to 8", 8, p.getHeight(), 0.0001);
        assertEquals("Volume should be 192 after scaling",192, p.volume(), 0.0001);
    }
}