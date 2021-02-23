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

        while (result.size() != k){
            int number = (int) (Math.random() * intern.size());
            if(!result.contains(intern.get(number)))
                result.add(intern.get(number));
        }
        return result;
    }
    public List<Solution> tournament(int k, int cut, List<Solution> generation){
        Collections.sort(generation);
        List<Solution> result = new ArrayList<>();
        List<Solution> best = generation.subList(0, cut);
        result.add(best.get(0));
        List<Solution> listWithoutBest = best.subList(1, best.size() - 1);
        Collections.shuffle(listWithoutBest);
        result.addAll(listWithoutBest.subList(0,k - 1));
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
            Integer[][] newSolutions = this.crossover(b.get(j), b.get(j + 1));
            for(Integer[] s : newSolutions){
                result.add(Distances.calculateDistanceOfTwoPointsAndGetASolution(s, distances));
            }
            result.add(b.get(j));
        }
        return result;
    }

    public void init(List<Solution> first, int generations, Distances distances){
        Double bestDistance = null;
        Integer[] bestSolution = new Integer[0];
        List<Solution> generation = new ArrayList<>(first);
        for(int i = 0; i <generations; i ++){
            List<Solution> best = this.roulette(15,30, new ArrayList<>(generation));
            generation.clear();
            List<Solution> newPopulation = getANewPopulation(best,distances);
            generation.addAll(newPopulation);
            List<Solution> solutions = GeneticAlgorithm.getAPopulation(100 - generation.size(), distances);
            generation.addAll(solutions);
            if(i == generations - 1){
                Collections.sort(generation);
                bestDistance = generation.get(0).getDistance();
                bestSolution = generation.get(0).getSolution();
            }
           
        }
        System.out.println("Distance ===> "+ bestDistance);
        System.out.print("Solution ===> {");
        for (Integer i : bestSolution ){
            System.out.print( " " + i+ " ");
        }
        System.out.print("}");
    }

}
