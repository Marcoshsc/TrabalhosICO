package genetic;

import java.util.*;

public class GeneticAlgorithm {

    private double minValue = -2.048;
    private double maxValue = 2.048;
    private int popSize = 40;
    private List<Solution> population;
    private int iterations = 10;
    private double mutationRate = 0.02;
    List<Solution> copy;
    List<Double> bestForEachGeneration;
    List<Double> fitnessValues;


    public Solution execute() {
        generateInitialPopulation();
        bestForEachGeneration = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            fitnessValues = new ArrayList<>();
            for (Solution solution : population)
                fitnessValues.add(evaluate(solution));
            this.copy = new ArrayList<>(population);
            copy.sort((a, b) -> {
                double aValue = fitnessValues.get(a.index);
                double bValue = fitnessValues.get(b.index);
                return Double.compare(bValue, aValue);
            });
            List<Solution> newPopulation = new ArrayList<>();
            int k = 0;
            for (int j = 0; j < popSize; j++) {
                Solution s1 = select();
                Solution s2 = select();
                Solution s3 = reproduce(s1, s2);
                newPopulation.add(s3);
                s3.index = k;
                k++;
            }
            bestForEachGeneration.add(evaluate(getBetterSolution()));
            updateBetters(newPopulation);
        }
        return getBetterSolution();
    }

    private void updateBetters(List<Solution> newSolutions) {
        List<Solution> copys = new ArrayList<>(population);
        copys.addAll(newSolutions);
        copys.sort((a, b) -> {
            double aValue = evaluate(a);
            double bValue = evaluate(b);
            return Double.compare(bValue, aValue);
        });
        this.population = copys.subList(0, popSize);
    }

    public Solution uniform(){
        int random = new Random().nextInt(population.size());
        return population.get(random);
    }


    public Solution truncate() {
        List<Solution> best = copy.subList(0, 5);
        int random = new Random().nextInt(5);
        return best.get(random);
    }

    public double evaluate(Solution solution) {
        return 3905.93 - (100*Math.pow(Math.pow(solution.getX(), 2) - solution.getY(), 2)) - Math.pow((1 - solution.getX()), 2);
//        return 3905.93 - Math.pow(((100 * Math.pow(solution.getX(),2)) - (100*solution.getY())),2) - Math.pow((1 - solution.getX()),2);
    }

    private Solution generateRandomSolution() {
        return new Solution(minValue + (maxValue - minValue) * Math.random(), minValue + (maxValue - minValue) * Math.random());
    }

    private void generateInitialPopulation() {
        population = new ArrayList<>();
        for (int i = 0; i < popSize; i++) {
            Solution solution = generateRandomSolution();
            solution.index = i;
            population.add(solution);
        }
    }

    private void mutate(Solution solution) {
        double number = Math.random();
        if(number <= mutationRate) {
            double isX = Math.random();
            if(isX >= 0.5) {
                double newX = solution.getX() * Math.random();
                solution.setX(Math.min(Math.max(newX, minValue), minValue));
            }
            else
                solution.setY(Math.min(Math.max(solution.getY() * Math.random(), minValue), maxValue));
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
        return selectTournament();

//        return roulette(1, 5).get(0);
//
//        return selectProportionalToAdequation();
//
//        return uniform();
//
//        this.copy = new ArrayList<>(population);
//        copy.sort((a, b) -> {
//            double aValue = -evaluate(a);
//            double bValue = -evaluate(b);
//            return Double.compare(aValue, bValue);
//        });
//        return truncate();
    }

    public List<Solution> roulette(int k ,int cut){
        List<Solution> best = copy.subList(0, cut);
        List<Solution> intern = new ArrayList<>();
        List<Solution> result = new ArrayList<>();
        for(int i = 0; i < best.size(); i++){
            int salt = (int) (( -fitnessValues.get(best.get(i).index) / 20) * i);
            int value = (int) (100 - salt - (-fitnessValues.get(best.get(i).index)));

            for(int j = 0;j <= value; j++){
                intern.add(best.get(i));
            }
        }
        int stop = 0;
        boolean incomplete = false;
        while (result.size() != k){
            int number = (int) (Math.random() * intern.size());
            if(!result.contains(intern.get(number)))
                result.add(intern.get(number));
            if(stop >= population.size() * 3){
                incomplete = true;
                break;
            }
            stop++;
        }
        if(incomplete){
            int u = 0;
            Collections.shuffle(intern);
            while (result.size() != k){
                if(u >= intern.size()){
                    break;
                }
                if(!result.contains(intern.get(u)))
                    result.add(intern.get(u));
                u++;
            }
        }
        return result;
    }

    private Solution selectTournament() {
        Random random = new Random();
        int[] randomIndexes = new int[]{
            random.nextInt(popSize),
            random.nextInt(popSize),
            random.nextInt(popSize),
            random.nextInt(popSize),
            random.nextInt(popSize)
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
            if(fitnessValues.get(population.get(greater).index) < fitnessValues.get(population.get(i).index))
                greater = i;
        }
        return population.get(greater);
    }

    private double getWorseFitness() {
        int worse = 0;
        for (int i = 1; i < population.size(); i++) {
            if(fitnessValues.get(population.get(worse).index) > fitnessValues.get(population.get(i).index))
                worse = i;
        }
        return fitnessValues.get(population.get(worse).index);
    }

}
