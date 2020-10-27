package ru.vsu.cs.course1;

import java.util.ArrayList;
import java.util.List;

public class Diagram{
    private List<Candle> candles;
    LineDrawer ld;
    private int width;

    public Diagram() {
//        candles = new ArrayList<>();
//        candles.add(new Candle());
    }

    public void createDiagram(){
        candles = new ArrayList<>();
        int[] c1 = {20, 35, 15, 30};
        int[] c2 = {10, 35, 15, 60};
        candles.add( new Candle(c1));
        candles.add( new Candle(c2));

    }

    public void draw(LineDrawer ld, ScreenConverter sc, int h){
        this.ld = ld;
        int i = h;
        for (Candle candle : candles) {
            RealPoint p1 = new RealPoint(i, candle.getArrPoints()[1]);
            RealPoint p2 = new RealPoint(i, candle.getArrPoints()[3]);
            ld.drawLine(sc.r2s(p1), sc.r2s(p2));
            for (int j = 0; j < h; j++) {
                System.out.println(i);
                RealPoint p3 = new RealPoint((int) (i-h/2), candle.getArrPoints()[0]);
                RealPoint p4 = new RealPoint((int) (i-h/2), candle.getArrPoints()[2]);
                ld.drawLine(sc.r2s(p3), sc.r2s(p4));
            }
            i += 2*h;
        }
    }

    public List<Candle> getCandles() {
        return candles;
    }


}
