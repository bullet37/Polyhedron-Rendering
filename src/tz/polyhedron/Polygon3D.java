package tz.polyhedron;

import java.awt.*;

public class Polygon3D {

    private Point3D[] points;
    private static final Color color = Color.BLUE;

    public Polygon3D(Point3D... pointArray) {
        int len = pointArray.length;
        points = new Point3D[len];
        double tempX,tempY,tempZ;
        for (int i = 0; i < len; ++i) {
            tempX = pointArray[i].getX();
            tempY = pointArray[i].getY();
            tempZ = pointArray[i].getZ();
            points[i] = new Point3D(tempX, tempY, tempZ);
        }
    }

    public void draw(Graphics g) {
        g.setColor(generateShade());
        // Draw the points
        for (Point3D p : points) {
            p.draw(g);
        }
        // Draw the lines
        int len = points.length;
        java.awt.Polygon awtPolygon= new Polygon();
        for (int i = 0; i < len; ++i) {
            Point p1 = points[i].getPoint();
            Point p2 = points[(i+1)%len].getPoint();
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
        for (int i = 0; i < len; ++i) {
            Point p = points[i].getPoint();
            awtPolygon.addPoint(p.x,p.y);
        }
        g.fillPolygon(awtPolygon);
    }

    public void drawWireFrame(Graphics g) {
        g.setColor(color);
        // Draw the points
        for (Point3D p : points) {
            p.draw(g);
        }
        // Draw the lines
        int len = points.length;
        for (int i = 0; i < len; ++i) {
            Point p1 = points[i].getPoint();
            Point p2 = points[(i+1)%len].getPoint();
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    public void rotate(double xDegree, double yDegree, double zDegree) {
        for (Point3D p : points) {
            p.rotate(xDegree, yDegree, zDegree);
        }
    }

    /**
     Find the depth of polygon center
     */
    public double getDepth() {
        double depth = 0.0;
        double temp;
        for (Point3D p : points) {
            temp = p.getZ();
            depth += temp;
        }
        depth  /=  points.length;
        return depth;
    }

    /**
     Find the normal of the plane with the first 3 vertices
     */
    public double[] getNormal() {
        Point3D p1 = points[0];
        Point3D p2 = points[1];
        Point3D p3 = points[2];
        double x1 = p1.getX();
        double x2 = p2.getX();
        double x3 = p3.getX();
        double y1 = p1.getY();
        double y2 = p2.getY();
        double y3 = p3.getY();
        double z1 = p1.getZ();
        double z2 = p2.getZ();
        double z3 = p3.getZ() ;

        double[] vector1 = {x1-x2, y1-y2, z1-z2};
        double[] vector2 = {x3-x1, y3-y1, z3-z1};
        double[] normal = new double[3];
        normal[0] = vector1[1] * vector2[2] - vector1[2] * vector2[1];
        normal[1] = vector1[2] * vector2[0] - vector1[0] * vector2[2];
        normal[2] = vector1[0] * vector2[1] - vector1[1] * vector2[0];
        return normal;
    }

    /**
     Angle between the normal and the Z axis
     */
    private double getAngle(double[] normal) {
        double[] zUnit = {0.0, 0.0, 1.0};
        double normalDeterminant = Math.sqrt(Math.pow(normal[1],2)+Math.pow(normal[0],2)+Math.pow(normal[2],2));
        double zUnitDeterminant = 1.0;
        double dotProd = zUnit[0]*normal[0] + zUnit[1]*normal[1] + zUnit[2]*normal[2];

        double angle = Math.acos(dotProd/(normalDeterminant*zUnitDeterminant));
        angle = (angle /Math.PI * 180) % 180; // Convert to a degree
        return angle ;
    }

    /**
     Adjust the color according to the distribution of angles
     */
    private Color generateShade() {
        int min = Integer.parseInt( "00005F", 16);
        int max = Integer.parseInt("0000FF", 16);
        int range = max - min;

        double angle = getAngle(getNormal());
        if (angle >= 90) {
            angle = 180 - angle;
        }
        String hexColor = Integer.toHexString((int)(min + angle/90*range));
        return Color.decode("#" + hexColor);
    }
}