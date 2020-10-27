package ru.vsu.cs.course1;

import java.util.ArrayList;
import java.util.List;

public class Candle {
    private List<Integer> points;
    private int[] arrPoints;

    Candle(List<Integer> points){
        this.points = points;
    }
    public Candle(int[] arrPoints) {
        this.arrPoints = new int[4];
        this.arrPoints = arrPoints;
    }
    public Candle(){
        points = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int s = random(0,100);
            points.add(random((s-20), (s+20)));
        }
    }
    private int random(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public int[] getArrPoints() {
        return arrPoints;
    }
}
