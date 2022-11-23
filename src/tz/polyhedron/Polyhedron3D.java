package tz.polyhedron;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class Polyhedron3D {

    private Polygon3D[] polygons;

    public Polyhedron3D(Polygon3D... p) {
        polygons = p;
    }

    public void draw(Graphics g) {
        for (Polygon3D p : polygons) {
            p.draw(g);
        }
    }
    public void drawWireFrame(Graphics g) {
        for (Polygon3D p : polygons) {
            p.drawWireFrame(g);
        }
    }
    public void rotate(double xDegrees, double yDegrees, double zDegrees) {
        for (Polygon3D p : polygons) {
            p.rotate(xDegrees, yDegrees, zDegrees);
        }
        // make sure the "shallower" polygon on the top
        Arrays.sort(polygons, new Comparator<Polygon3D>() {
            @Override
            public int compare(Polygon3D p1, Polygon3D p2) {
                if(p1.getDepth()>p2.getDepth()) return -1;
                else return 1;
            }
        });
    }
}
