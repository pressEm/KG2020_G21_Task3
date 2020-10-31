package ru.vsu.cs.course1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener, KeyListener {
    private ScreenConverter sc = new ScreenConverter(
            -10, 190, 200, 200, 800, 600);

    private Diagram diagram;


    DrawPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);

        diagram = new Diagram();
        diagram.randomDiagram(30);
        diagram.printCandles();
    }

    private void drawXY(LineDrawer ld, int widthCandles, int count, int xDel) {
        Line xAxis = new Line(0, 0, widthCandles * (count + 1), 0);
        Line yAxis = new Line(0, 0, 0, 200);
        drawLine(ld, xAxis);
        drawLine(ld, yAxis);
        for (int i = 0; i < count; i++) {
            int xx = sc.r2s(new RealPoint(xDel, 0)).getX();
            ScreenPoint p1 = new ScreenPoint(xx + 2 * widthCandles * i + widthCandles / 2, sc.r2s(new RealPoint(0, -2)).getY());
            ScreenPoint p2 = new ScreenPoint(xx + 2 * widthCandles * i + widthCandles / 2, sc.r2s(new RealPoint(0, 2)).getY());
            ld.drawLine(p1, p2);
        }
        for (int i = 0; i < 20; i++) {
            Line line = new Line(new RealPoint(-2, i*10), new RealPoint(2, i*10));
            drawLine(ld, line);
        }
    }

    private void drawDiagram(LineDrawer ld) {
        int count = diagram.getCandles().size();
        int widthCandle = 20;
        int xDel = 10;
        drawXY(ld, widthCandle, count, xDel);
        diagram.draw(ld, sc, xDel, widthCandle);
//        for (Line q : allLines) {
//            drawLine(ld, q);
//            if (newLine != null) {
//                drawLine(ld, newLine);
//            }
//        }
    }

    @Override
    public void paint(Graphics g) {
        sc.setScreenW(getWidth());
        sc.setScreenH(getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics bi_g = bi.createGraphics();
        bi_g.setColor(Color.WHITE);
        bi_g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        bi_g.dispose();
        /**/
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pd);
        drawDiagram(ld);
        /**/
        g.drawImage(bi, 0, 0, null);
    }


    private void drawLine(LineDrawer ld, Line l) { //любую линию с помощью LineDrawer
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }

    private ScreenPoint prevPoint = null;

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        ScreenPoint currentPoint = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
        if (prevPoint != null) {
            ScreenPoint deltaScreen = new ScreenPoint(
                    currentPoint.getX() - prevPoint.getX(),
                    currentPoint.getY() - prevPoint.getY()
            );
            RealPoint deltaReal = sc.s2r(deltaScreen);

            double vectorX = deltaReal.getX() - sc.getCornerX();
//            double vectorY = deltaReal.getY() - sc.getCornerY();

            sc.setCornerX(sc.getCornerX() - vectorX);
//            sc.setCornerY(sc.getCornerY() - vectorY);
            prevPoint = currentPoint;
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON3) { //правая кнопка мыши
            prevPoint = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        int clicks = mouseWheelEvent.getWheelRotation();
        double scale = 1;
        double step = (clicks < 0) ? 0.9 : 1.1;
        for (int i = Math.abs(clicks); i > 0; i--) {
            scale *= step;
        }
        sc.setRealW(scale * sc.getRealW());
        sc.setRealH(scale * sc.getRealH());
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if ( keyEvent.getKeyCode() == 127) {
            diagram = new Diagram();
            diagram.randomDiagram(50);
            diagram.printCandles();
            repaint();
        }
    }
}
