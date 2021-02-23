package ex2.ga;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        GeneticAlgorithm ge = new GeneticAlgorithm();
        List<Solution> solutions = GeneticAlgorithm.getAPopulation(100, distances);
//        List<Solution> solutions = new ArrayList<>();
//        for (Integer[]s: i){
//            Solution ss = new Solution( distances.calculateDistanceOfTwoPoints(s,distances), s);
//            dsd.add(ss);
//        }


        ge.init(solutions, 500, distances);

    }

}
