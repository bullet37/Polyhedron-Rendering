package tz.polyhedron;

import java.awt.event.*;


public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {
    private int button = -1;
    private int wheel = 0;
    private int x = -1;
    private int y = -1;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        this.wheel = e.getWheelRotation();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
        this.button = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    // getters
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getButton() {
        return button;
    }
    public int getWheel() {
        return wheel;
    }

    public void resetWheel() {
        wheel = 0;
    }
    public boolean isScrollingUp() {
        return wheel == -1;
    }
    public boolean isScrollingDown() {
        return wheel == 1;
    }

}
