package Help.Point;

import java.util.Random;

public class Point2D extends Point {
    private int x;
    private int y;

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static Integer getCoordRandom(int a, int b){
        return new Random().ints(a, b).limit(1).findFirst().getAsInt();
    }
}


