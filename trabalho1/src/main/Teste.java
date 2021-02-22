package main;

import genetic.GeneticAlgorithm;
import genetic.Solution;

public class Teste {
    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        Solution solution = geneticAlgorithm.execute();
        System.out.println(solution);
        System.out.println(geneticAlgorithm.evaluate(solution));
        System.out.println(geneticAlgorithm.evaluate(new Solution(0.234949392488371, 0.1174746962441855)));
        System.out.println(geneticAlgorithm.evaluate(new Solution(1.00004, 1.000006)));
    }
}
