package tz.polyhedron;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;
import javafx.scene.canvas.Canvas;


/**
 * JavaFx window rendering startup function
 */
public class Display extends Canvas {
    private Polyhedron3D polyhedron;
    private JFrame jframe;
    public static final int WIDTH = 900;
    public static final int HEIGHT = 900;
    public static final int PolyhedronScale = 150;
    public static final double MouseMovementScale = 0.2;
    public static double windoScale = 1.0;
    private static double zoomFactor = 1.2;
    private static final String TITLE = "Ployhedron";
    // test input
    private static String INPUT_FILE = "src/tz/data/object.txt";
    // mouse event macros
    private int currentX;
    private int currentY;

    private Mouse mouse;
    public static final int NO_CLICK = -1;
    public static final int CLICK_LEFT = 1;
    public static final int SCROLL = 2;
    public static final int CLICK_RIGHT = 3;

    public Display() {
        jframe = new JFrame();
        mouse = new Mouse();
    }


    public static void main(String[] args) {
        Display display = new Display();
        display.init();
        while (true) {
            display.update();
            //display.draw();
            display.drawWireFrame();
        }
    }

    public void init() {
        this.setJframe();
        this.polyhedron = readPolyhedronFromFile(INPUT_FILE);
    }

    /**
     * Draw the polyhedron on window
     */
    private void draw() {
        BufferStrategy bs = jframe.getBufferStrategy();
        Graphics g= bs.getDrawGraphics();
        try {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            polyhedron.draw(g);
        } finally {
            g.dispose();
        }
        bs.show();
    }

    private void drawWireFrame() {
        BufferStrategy bs = jframe.getBufferStrategy();
        Graphics g= bs.getDrawGraphics();
        try {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            polyhedron.drawWireFrame(g);
        } finally {
            g.dispose();
        }
        bs.show();
    }

    /**
     * Track mouse location to do the rotation
     */
    private void update() {
        int x = mouse.getX();
        int y = mouse.getY();
        double degreeX;
        double degreeY;
        if (mouse.getButton() ==  CLICK_LEFT) {
            degreeX = (x - currentX)*MouseMovementScale;
            degreeY = -(y - currentY)*MouseMovementScale;
            polyhedron.rotate(degreeY, degreeX, 0);
        }else if (mouse.getButton() == CLICK_RIGHT) {
            degreeX = (x - currentX)*MouseMovementScale;
            polyhedron.rotate( 0, 0, degreeX);
        }
        if (mouse.isScrollingUp()) {
            Display.zoomOut();
        } else if (mouse.isScrollingDown()) {
            Display.zoomIn();
        }
        mouse.resetWheel();
        currentX = x;
        currentY = y;
    }

    /**
     * Setup Jframe for display
     */
    private void setJframe(){
        this.jframe.setSize(WIDTH, HEIGHT);
        this.jframe.setVisible(true);
        this.jframe.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.jframe.addMouseListener(mouse);
        this.jframe.setTitle(TITLE);
        this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jframe.addMouseMotionListener(mouse);
        this.jframe.addMouseWheelListener(mouse);
        this.jframe.pack();
        this.jframe.setLocationRelativeTo(null);
        this.jframe.setResizable(false);
        this.jframe.createBufferStrategy(2);
    }
    /**
     * Construct the Polyhedron from the file data
     */
    private Polyhedron3D readPolyhedronFromFile(String fileName){
        File file = new File(fileName);
        Polyhedron3D result = new Polyhedron3D();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            int[] numbers =  Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
            int pointNum = numbers[0];
            int ploygonNum = numbers[1];

            HashMap<Integer, Point3D> points = new HashMap<Integer, Point3D>();
            Polygon3D[] polygons = new Polygon3D[ploygonNum];

            for (int i = 0; i < pointNum; ++i) {
                line = br.readLine();
                double[] data = Arrays.stream(line.split(",")).mapToDouble(Double::parseDouble).toArray();
                int idx = new Double(data[0]).intValue();
                Point3D point3D = new Point3D(data[1]*PolyhedronScale , data[2]*PolyhedronScale , data[3]*PolyhedronScale );
                points.put(idx, point3D);
            }

            for (int i = 0; i < ploygonNum; ++i) {
                line = br.readLine();
                int[] idxs = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
                int len = idxs.length;
                Point3D[] point3Ds = new Point3D[len];
                for (int j = 0; j < len; j++) {
                    point3Ds[j] = points.get(idxs[j]);
                }
                polygons[i] = new Polygon3D(point3Ds);
            }
            result = new Polyhedron3D(polygons);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void zoomIn() {
        windoScale *= zoomFactor;
    }

    public static void zoomOut() {
        windoScale /= zoomFactor;
    }
}

