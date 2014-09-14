package edu.uiuc.anymap;

import android.widget.Toast;

/**
 * Created by KomIG on 9/13/14.
 */
public class Calculations {
    private static DoublePoint p1;
    private static DoublePoint p2;

    private double a, b, c, d;

    //Index of current point
    public static int i = 1;

    /**
     * Used to update the points of our approx.
     * @param x Point to edit
     */
    public void setPoint(DoublePoint x) {
        if (i == 1) p1 = x;
        else if (i == 2) p2 = x;
        else return;
        if(++i == 3) calculate();
    }

    private void calculate() {
        double den = ((p2.x.y * p1.x.x) - (p2.x.x * p1.x.y));
        a = ((p1.xp.x * p2.x.y) - (p2.xp.x * p1.x.y))/den;
        b = ((p2.xp.x * p1.x.x) - (p1.xp.x * p2.x.x))/den;
        c = ((p1.xp.y * p2.x.y) - (p2.xp.y * p1.x.y))/den;
        d = ((p2.xp.y * p1.x.x) - (p1.xp.y * p2.x.x))/den;
        System.out.println("testZweiDreiFeur:" + den);
    }

    /**
     * Takes in a GPS point and returns a point on the image.
     * @param temp Point from the gps
     * @return Point on the image
     */
    private Point updatePoint(Point temp) {
        Point ret = new Point();
        ret.x = a*temp.x + b*temp.y;
        ret.y = c*temp.x + d*temp.y;
        return ret;
    }
}
