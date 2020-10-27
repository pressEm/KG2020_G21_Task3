package ru.vsu.cs.course1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    private ScreenConverter sc = new ScreenConverter(
            -10, 190, 200, 200, 800, 600);

    private Line xAxis = new Line(0, 0, 200, 0);
    private Line yAxis = new Line(0, 0, 0, 200);

    DrawPanel (){
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    private void drawAll(LineDrawer ld) {
//        ld.drawLine(sc.r2s(xAxis.getP1()), sc.r2s(xAxis.getP2()));
//        ld.drawLine(sc.r2s(yAxis.getP1()), sc.r2s(yAxis.getP2()));
        drawLine(ld, xAxis);
        drawLine(ld, yAxis);
        drawLine(ld, new Line(10, 10, 20, 20));


        Diagram diagram = new Diagram();
        diagram.createDiagram();
        diagram.draw(ld, sc, 10);
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
        drawAll(ld);
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

//            ????????
            double vectorX = deltaReal.getX() - sc.getCornerX();
            double vectorY = deltaReal.getY() - sc.getCornerY();

            sc.setCornerX(sc.getCornerX() - vectorX);
            sc.setCornerY(sc.getCornerY() - vectorY);
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
}

//Задавать свечи в реальных или экранных координатах
