package ex1.sa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Double[] initial = {0.7928990020427462,-0.6085854353728755};
        SimulatedAnnealing simulatedAnnealing =  new SimulatedAnnealing(0.99999,5);
        List<Double[]> initialSolutions = simulatedAnnealing.getSolutionsSet(initial);
        DeJong deJong = new DeJong();
        Double ds = simulatedAnnealing.getInitialTemperature(deJong, initialSolutions);
        System.out.println(ds);
        System.out.println();
        simulatedAnnealing.init(simulatedAnnealing.getInitialTemperature(deJong, initialSolutions),initial, deJong);
    }
}
