package ru.vsu.cs.course1;

import java.awt.*;

public class DrawCandle {
//    private static int[] arrPoints = new int[4];
    public static void draw(LineDrawer ld, ScreenConverter sc, int x, int deltaX, double[] arrPoints){
        if (arrPoints[0] < arrPoints[3]){
            ld.setColor(Color.blue);
        } else{
            ld.setColor(Color.red);
        }
        RealPoint rp1 =  new RealPoint(x, arrPoints[1]);
        RealPoint rp2 =  new RealPoint(x, arrPoints[2]);
        ScreenPoint p1 = new ScreenPoint(x+deltaX/2, sc.r2s(new RealPoint(x, arrPoints[1])).getY());
        ScreenPoint p2 = new ScreenPoint(x+deltaX/2, sc.r2s( new RealPoint(x, arrPoints[2])).getY());

//        ScreenPoint p1 = new ScreenPoint(sc.r2s(rp1).getX()+deltaX/2, sc.r2s(rp1).getY());
//        ScreenPoint p2 = new ScreenPoint(sc.r2s(rp2).getX()+deltaX/2, sc.r2s(rp2).getY());
        ld.drawLine(p1, p2);
        for (int i = 0; i < deltaX; i++) {
            RealPoint p3 = new RealPoint(x, arrPoints[0]);
            RealPoint p4 = new RealPoint(x, arrPoints[3]);
            ScreenPoint p5 = new ScreenPoint( x+i,  sc.r2s(new RealPoint(x, arrPoints[0])).getY());
            ScreenPoint p6 = new ScreenPoint( x+i,  sc.r2s(new RealPoint(x, arrPoints[3])).getY());
            ld.drawLine(p5, p6);
        }
    }
}
