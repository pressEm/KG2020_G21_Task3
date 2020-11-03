package ru.vsu.cs.course1;

import java.util.List;

public class Logic {

    public static double random(double min, double max){
            max -= min;
            return (Math.random() * ++max) + min;
    }

    public static double getMinCandle(List<Candle> candles){
        double min = candles.get(0).getArrPoints()[2];
        for (Candle candle : candles){
            double curr = candle.getArrPoints()[2];
            if (curr < min){
                min = curr;
            }
        }
        return min;
    }
    public static double getMaxCandle(List<Candle> candles){
        double max = candles.get(0).getArrPoints()[1];
        for (Candle candle : candles){
            double curr = candle.getArrPoints()[1];
            if (curr > max){
                max = curr;
            }
        }
        return max;
    }
}
