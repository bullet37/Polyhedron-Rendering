package tz.polyhedron;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Point3D {
    private double x;
    private double y;
    private double z;
    private final int radius;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = 9;
    }

    // getter & setter
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public double getZ() {
        return this.z;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setZ(double z) {
        this.z = z;
    }

    public Point getPoint() {
        int width = Display.WIDTH;
        int xIn2D = (int) (width/ 2 + this.x*Display.windoScale);
        int yIn2D = (int) (width / 2 - this.y*Display.windoScale);
        return new Point(xIn2D, yIn2D);
    }

    public void draw(Graphics g){
        Graphics2D g2D = (Graphics2D)g;
        Point p = this.getPoint();
        double r = this. radius*Display.windoScale;
        Ellipse2D e = new Ellipse2D.Double(p.x - r, p.y - r, r*2, r*2);
        g2D.fill(e);
        //g.drawOval(p.x - radius, p.y - radius, radius*2, radius*2);
    }

    /**
     * Rotate according to the X,Y,Z axis in turn
     */
    public void rotate(double xDegree, double yDegree, double zDegree){
        double d1 = getDistance(y,z);
        double a1 = getAngle(z,y,xDegree);
        z = (d1 * Math.sin(a1));
        y = (d1 * Math.cos(a1));

        double d2 = getDistance(x,z);
        double a2 = getAngle(z,x,yDegree);
        x = (d2 * Math.cos(a2));
        z = (d2 * Math.sin(a2));

        double d3 = getDistance(x,y);
        double a3 = getAngle(y,x,zDegree);
        x = (d3 * Math.cos(a3));
        y = (d3 * Math.sin(a3));
    }

    /**
     * Get the distance to the origin
     */
    private double getDistance (double x, double y){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    /**
     * Add the rotation angle to the original angle
     */
    private double getAngle (double x, double y,double degree){
        return Math.atan2(x,y) + Math.PI * degree /180;
    }
}

