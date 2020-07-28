abstract class Shape {

    abstract double getPerimeter();

    abstract double getArea();
}

class Triangle extends Shape {

    private final double a;
    private final double b;
    private final double c;

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    double getPerimeter() {
        return a + b + c;
    }

    @Override
    double getArea() {
        double s = (a + b + c) / 2.0d;
        double x = (s * (s - a) * (s - b) * (s - c));
        return Math.sqrt(x);
    }
}

class Rectangle extends Shape {

    private final double a;
    private final double b;

    public Rectangle(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    double getPerimeter() {
        return 2 * (a + b);
    }

    @Override
    double getArea() {
        return a * b;
    }
}

class Circle extends Shape {

    private final double a;

    public Circle(double a) {
        this.a = a;
    }

    @Override
    double getPerimeter() {
        return 2 * Math.PI * a;
    }

    @Override
    double getArea() {
        return Math.PI * a * a;
    }
}