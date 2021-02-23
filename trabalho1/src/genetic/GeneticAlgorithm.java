package genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {

    private double minValue = -2.048;
    private double maxValue = 2.048;
    private int popSize = 800;
    private List<Solution> population;
    private double mutationRate = 0.02;

    public Solution execute() {
        generateInitialPopulation();
        for (int i = 0; i < 500; i++) {
            List<Solution> newPopulation = new ArrayList<>();
            for (int j = 0; j < 400; j++) {
                Solution s1 = select();
                Solution s2 = select();
                Solution s3 = reproduce(s1, s2);
                newPopulation.add(s3);
            }
            this.population = newPopulation;
        }
        return getBetterSolution();
    }

    public double evaluate(Solution solution) {
        return 3905.93 - (100*Math.pow(Math.pow(solution.getX(), 2) - solution.getY(), 2)) - Math.pow((1 - solution.getX()), 2);
//        return 3905.93 - Math.pow(((100 * Math.pow(solution.getX(),2)) - (100*solution.getY())),2) - Math.pow((1 - solution.getX()),2);
    }

    private Solution generateRandomSolution() {
        Solution solution = new Solution(minValue + (maxValue - minValue) * Math.random(), minValue + (maxValue - minValue) * Math.random());
        return solution;
    }

    private void generateInitialPopulation() {
        population = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            population.add(generateRandomSolution());
        }
    }

    private void mutate(Solution solution) {
        double number = Math.random();
        if(number <= mutationRate) {
            double isX = Math.random();
            if(isX >= 0.5) {
                double newX = solution.getX() / 2;
                solution.setX(Math.min(Math.max(newX, minValue), minValue));
            }
            else
                solution.setY(Math.min(Math.max(solution.getY() / 2, minValue), maxValue));
        }
    }

    private Solution reproduce(Solution s1, Solution s2) {
        double x = (s1.getX() + s2.getX()) / 2;
        double y = (s1.getY() + s2.getX()) / 2;
        Solution solution = new Solution(Math.min(Math.max(x, minValue), maxValue), Math.max(Math.min(y, maxValue), minValue));
        mutate(solution);
        return solution;
    }

    private Solution select() {
        double random = Math.random();
        if(random <= 0.5)
            return selectTournament();
        else
            return selectProportionalToAdequation();
    }

    private Solution selectTournament() {
        Random random = new Random();
        int[] randomIndexes = new int[]{
            random.nextInt(400),
            random.nextInt(400),
            random.nextInt(400),
            random.nextInt(400),
            random.nextInt(400)
        };
        int greater = 0;
        for (int i = 1; i < randomIndexes.length; i++) {
            if(evaluate(population.get(greater)) < evaluate(population.get(i)))
                greater = i;
        }
        return population.get(greater);
    }

    private Solution selectProportionalToAdequation() {
        double worseFitness = getWorseFitness();
        double variationSummatory = 0;
        for (Solution solution : population) {
            variationSummatory += evaluate(solution) - worseFitness;
        }
        while(true) {
            for (Solution solution : population) {
                double random = Math.random();
                if (variationSummatory == 0)
                    return solution;
                if (random <= (evaluate(solution) - worseFitness) / variationSummatory)
                    return solution;
            }
        }
    }

    private Solution getBetterSolution() {
        int greater = 0;
        for (int i = 1; i < population.size(); i++) {
            if(evaluate(population.get(greater)) < evaluate(population.get(i)))
                greater = i;
        }
        return population.get(greater);
    }

    private double getWorseFitness() {
        int worse = 0;
        for (int i = 1; i < population.size(); i++) {
            if(evaluate(population.get(worse)) > evaluate(population.get(i)))
                worse = i;
        }
        return evaluate(population.get(worse));
    }

}
