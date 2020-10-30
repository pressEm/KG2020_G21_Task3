package ru.vsu.cs.course1;

public class Logic {

    public static int random(int min, int max){
            max -= min;
            return (int) (Math.random() * ++max) + min;
    }
}
