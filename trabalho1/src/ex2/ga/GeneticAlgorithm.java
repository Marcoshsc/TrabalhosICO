package ex2.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GeneticAlgorithm {


    public static  List<Solution> getAPopulation(int numberOfIndividuals, Distances distances){
        Integer[][] solution = new Integer[numberOfIndividuals][9];
        for (int i = 0; i < numberOfIndividuals; i++){
            Integer [] arr = new Integer[9];
            int control = 0;
            while (control < 8) {
                int number = (int) (Math.random() * 8) + 1;
                if (!Arrays.asList(arr).contains(number)) {
                    arr[control] = number;
                    control++;
                }
            }
            arr[8] = arr[0];
            solution[i] = arr;
        }

        List<Solution> result = new ArrayList<>();
        for(Integer[] s: solution){
            Solution distanceWithResult = new Solution( Distances.calculateDistanceOfTwoPoints(s,distances), s);
            result.add(distanceWithResult);
        }

        return result;
    }



    private List<Solution> selectProportionalToAdequation(int k ,List<Solution> population) {
        List<Solution> result = new ArrayList<>();
        double worseFitness = getWorseFitness(population);
        double variationSummatory = 0;
        for (Solution solution : population) {
            variationSummatory += solution.getDistance() - worseFitness;
        }
        while (result.size() != k){

            while (true) {
                for (Solution solution : population) {
                    double random = Math.random();
                    if (variationSummatory == 0) {
                        result.add(solution);
                        break;
                    }
                    if (random <= (-solution.getDistance() - worseFitness) / variationSummatory) {
                        result.add(solution);
                        break;
                    }
                }
                if (result.size() > 0) {
                    break;
                }
            }
        }

    return result;
    }

    private double getWorseFitness(List<Solution> population) {
        int worse = 0;
        for (int i = 1; i < population.size(); i++) {
            if( population.get(worse).getDistance() < population.get(i).getDistance())
                worse = i;
        }
        return population.get(worse).getDistance();
    }

    public List<Solution> roulette(int k ,int cut, List<Solution> generation){
        Collections.sort(generation);
        List<Solution> best = generation.subList(0, cut);
        List<Solution> intern = new ArrayList<>();
        List<Solution> result = new ArrayList<>();
        for(int i = 0; i < best.size(); i++){
            int salt = (int) (( best.get(i).getDistance() / 20) * i);
            int value = (int) (100 - salt - (best.get(i).getDistance()));

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
            if(stop >= generation.size() * 3){
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

    public List<Solution> tournament2(int k, List<Solution> generation){
        List<Solution> result = new ArrayList<>();
        while (result.size() < k){
            Collections.shuffle(generation);
            List<Solution> tor2 = generation.subList(0, 2);
            if(!result.contains(tor2.get(0)))
                result.add(tor2.get(0));
        }
        return result;
    }

    public List<Solution> tournament3(int k, List<Solution> generation){
        List<Solution> result = new ArrayList<>();
        while (result.size() < k){
            Collections.shuffle(generation);
            List<Solution> tor3 = generation.subList(0, 3);
            if(!result.contains(tor3.get(0)))
                result.add(tor3.get(0));
        }
        return result;
    }

    public List<Solution> tournament5(int k, List<Solution> generation){
        List<Solution> result = new ArrayList<>();
        while (result.size() < k){
            Collections.shuffle(generation);
            List<Solution> tor5= generation.subList(0, 5);
            if(!result.contains(tor5.get(0)))
                result.add(tor5.get(0));
        }
        return result;
    }


    public List<Solution> uniform(int k, List<Solution> generation){
        Collections.shuffle(generation);
        return generation.subList(0,k);
    }


    public List<Solution> truncate(int k, int cut,List<Solution> generation) {
        Collections.sort(generation);
        List<Solution> best = generation.subList(0, cut);
        Collections.shuffle(best);
        return best.subList(0,k);
    }


    private Integer[]  getPartial(Integer[] solution, int[] cut){
        Integer[] result = new Integer[8];
        for (int i =0; i < solution.length - 1; i++){
            if(i < cut[0]){
                result[i] = 0;
                continue;
            }
            if(i > cut[1]){
                result[i] = 0;
                continue;
            }
            result[i] = solution[i];
        }
        return result;
    }


    private Integer[] addMutation(Integer[] solution){
        int from;
        int to;
        do{
            from = (int) (Math.random() * 8) ;
            to = (int) (Math.random() * 8) ;
        }while (from == to);

        if(Math.random() < 0.05){
            Integer temp = solution[to];
            solution[to] = solution[from];
            solution[from] = temp;
        }
        return solution;
    }


    private Integer[] getReturnTrip(Integer[] solution){
        Integer[] solutionWithReturnTrip = new Integer[9];
        for(int i = 0; i < solution.length; i++){
            solutionWithReturnTrip[i] = solution[i];
        }
        solutionWithReturnTrip[solutionWithReturnTrip.length -1 ] = solution[0];
        return solutionWithReturnTrip;
    }
    private Integer[] competeArrayOfSolutionsWithAnotherValues(Integer[] solution1, Integer[] solution2, Integer[] arr){
        for(int i = 0; i < arr.length; i++){
            if(arr[i] != 0){
                continue;
            }
            if(!Arrays.asList(arr).contains(solution1[i])){
                arr[i] = solution1[i];
            }else if(!Arrays.asList(arr).contains(solution2[i])){
                arr[i] = solution2[i];
            }else {
                for (int j = 0; j < solution2.length - 1; j++) {
                    if (!Arrays.asList(arr).contains(solution2[j]))
                        arr[i] = solution2[j];
                }
            }
        }
        return arr;
    }
    public Integer[][] crossover(Solution s1, Solution s2){
        int[] cut = new int[2];
        cut[0] = (int) (Math.random() * 3);
        cut[1] = (int) ((Math.random() * 4) + 2);

        Integer[] c1 = addMutation(competeArrayOfSolutionsWithAnotherValues(
                s1.getSolution(), s2.getSolution(), getPartial(s2.getSolution(), cut)));

        Integer[] c2 = addMutation(competeArrayOfSolutionsWithAnotherValues(
                s2.getSolution(), s1.getSolution(), getPartial(s1.getSolution(), cut)));

        return new Integer[][]{getReturnTrip(c1), getReturnTrip(c2)};
    }

    public List<Solution> getANewPopulation(List<Solution> b, Distances distances){
        List<Solution> result = new ArrayList<>();
        for(int j =0; j < b.size() - 1; j++){
            if( j % 2 != 0) {
                Integer[][] newSolutions = this.crossover(b.get(j), b.get(j + 1));
                for (Integer[] s : newSolutions) {
                    result.add(Distances.calculateDistanceOfTwoPointsAndGetASolution(s, distances));
                }
                result.add(b.get(j));
            }
        }
        return result;
    }

    private List<Solution> selectSelection(int k, int cut, List<Solution> solutions, int select){
        switch (select){
            case 0: return uniform(k, solutions);
            case 1:  return selectProportionalToAdequation(k, solutions);
            case 2:  return roulette(k,cut,solutions);
            case 3: return truncate(k, cut,solutions);
            case 4: return tournament2(k,solutions);
            case 5: return tournament3(k,solutions);
            default: return tournament5(k,solutions);
        }
    }
    public List<Solution> init(List<Solution> first, Distances distances, int type){
        List<Solution> result = new ArrayList<>();
        Double bestDistance = null;
        Integer[] bestSolution;
        List<Solution> generation = new ArrayList<>(first);
        for(int i = 0; i <50; i ++){
            for(int j = 0; j < 3; j++) {
                List<Solution> best = this.selectSelection(10, 20, new ArrayList<>(generation), type);
                List<Solution> newPopulation = getANewPopulation(best, distances);
                generation.addAll(newPopulation);
                generation.addAll(best);
                List<Solution> solutions = new ArrayList<>(generation);
                Collections.sort(solutions);
                generation.clear();
                generation.addAll(solutions.subList(0, 30));
                generation.addAll(GeneticAlgorithm.getAPopulation(20, distances));
            }
            bestDistance = generation.get(0).getDistance();
            bestSolution = generation.get(0).getSolution();
            result.add(new Solution(bestDistance,bestSolution));
        }
       return result;
    }

    public static Double getBest(List<List<Solution>> el, int numberOfColumn){
        List<Solution> column = new ArrayList<>();

        for(List<Solution> x: el){
            column.add(x.get(numberOfColumn));
        }

        Double result = 0d;
        for(Solution x: column){
            result += x.getDistance();
        }

        return result / column.size();
    }

}
