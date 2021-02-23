package main;

import genetic.GeneticAlgorithm;
import genetic.Solution;

import java.util.ArrayList;
import java.util.List;

public class Teste {
    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm();
        List<Solution> solutions = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            System.out.println("Execution " + i);
            solutions.add(new GeneticAlgorithm().execute());
        }
        for (int i = 0; i < 500; i++) {
            System.out.println(ga.evaluate(solutions.get(i)));
        }
    }
}
