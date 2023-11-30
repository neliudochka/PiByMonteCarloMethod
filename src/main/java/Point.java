//point for monte carlo method to calculate Pi
public class Point {
    private double x;
    private double y;
    public Point() {
        x = Math.random();
        y = Math.random();
    }

    public double distance () {
        //(from (0;0)
        return Math.sqrt((x*x+y*y));
    }
}
