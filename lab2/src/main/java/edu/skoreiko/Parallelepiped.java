package edu.skoreiko;
/**
    @author Rout
    @project lab2
    @class Parallelepiped
    @version 1.0.0
    @since 06.03.2025 - 13.24
*/
public class Parallelepiped {
    private double length;
    private double width;
    private double height;

    // Constructor
    public Parallelepiped(double length, double width, double height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    /**
     * Calculates the volume of the parallelepiped.
     * @return volume = length * width * height
     */
    public double volume() {
        return length * width * height;
    }

    /**
     * Calculates the surface area of the parallelepiped.
     * @return surface area = 2*(lw + lh + wh)
     */
    public double surfaceArea() {
        return 2 * (length * width + length * height + width * height);
    }

    /**
     * Calculates the length of the space diagonal.
     * @return space diagonal = sqrt(l^2 + w^2 + h^2)
     */
    public double spaceDiagonal() {
        return Math.sqrt(length * length + width * width + height * height);
    }

    /**
     * Checks if the parallelepiped is a cube.
     * @return true if all edges are equal, false otherwise
     */
    public boolean isCube() {
        return length == width && width == height;
    }

    /**
     * Scales the dimensions of the parallelepiped by the given factor.
     * @param factor scale factor
     */
    public void scale(double factor) {
        length *= factor;
        width *= factor;
        height *= factor;
    }
}
