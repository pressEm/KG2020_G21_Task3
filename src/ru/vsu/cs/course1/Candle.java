package ru.vsu.cs.course1;

import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Candle {
    private double[] arrPoints = new double[4];

    public Candle() {
//        MathContext context = new MathContext(5, RoundingMode.HALF_UP);
//        BigDecimal result = new BigDecimal(value, context);

        double s = Logic.random(-20, 50);
        arrPoints[0] = Logic.random(s - 20, s + 20);//начало
        arrPoints[3] = Logic.random(s - 20, s + 20);//конец
        double max = Math.max(arrPoints[0], arrPoints[3]);
        double min = Math.min(arrPoints[0], arrPoints[3]);
        arrPoints[1] = Logic.random(max, max + 10);//макс
        arrPoints[2] = Logic.random(min - 10, min + 1);//мин
    }

    public Candle(double[] arrPoints) {
        this.arrPoints = new double[4];
        this.arrPoints = arrPoints;
    }

    public double[] getArrPoints() {
        return arrPoints;
    }
}
