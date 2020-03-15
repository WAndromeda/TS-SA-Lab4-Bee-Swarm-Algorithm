package Help.Point;

import java.math.RoundingMode;
import java.util.Random;

public abstract class Point {
    public static Double generateCoordinate(Double a, Double b){
        return new Random().doubles(a, b).limit(1).findFirst().getAsDouble();
    }
}
