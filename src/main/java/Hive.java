import Entity.Bee;
import Entity.SphereBee;
import Help.Point.PointND;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Hive {

    public static final int MAX_ScoutBees = 50;
    public static final int MAX_BestBee = 50;
    public static final int MAX_OthersBee = 50;
    public static final int MAX_COORDINATE_ABS = 300;
    //private int sigma; //Параметр остановки (количество безрезультатных повторений цикла)
    private int scoutBees; //Количество пчёл разведчиков
    private int amountOfBestBees; //Количесто пчёл, посылаемых на лучшие участки
    private int amountOfOthersBees; // Количество пчёл, посылаемых на оставшие участки после лучших

    private int amountOfBestAreas; //Количество лучших участков
    private int amountOfOthersAreas; //Количество оставшихся участков
    private int amountOfBees; //Суммарное количество пчёл
    private ArrayList<Bee> bestAreasList; //Лучшие области, их количество определяется параметром bestAreas (Фактически, данный список состоит из ПЧЁЛ, которые нашли лучшие участки)
    private ArrayList<Bee> othersAreasList; //Оставшие области после лучших, их количесво определяется параметром othersAreas (Фактически, данный список состоит из ПЧЁЛ, которые нашли оставшиеся участки за лучшими)
    private ArrayList<Double> rangeList; //Список с разбросом для каждой из N координат
    private ArrayList<Bee> swarm; //Рой пчёл, который заполняется случайным образом в конструкторе
    private ArrayList<Bee> sendedBees;
    private PointND bestPosition; //Лучшая позиция за все итерации
    private double bestFitness; //Лучшее здоровье пчелы

    public Hive(/*int sigma,*/ int scoutBees, int amountOfBestBees, int amountOfOthersBees, int amountOfBestAreas, int amountOfOthersAreas, ArrayList<Double> rangeList){
        //this.sigma = sigma;
        this.scoutBees = Math.min(scoutBees, MAX_ScoutBees);
        this.amountOfBestBees = Math.min(amountOfBestBees, MAX_BestBee);
        this.amountOfOthersBees = Math.min(amountOfOthersBees, MAX_OthersBee);
        this.amountOfBestAreas = amountOfBestAreas;
        this.amountOfOthersAreas = amountOfOthersAreas;
        this.bestAreasList = new ArrayList<>();
        this.othersAreasList = new ArrayList<>();
        this.rangeList = new ArrayList<>(rangeList);
        this.bestPosition = null;
        this.bestFitness = Integer.MIN_VALUE;
        //coordinates = new Random().ints(-MAX_COORDINATE_ABS, MAX_COORDINATE_ABS).limit(MAX_COORDINATE_ABS * this.beeS).boxed().collect(Collectors.toCollection(ArrayList::new));
        //coordinates.forEach(System.out::println);
        this.amountOfBees = scoutBees + amountOfOthersAreas * amountOfOthersBees + amountOfBestAreas * amountOfBestBees;
        this.swarm = new ArrayList<>();
        for (int i = 0; i < amountOfBees; i++)
            this.swarm.add(new SphereBee());
        this.swarm.sort((o1, o2) -> o2.getFitness().compareTo(o1.getFitness()));
        this.bestPosition = this.swarm.get(0).getPosition();
        this.bestFitness = this.swarm.get(0).getFitness();
    }

    public void nextStep() {
        this.bestAreasList = new ArrayList<>();
        this.bestAreasList.add(swarm.get(0));  //В начало списка должна добавиться первая пчела

        this.sendedBees = new ArrayList<>();

        for (Bee bee : swarm) {
                //[curr_index: -1];
            //Если пчела находится в пределах уже отмеченного лучшего участка, то ее положение не считаем
            if (bee.otherPatch(bestAreasList, rangeList)) {
                bestAreasList.add(bee);
                if (bestAreasList.size() == amountOfBestAreas)
                    break;
            }
        }

        othersAreasList = new ArrayList<>();
        for (Bee bee  : swarm) {
            if (bee.otherPatch(bestAreasList, rangeList) && bee.otherPatch(othersAreasList, rangeList)) {
                othersAreasList.add(bee);
                if (othersAreasList.size()  == amountOfOthersAreas)
                    break;
            }
        }
        //Номер очередной отправляемой пчелы. 0-ую пчелу никуда не отправляем
        int beeIndex = 1;
        //sendedBees.addAll(bestAreasList); sendedBees.addAll(othersAreasList);
        for (Bee bee : bestAreasList)
            beeIndex = sendBees(bee.getPosition(), beeIndex, amountOfBestBees);

        for (Bee bee : othersAreasList)
            beeIndex = sendBees(bee.getPosition(), beeIndex, amountOfOthersBees);


        //Оставшихся пчел пошлем куда попадет
        for (Bee bee : swarm)
            if (!sendedBees.contains(bee))
                bee.goToRandom();

        this.swarm.sort((o1, o2) -> o2.getFitness().compareTo(o1.getFitness()));
        //System.out.println(swarm);
        //Thread.sleep(5000);
        this.bestPosition = swarm.get(0).getPosition();
        this.bestFitness = swarm.get(0).getFitness();
    }

    public int sendBees(PointND pos, int index, int count){
        //swarm.get(index).goTo(pos, rangeList);
        for (int i = 0; i < count; i++) {
			//Чтобы не выйти за пределы улея
            if (index == swarm.size())
                break;
            Bee bee =swarm.get(index);

            if (!(bestAreasList.contains(bee) || othersAreasList.contains(bee) || sendedBees.contains(bee))) {
                //Пчела не на лучших или выбранных позициях
                bee.goTo(pos, rangeList);
                sendedBees.add(bee);
            }
            index++;
        }
        return index;
    }

    public PointND getBestPosition() {
        return bestPosition;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public ArrayList<Double> getRangeList() {
        return rangeList;
    }

    public void setRangeList(ArrayList<Double> rangeList) {
        this.rangeList = rangeList;
    }

    /*public void calculateAreas(){
        for (int i = 0; i < sigma; i++) {
            ArrayList<Area> areasList = new ArrayList<>();
            for (int j = 0; j < scoutBees; j++) {
                Point2D p = new Point2D(getCoordinateRandom(-MAX_COORDINATE_ABS, MAX_COORDINATE_ABS), getCoordinateRandom(-MAX_COORDINATE_ABS, MAX_COORDINATE_ABS));
                areasList.add(new Area(p, functionValue(p.x, p.y)));
            }
        }
    }*/
}
