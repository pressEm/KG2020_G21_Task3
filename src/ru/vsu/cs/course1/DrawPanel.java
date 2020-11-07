package ru.vsu.cs.course1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener, KeyListener {
    private ScreenConverter sc = new ScreenConverter(
            -10, 140, 200, 200, 800, 600);

    private Diagram diagram;
    private int candleWidth = this.getWidth() / 60;

    private int border = 30;

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

    private void drawOY(LineDrawer ld) {
        double min = diagram.getMinCandle();
        double max = diagram.getMaxCandle();

        ScreenPoint sP1 = new ScreenPoint(10, 0);
        ScreenPoint sP2 = new ScreenPoint(10, getHeight());

        ld.drawLine(sP1, sP2);

        int i = (int) (min - 10) / 10;
        for (; i < 20; i++) {
            if ((i * 10 >= (min - 10)) && (i * 10 < (max + 10))) {
                RealPoint rp3 = new RealPoint(-2, i * 10);
                RealPoint rp4 = new RealPoint(2, i * 10);

                ScreenPoint sP3 = new ScreenPoint(10 - getWidth() / 100, sc.r2s(rp3).getY());
                ScreenPoint sP4 = new ScreenPoint(10 + getWidth() / 100, sc.r2s(rp4).getY());

                ld.drawLine(sP3, sP4);
            }
        }
    }

    private void drawOX(LineDrawer ld, int widthCandles, int count, int xDel) {
//        System.out.println("w " + widthCandles);
        RealPoint rp1 = new RealPoint(10, 0);
        RealPoint rp2 = new RealPoint(10 + widthCandles * (count + 1), 0);

        ScreenPoint sP1 = new ScreenPoint(10, sc.r2s(rp1).getY());
        ScreenPoint sP2 = new ScreenPoint(getWidth(), sc.r2s(rp2).getY());

        ld.drawLine(sP1, sP2);
        for (int i = 0; i < count; i++) {
            int xx = sc.r2s(new RealPoint(xDel, 0)).getX();
            ScreenPoint p1 = new ScreenPoint(xx + 2 * widthCandles * i + widthCandles / 2, sc.r2s(new RealPoint(0, -2)).getY());
            ScreenPoint p2 = new ScreenPoint(xx + 2 * widthCandles * i + widthCandles / 2, sc.r2s(new RealPoint(0, 2)).getY());
            ld.drawLine(p1, p2);
        }
    }

    private void drawXY(LineDrawer ld, int widthCandles, int count, int xDel) {
        drawOY(ld);
        drawOX(ld, widthCandles, count, xDel);
    }

    private void drawDiagram(LineDrawer ld) {
        int count = diagram.getCandles().size();
        candleWidth = getWidth() / 60;
        int xDel = 0;
        drawXY(ld, candleWidth, count, xDel);
        DrawLogic.drawDiagram(ld, sc, diagram, 0, candleWidth);
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
        if (SwingUtilities.isRightMouseButton(mouseEvent)) {
            double bot = diagram.getMinCandle();
            double top = diagram.getMaxCandle();
            ScreenPoint p1 = sc.r2s(new RealPoint(0, bot));
            ScreenPoint p2 = sc.r2s(new RealPoint(0, top));
            ScreenPoint currentPoint = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
            if (!((p1.getY() <= this.getHeight() - border || currentPoint.getY() < prevPoint.getY())
                    && (p2.getY() >= border || currentPoint.getY() > prevPoint.getY()))) {
                currentPoint = new ScreenPoint(currentPoint.getX(), prevPoint.getY());
            }
            if (!(p1.getX() <= candleWidth * 2 || currentPoint.getX() < prevPoint.getX())) {
                currentPoint = new ScreenPoint(prevPoint.getX(), currentPoint.getY());
            }
            if (prevPoint != null) {
                ScreenPoint deltaScreen = new ScreenPoint(
                        currentPoint.getX() - prevPoint.getX(),
                        currentPoint.getY() - prevPoint.getY()
                );
                RealPoint deltaReal = sc.s2r(deltaScreen);

                double vectorX = deltaReal.getX() - sc.getCornerX();
                double vectorY = deltaReal.getY() - sc.getCornerY();

                sc.setCornerX(sc.getCornerX() - vectorX);
                sc.setCornerY(sc.getCornerY() - vectorY);
                prevPoint = currentPoint;
            }
            repaint();

        }
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
        int rotation = mouseWheelEvent.getWheelRotation();
        if (rotation < 0) {
            double bot = diagram.getMinCandle();
            double top = diagram.getMaxCandle();
            ScreenPoint p1 = sc.r2s(new RealPoint(0, bot));
            ScreenPoint p2 = sc.r2s(new RealPoint(0, top));
            if (Math.abs(p2.getY() - p1.getY()) * 1.11 > this.getHeight() - 2 * border) {
                return;
            }
        }
        double scale = 1;
        double step = (rotation < 0) ? 0.9 : 1.1;
        for (int i = Math.abs(rotation); i > 0; i--) {
            scale *= step;
        }
        sc.setRealW(scale * sc.getRealW());
        sc.setRealH(scale * sc.getRealH());

        RealPoint p1 = new RealPoint(0, 0);
        ScreenPoint p2 = sc.r2s(p1);
        if (rotation < 0 && p2.getX() > candleWidth * 2) {
            ScreenPoint p3 = new ScreenPoint(candleWidth, 0);
            sc.setCornerX(sc.s2r(p3).getX());
        }
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
        if (keyEvent.getKeyCode() == 127) {
            diagram = new Diagram();
            diagram.randomDiagram(50);
            diagram.printCandles();
            repaint();
        }
    }
}