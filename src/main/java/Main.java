import Entity.SphereBee;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args)  {
        Statistic stat = new Statistic();
        int runNum = 0;
        //Максимальное количество итераций алгоритмы
        int maxRuns = 1;
        //Максимальное количество итераций алгоритмы
        int maxIter = 10000;
        // Через такое количество итераций без нахождения лучшего решения уменьшим область поиска
        int maxFuncCounter = 10;

        //Начальное значение целевой функции
        double bestFunc = -1.0e9;

        //Количество итераций без улучшения целевой функции
        int funcCounter = 0;
        ArrayList<Double> koefList = SphereBee.getRangeKoef();
        for (runNum = 0; runNum < maxRuns; runNum++) {
            Hive hive = new Hive(300, 30, 10, 5, 15, SphereBee.getStartRange());

            for (int i = 0; i < maxIter; i++) {
                hive.nextStep();
                stat.add(runNum, hive);
                if (hive.getBestFitness() > bestFunc) {
                    bestFunc = hive.getBestFitness();
                    funcCounter = 0;
                    System.out.println(String.format("\n*** Iteration %d / %d", runNum + 1, i));
                    System.out.println(String.format("Best position: %s", hive.getBestPosition()));
                    System.out.println(String.format("Best fitness: %f", hive.getBestFitness()));
                } else {
                    funcCounter++;
                    if (funcCounter == maxFuncCounter) {
                        ArrayList<Double> newRangeList = new ArrayList<>();

                        for (int k = 0; k < hive.getRangeList().size(); k++)
                            newRangeList.add(hive.getRangeList().get(k) * koefList.get(k));
                        hive.setRangeList(newRangeList);
                        funcCounter = 0;
                        System.out.println(String.format("\n*** Iteration %d / %d (new range)", runNum + 1, i));
                        System.out.println(String.format("New range: %s", hive.getRangeList()));
                        System.out.println(String.format("Best position: %s", hive.getBestPosition()));
                        System.out.println(String.format("Best fitness: %f", hive.getBestFitness()));
                    }
                }
            }
            System.out.print("\nBEST FITNESS = " + bestFunc + "\n");
        }
    }
}
