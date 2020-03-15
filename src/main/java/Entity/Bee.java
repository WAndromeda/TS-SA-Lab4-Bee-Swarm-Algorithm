package Entity;

import Help.Point.PointND;
import java.util.ArrayList;
import static Help.Point.Point.generateCoordinate;
public class Bee {

    protected ArrayList<Double> minVal;
    protected ArrayList<Double> maxVal;
    protected PointND position;
    protected Double fitness;

    public Bee(){
        minVal = new ArrayList<>();
        maxVal = new ArrayList<>();
        position = new PointND();
        fitness = 0.0;
        //ArrayList<Integer> coordinates = new Random().ints(-Hive.MAX_COORDINATE_ABS, Hive.MAX_COORDINATE_ABS).limit(count).boxed().collect(Collectors.toCollection());
        //position = new PointND(coordinates);
        //calcFitness();
    }

    protected void calcFitness(){
        /*if (position.getCoordinates().size() < 2)
            throw new RuntimeException("Пространство должен быть, как минимум, двумерным");
        fitness =
                -(position.getCoordinate(0)*position.getCoordinate(0)
                + position.getCoordinate(1)*position.getCoordinate(1));*/
    }



    public void goTo(PointND newPos, ArrayList<Double> rangeList){
        // Перелет в окрестность места, которое нашла другая пчела. Не в то же самое место!
        // К каждой из координат добавляем случайное значение
        for (int i = 0; i < newPos.getCoordinates().size(); i++)
            position.setCoordinate(i, newPos.getCoordinate(i) + generateCoordinate(-rangeList.get(i), rangeList.get(i)));

        // Проверим, чтобы не выйти за заданные пределы
        checkPosition();
        // Расчитаем и сохраним целевую функцию
        calcFitness();
    }

    public void goToRandom(){
        for (int i = 0; i < position.getCoordinates().size(); i++)
            position.setCoordinate(i, generateCoordinate(minVal.get(i), maxVal.get(i)));
        checkPosition();
        calcFitness();
    }

    public void checkPosition(){
        //Скорректировать координаты пчелы, если они выходят за установленные пределы
        for (int i = 0; i < position.getCoordinates().size(); i++) {
            if (position.getCoordinate(i) < minVal.get(i))
                position.setCoordinate(i, minVal.get(i));
            else
            if (position.getCoordinate(i) > maxVal.get(i))
                position.setCoordinate(i, maxVal.get(i));
        }
    }

    public Double getFitness() {
        return fitness;
    }

    public PointND getPosition() {
        return position;
    }

    public boolean otherPatch(ArrayList<Bee> beeList, ArrayList<Double> rangeList) {
        //Проверить находится ли пчела на том же участке, что и одна из пчел в bee_list.
        //rangeList - интервал изменения каждой из координат
        if (beeList.size() == 0)
            return true;

        for (Bee bee : beeList) {
            PointND pos = bee.getPosition();
            for (int i = 0; i < position.getCoordinates().size(); i++)
                if (Math.abs(position.getCoordinate(i) - pos.getCoordinate(i)) > rangeList.get(i))
                    return true;
        }
        return false;
    }
}
