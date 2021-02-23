package genetic;

import anneling2.SimulatedAnneling;
import anneling2.Solution;

import java.util.ArrayList;
import java.util.List;

public class Teste {

    public static void main(String[] args) {
        SimulatedAnneling ga = new SimulatedAnneling();
        List<Solution> solutions = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            System.out.println("Execution " + i);
            solutions.add(new SimulatedAnneling().execute());
        }
        for (int i = 0; i < 500; i++) {
            System.out.println(ga.evaluate(solutions.get(i)));
        }
    }

}
