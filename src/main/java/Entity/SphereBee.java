package Entity;

import java.util.ArrayList;

import static Help.Point.Point.generateCoordinate;

public class SphereBee extends Bee {
    private static final int count = 10; //Количество координат

    public SphereBee(){
        super();
        for (int i = 0; i < count; i++){
            minVal.add(-150.0);
            maxVal.add(150.0);
            position.setCoordinate(i, generateCoordinate(minVal.get(i), maxVal.get(i)));
        }
        calcFitness();
    }

    @Override
    public void calcFitness(){
        if (position.getCoordinates().size() <= 0)
            throw new RuntimeException("Пространство должен быть, как минимум, одномерным");
        fitness = - position.getCoordinates().stream().mapToDouble((x) -> x*x).reduce(Double::sum).getAsDouble();
    }

    public static ArrayList<Double> getStartRange(){
        ArrayList<Double> startRange = new ArrayList<>();
        for (int i = 0; i < count; i++)
            startRange.add(150.0);
        return startRange;
    }

    public static ArrayList<Double> getRangeKoef(){
        ArrayList<Double> rangeKoef = new ArrayList<>();
        for (int i = 0; i < count; i++)
            rangeKoef.add(0.98);
        return rangeKoef;
    }

    @Override
    public String toString() {
        return "SphereBee{" +
                "fitness=" + fitness +
                '}';
    }
}
