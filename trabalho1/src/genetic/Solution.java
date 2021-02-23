package genetic;

public class Solution {
    private double x;
    private double y;

    public Solution(double x, double y) {
        this.x = x;
        this.y = y;
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

    @Override
    public String toString() {
        return "Solution{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
