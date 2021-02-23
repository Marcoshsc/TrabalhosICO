package ex1.sa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DeJong {


    private Double[] initialSolution = new Double[2];

//    private final double MIN_INTER
    private int numberOfSolutions;
    private Set<Double[]> setOfSolutions = new HashSet<>();

   public DeJong(Double x, Double y, int numberOfSolutions) {
       this.initialSolution[0] = x;
       this.initialSolution[1] = y;
       this.numberOfSolutions = numberOfSolutions;
       this.setOfSolutions = getSolutionsSet();
   }

    private Set<Double[]> getSolutionsSet(){

        Set<Double[]> solution = new HashSet<>();
        for(int i = 0; i <= this.numberOfSolutions; i++){
            double numberOne =   (Math.random() * (2.048 - (-2.048)) + -2.048);
            double numberTwo =   (Math.random() * (2.048 - (-2.048)) + -2.048);
            solution.add(new Double[]{numberOne ,numberTwo });
        }

       return solution;
    }

    public  Double[] getAnotherSolution(Double[] solution){
        Double[] newSolution = new Double[2];
        double salt = Math.random();
        if(salt < 0.5){
            double result =  solution[0] +  (Math.random() * (2.048 - (-solution[0])) + -solution[0]);
            newSolution[0] = Math.min(result, 2.048 + solution[0]);
        }else{
            double result  = solution[0]  - (Math.random() * (solution[0] - (-2.048)) + -2.048);
            newSolution[0] = Math.min(-2.048, result - solution[0]);
        }

        if(Math.round(salt * 10) % 2 == 0){
            double result =  solution[1] +  (Math.random() * (2.048 - (-solution[1])) + -solution[1]);
            newSolution[1] = Math.min(result, 2.048 - solution[1]);
        }else{
            double result  = solution[1]  - (Math.random() * (solution[1] - (-2.048)) + -2.048);
            newSolution[1] = Math.min(-2.048, result - solution[1]);
        }

        return newSolution;
    }

    public static double calcDeJongFunction(Double[] values){
        return 3905.93 - (100*Math.pow(Math.pow(values[0], 2) -values[1], 2)) - Math.pow((1 - values[0]), 2);
    }

    public Double[] getInitialSolution() {
        return initialSolution;
    }

    public void setInitialSolution(Double[] initialSolution) {
        this.initialSolution = initialSolution;
    }

    public int getNumberOfSolutions() {
        return numberOfSolutions;
    }

    public void setNumberOfSolutions(int numberOfSolutions) {
        this.numberOfSolutions = numberOfSolutions;
    }

    public Set<Double[]> getSetOfSolutions() {
        return setOfSolutions;
    }

    public void setSetOfSolutions(Set<Double[]> setOfsolutions) {
        this.setOfSolutions = setOfsolutions;
    }
}
