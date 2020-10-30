package ru.vsu.cs.course1;

import java.util.ArrayList;
import java.util.List;

public class Diagram{
    private List<Candle> candles = new ArrayList<>();
//    LineDrawer ld;
    private int width;

    public Diagram() {
//        candles = new ArrayList<>();
//        candles.add(new Candle());
    }

    public void createDiagram(){
//        candles = new ArrayList<>();
        int[] c1 = {20, 35, 5, 30};
        int[] c2 = {10, 35, 5, 25};
        candles.add( new Candle(c1));
        candles.add( new Candle(c2));
        candles.add( new Candle(new int[] {10, 50, 10, 30}));
        candles.add( new Candle(new int[] {30, 30, 30, 20}));
        candles.add( new Candle(new int[] {10, 30, 20, 20}));
        candles.add( new Candle(new int[] {50, 60, 30, 40}));
        candles.add( new Candle(new int[] {20, 40, 10, 30}));
        candles.add( new Candle(new int[] {10, 10, 10, 20}));
    }

    public void randomDiagram(int size){
        for (int i = 0; i < size; i++) {
            candles.add(new Candle());
        }
    }


        public void draw(LineDrawer ld, ScreenConverter sc, int x, int y, int deltaX){
        ScreenPoint screenPointDelta = new ScreenPoint(x, y);
        int screenX = sc.r2s(new RealPoint(x,y)).getX();
        for (Candle candle : candles){
            candle.draw(ld, sc, screenX, deltaX);
            screenX += 2*deltaX;
        }
    }

    public List<Candle> getCandles() {
        return candles;
    }


}
