package edu.uiuc.anymap;

/**
 * Created by KomIG on 9/13/14.
 */
public class Calculations {
    private static Point p1;
    private static Point p2;
    private static Point p3;

    public void setPoint(int i, Point x){
        if(i == 1) p1 = x;
        else if(i==2) p2 = x;
        else if(i==3) p3 = x;
    }

    public int checkBlank(){
        return p1.xGPS == -1 ? (p2.xGPS == -1 ? (p3.xGPS == -1 ? 0 : 3) : 2) : 1;
    }


}
