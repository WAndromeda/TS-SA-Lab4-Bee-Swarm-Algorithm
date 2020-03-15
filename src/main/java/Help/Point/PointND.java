package Help.Point;

import java.util.ArrayList;
import java.util.Random;

//Точка для хранения координат в N-мерном пространстве
public class PointND extends Point {

    private ArrayList<Double> coordinates;

    public PointND() {
        coordinates = new ArrayList<>();
    }

    public PointND(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public Double getCoordinate(int numOfCoordinate){
        if (numOfCoordinate >= coordinates.size() || numOfCoordinate < 0)
            throw  new RuntimeException("Запрашиваеся отсутствующая координата");
        else
            return coordinates.get(numOfCoordinate);
    }

    public void setCoordinate(int numOfCoordinate, Double value){
        if (numOfCoordinate < 0)
            throw  new RuntimeException("Запрашивается отсутствующая координата");
        else {
            if (numOfCoordinate > (coordinates.size()-1))
                coordinates.add(value);
            else
                coordinates.set(numOfCoordinate, value);
        }
    }

    @Override
    public String toString() {
        return "PointND{" +
                "coordinates=" + coordinates +
                '}';
    }
}
