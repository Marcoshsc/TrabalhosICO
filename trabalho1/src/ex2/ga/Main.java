package ex2.ga;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Distances distances = new Distances(new Double[][]{
                 new Double[]{0.0, 1.0000, 10.1822, 8.1232, 6.5403, 6.6211, 7.1728, 8.0000},
                 new Double[]{1.0000, 0.0, 2.0000, 5.8972, 5.5189, 5.5814, 5.8125, 4.8665},
                 new Double[]{10.1822, 2.0000, 0.0, 3.0000, 7.1685, 9.2888, 10.2154, 10.5143},
                 new Double[]{81.232, 5.8972, 3.0000, 0.0, 4.0000, 7.3622, 7.4171, 7.4816},
                 new Double[]{6.5403, 5.5189, 7.1685, 4.0000, 0.0, 5.0000, 7.9040, 7.3343},
                 new Double[]{6.6211, 5.5814, 9.2888, 7.3622, 5.0000, 0.0, 6.0000, 9.5582},
                 new Double[]{7.1728, 5.8125, 10.2154, 7.4171, 7.9040, 6.0000, 0.0, 7.0000},
                 new Double[]{8.0000, 4.8665, 10.5143, 7.4816, 7.3343, 9.5582, 7.0000, 0.0}
        }
        );



        List<List<Solution>> results = new ArrayList<>();

        for(int i = 0; i< 500; i++){
            GeneticAlgorithm ge = new GeneticAlgorithm();
            List<Solution> solutions = GeneticAlgorithm.getAPopulation(50, distances);
            System.out.println(i);
            results.add(ge.init(solutions, distances,6));
        }

        List<Double> finals = new ArrayList<>();
        for (int i = 0; i < 50; i++){
            System.out.println( i);
            finals.add(GeneticAlgorithm.getBest(results, i));
        }
      for(int i =0; i < finals.size() ; i++){
          System.out.println(i+1 +"- "+ finals.get(i));
      }


        StringBuilder builder = new StringBuilder();
        for (List<Solution> line : results) {
            for (Solution column : line) {
                builder.append(column.getDistance());
                builder.append(",");
            }
            builder.append("\n");
        }
        String str = builder.toString();
        try {
            FileWriter myWriter = new FileWriter("gaTruncate.txt");
            myWriter.write(str);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        StringBuilder builder2 = new StringBuilder();
        int u = 1;
        for (Double value : finals) {
            builder2.append(u).append(": ");
            builder2.append(value);
            builder2.append("\n");
            u++;
        }

        String str2 = builder2.toString();
        try {
            FileWriter myWriter = new FileWriter("best.txt");
            myWriter.write(str2);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }

}
