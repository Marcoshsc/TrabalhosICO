package main;

import genetic.GeneticAlgorithm;
import genetic.Solution;

public class Teste {
    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        Solution solution = geneticAlgorithm.execute();
        System.out.println(solution);
        System.out.println(geneticAlgorithm.evaluate(solution));
    }
}
