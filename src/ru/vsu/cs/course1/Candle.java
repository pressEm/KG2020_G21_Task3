package ru.vsu.cs.course1;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Candle {
//    private List<Integer> points;
    private int[] arrPoints = new int[4];

    public Candle() {
        int s = Logic.random(30, 70);
        arrPoints[0] = Logic.random(s - 20, s + 20);//начало
        arrPoints[3] = Logic.random(s - 20, s + 20);//конец
        int max = Math.max(arrPoints[0], arrPoints[3]);
        int min = Math.min(arrPoints[0], arrPoints[3]);
        arrPoints[1] = Logic.random(max, max + 10);//макс
        arrPoints[2] = Logic.random(min - 10, min + 1);//мин
    }

    public Candle(int[] arrPoints) {
        this.arrPoints = new int[4];
        this.arrPoints = arrPoints;
    }
//    public Candle(){
//        points = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            int s = random(0,100);
//            points.add(random((s-20), (s+20)));
//        }
//    }

    public void draw(LineDrawer ld, ScreenConverter sc, int x, int deltaX){
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



    public int[] getArrPoints() {
        return arrPoints;
    }
}
