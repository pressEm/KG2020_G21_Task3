package ru.vsu.cs.course1;

import java.awt.*;

public interface LineDrawer {
    void drawLine(ScreenPoint p1, ScreenPoint p2);
    void setColor(Color color);
}
