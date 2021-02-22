package genetic;

import anneling2.SimulatedAnneling;
import anneling2.Solution;

public class Teste {

    public static void main(String[] args) {
        SimulatedAnneling simulatedAnneling = new SimulatedAnneling();
        Solution solution = simulatedAnneling.execute();
        System.out.println(solution);
        System.out.println(simulatedAnneling.evaluate(solution));
    }

}
