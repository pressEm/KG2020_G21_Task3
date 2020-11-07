package ru.vsu.cs.course1;

import java.awt.*;

public class DrawLogic {
    public static void drawCandle(LineDrawer ld, ScreenConverter sc, int x, int deltaX, double[] arrPoints) {
        if (arrPoints[0] < arrPoints[3]) {
            ld.setColor(Color.BLUE);
        } else {
            ld.setColor(Color.RED);
        }
        ScreenPoint p1 = new ScreenPoint(x + deltaX / 2, sc.r2s(new RealPoint(x, arrPoints[1])).getY());
        ScreenPoint p2 = new ScreenPoint(x + deltaX / 2, sc.r2s(new RealPoint(x, arrPoints[2])).getY());
        ld.drawLine(p1, p2);
        for (int i = 0; i < deltaX; i++) {
            ScreenPoint p3 = new ScreenPoint(x + i, sc.r2s(new RealPoint(x, arrPoints[0])).getY());
            ScreenPoint p4 = new ScreenPoint(x + i, sc.r2s(new RealPoint(x, arrPoints[3])).getY());
            ld.drawLine(p3, p4);
        }
    }

    public static void drawDiagram(LineDrawer ld, ScreenConverter sc, Diagram diagram, int x, int width) {
        int screenX = sc.r2s(new RealPoint(x, 0)).getX();
        for (Candle candle : diagram.getCandles()) {
            DrawLogic.drawCandle(ld, sc, screenX, width, candle.getArrPoints());
            screenX += 2 * width;
        }
    }
}