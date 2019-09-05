package ru.nsu.boyarintsev.Utils;

/**
 * Created by Артем on 09.03.2017.
 */
public class Point
{
    private int x = 0;
    private int y =0;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
