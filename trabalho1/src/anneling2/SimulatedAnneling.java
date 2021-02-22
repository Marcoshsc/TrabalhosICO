package anneling2;

import java.util.*;

public class SimulatedAnneling {

    private double[][] distanceMatrix = new double[][]{
            new double[]{0d, 1.0000d, 10.1822d, 8.1232d, 6.5403d, 6.6211d, 7.1728d, 8.0000d},
            new double[]{1.0000d, 0d, 2.0000d, 5.8972d, 5.5189d, 5.5814d, 5.8125d, 4.8665d},
            new double[]{10.1822d, 2.0000d, 0d, 3.0000d, 7.1685d, 9.2888d, 10.2154d, 10.5143d},
            new double[]{8.1232d, 5.8972d, 3.0000d, 0d, 4.0000d, 7.3622d, 7.4171d, 7.4816d},
            new double[]{6.5403d, 5.5189d, 7.1685d, 4.0000d, 0d, 5.0000d, 7.9040d, 7.3343d},
            new double[]{6.6211d, 5.5814d, 9.2888d, 7.3622d, 5.0000d, 0d, 6.0000d, 9.5582d},
            new double[]{7.1728d, 5.8125d, 10.2154d, 7.4171d, 7.9040d, 6.0000d, 0d, 7.0000d},
            new double[]{8.0000d, 4.8665d, 10.5143d, 7.4816d, 7.3343d, 9.5582d, 7.0000d, 0d}
    };

    private double initialTemperatureConstant = 0.8;
    private double freezingRate = 0.9;
    private int iterationsPerTemperature = 10;
    private int possibleInitialSolutions = 10;
    private double rnd = 0.5;
    private List<Solution> solutions;
    private double temperature;

    public Solution execute() {
        this.solutions = getInitialSolutions();
        this.temperature = calculateInitialTemperature();
        while(this.temperature > 0.00001) {
            for (int i = 0; i < iterationsPerTemperature; i++) {
                Solution old = solutions.get(i);
                Solution perturbated = perturbate(old);
                double selectionFactor = calculateSelectionFactor(perturbated, old);
                if (selectionFactor >= rnd)
                    old.replace(perturbated);
            }
            this.temperature = freezingRate * this.temperature;
            Solution betterSolution = getBetterSolution();
            System.out.println(betterSolution);
            System.out.println(evaluate(betterSolution));
        }
        return getBetterSolution();
    }

    private Solution getBetterSolution() {
        int greater = 0;
        for (int i = 1; i < solutions.size(); i++) {
            if(evaluate(solutions.get(greater)) > evaluate(solutions.get(i)))
                greater = i;
        }
        return solutions.get(greater);
    }

    private double calculateSelectionFactor(Solution s1, Solution s2) {
        double variance = -(evaluate(s2) - evaluate(s1));
        return Math.pow(Math.E, -(variance / temperature));
    }

    private Solution perturbate(Solution solution) {
        Random random = new Random();
        Solution copy = solution.copy();
        int firstIndex = random.nextInt(5);
        int secondIndex = random.nextInt(5);
        int[] stops = copy.getStops();
        int aux = stops[firstIndex];
        stops[firstIndex] = stops[secondIndex];
        stops[secondIndex] = aux;
        return copy;
    }

    private double calculateInitialTemperature() {
        double variations = 0;
        for (int i = 0; i < solutions.size() - 1; i++) {
            double variation = evaluate(solutions.get(i+1)) - evaluate(solutions.get(i));
            variations += variation;
        }
        return (variations / (solutions.size() - 1)) / Math.log(initialTemperatureConstant);
    }

    public double evaluate(Solution solution) {
        double objective = 0;
        int[] stops = solution.getStops();
        for (int i = 0; i < stops.length - 1; i++) {
            objective += distanceMatrix[stops[i]][stops[i+1]];
        }
        objective += distanceMatrix[stops[stops.length - 1]][stops[0]];
        return objective;
    }

    private List<Solution> getInitialSolutions() {
        List<Solution> initialSolutions = new ArrayList<>();
        for (int i = 0; i < possibleInitialSolutions; i++) {
            initialSolutions.add(generateInitialSolution());
        }
        return initialSolutions;
    }

    private Solution generateInitialSolution() {
        int[] stops = new int[8];
        int addedStops = 0;
        Map<Integer, Integer> alreadyAddedStops = new HashMap<>();
        Random random = new Random();
        while(addedStops != 8) {
            int newNumber = random.nextInt(8);
            if(alreadyAddedStops.containsKey(newNumber))
                continue;
            alreadyAddedStops.put(newNumber, newNumber);
            stops[addedStops] = newNumber;
            addedStops++;
        }
        return new Solution(stops);
    }

}
