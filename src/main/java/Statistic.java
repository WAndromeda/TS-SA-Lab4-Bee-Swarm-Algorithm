import Help.Point.PointND;

import java.util.ArrayList;

public class Statistic {
    public ArrayList<ArrayList<Double> > fitnesses;
    public ArrayList<ArrayList<PointND>> positions;
    public ArrayList<ArrayList<Double> > range;

    public Statistic() {
        fitnesses = new ArrayList<>();
        positions = new ArrayList<>();
        range = new ArrayList<>();
    }

    public void add(int runNum, Hive hive){
        if (runNum > fitnesses.size()-1) {
            fitnesses.add(new ArrayList<>());
        }
        fitnesses.get(runNum).add(hive.getBestFitness());

        if (runNum > positions.size()-1) {
            positions.add(new ArrayList<>());
        }
        positions.get(runNum).add(hive.getBestPosition());

       if (runNum > range.size()-1){
           range.add(new ArrayList<>());
       }
       range.set(runNum, hive.getRangeList());
    }

    public String  formatFitness (int runNum){
        //Сформировать таблицу целевой функции
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < fitnesses.get(runNum).size(); i++) {
            String line = String.format("%6.6d    %10f\n", i, fitnesses.get(runNum).get(i));
            result.append(line);
        }
        return result.toString();
    }

    public String formatColumns (int runNum, ArrayList<ArrayList<PointND>> column) {
        //Форматировать список списков items для вывода
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < column.get(runNum).size(); i++) {
            StringBuilder line = new StringBuilder(String.format("%6.6d", i));

            for (Double  d : column.get(runNum).get(i).getCoordinates())
                line.append(String.format("    %10f", d));

            result.append(line).append("\n");
        }
        return result.toString();

    }

    public String formatColumnsRange (int runNum, ArrayList<ArrayList<Double>> column) {
        //Форматировать список списков items для вывода
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < column.get(runNum).size(); i++) {
            StringBuilder line = new StringBuilder(String.format("%6.6d", i));

            for (Double  d : column.get(runNum))
                line.append(String.format("    %10f", d));

            result.append(line).append("\n");
        }
        return result.toString();

    }

    public String formatPos (int runNum) {
        return formatColumns(runNum, positions);
    }

    public String formatRange (int runNum) {
        return formatColumnsRange(runNum, range);
    }
}
