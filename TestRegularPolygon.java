public class TestRegularPolygon {
    public static void main(String[] args) {
        // Create three RegularPolygon objects
        RegularPolygon polygon1 = new RegularPolygon(); // No-arg constructor
        RegularPolygon polygon2 = new RegularPolygon(6, 4); // 6-sided, side length 4
        RegularPolygon polygon3 = new RegularPolygon(10, 4, 5.6, 7.8); // 10-sided, side length 4, center (5.6, 7.8)

        // Display perimeter and area for each polygon
        System.out.println("Polygon 1 (default):");
        System.out.printf("Perimeter: %.2f\n", polygon1.getPerimeter());
        System.out.printf("Area: %.2f\n", polygon1.getArea());

        System.out.println("\nPolygon 2 (6 sides, side length 4):");
        System.out.printf("Perimeter: %.2f\n", polygon2.getPerimeter());
        System.out.printf("Area: %.2f\n", polygon2.getArea());

        System.out.println("\nPolygon 3 (10 sides, side length 4, center (5.6, 7.8)):");
        System.out.printf("Perimeter: %.2f\n", polygon3.getPerimeter());
        System.out.printf("Area: %.2f\n", polygon3.getArea());
    }
}

class RegularPolygon {
    private int n;        // number of sides
    private double side;  // length of side
    private double x;     // x-coordinate of center
    private double y;     // y-coordinate of center

    // No-arg constructor
    public RegularPolygon() {
        this.n = 3;      // default to triangle
        this.side = 1;   // default side length
        this.x = 0;      // default x coordinate
        this.y = 0;      // default y coordinate
    }

    // Constructor with specified number of sides and side length
    public RegularPolygon(int n, double side) {
        this.n = n;
        this.side = side;
        this.x = 0;      // default x coordinate
        this.y = 0;      // default y coordinate
    }

    // Constructor with specified number of sides, side length, and coordinates
    public RegularPolygon(int n, double side, double x, double y) {
        this.n = n;
        this.side = side;
        this.x = x;
        this.y = y;
    }

    // Accessor and mutator methods
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Method to calculate perimeter
    public double getPerimeter() {
        return n * side;
    }

    // Method to calculate area
    public double getArea() {
        return (n * Math.pow(side, 2)) / (4 * Math.tan(Math.PI / n));
    }
}