public class Circle extends GeometricObject implements Comparable<Circle> {
    private double radius;

    /** Default constructor */
    public Circle() {
        this.radius = 1.0;
    }

    /** Construct a circle with specified radius */
    public Circle(double radius) {
        this.radius = radius;
    }

    /** Construct a circle with specified radius, color, and filled status */
    public Circle(double radius, String color, boolean filled) {
        super(color, filled);
        this.radius = radius;
    }

    /** Getter method for radius */
    public double getRadius() {
        return radius;
    }

    /** Setter method for radius */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /** Override the getArea method from GeometricObject */
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    /** Override the getPerimeter method from GeometricObject */
    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    /** Override the compareTo method from Comparable */
    @Override
    public int compareTo(Circle other) {
        return Double.compare(this.radius, other.radius);
    }

    /** Override the equals method from Object */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Circle other = (Circle) obj;
        return Double.compare(this.radius, other.radius) == 0;
    }

    /** Main method for testing the Circle class */
    public static void main(String[] args) {
        // Create two Circle objects
        Circle circle1 = new Circle(5.0, "red", true);
        Circle circle2 = new Circle(5.0, "blue", false);

        // Test the equals method
        System.out.println("Circle1 equals Circle2? " + circle1.equals(circle2));

        // Test the compareTo method
        Circle circle3 = new Circle(7.0);
        System.out.println("Comparing Circle1 to Circle3: " + circle1.compareTo(circle3));

        // Display area and perimeter of circle1
        System.out.println("Area of Circle1: " + circle1.getArea());
        System.out.println("Perimeter of Circle1: " + circle1.getPerimeter());
    }
}
