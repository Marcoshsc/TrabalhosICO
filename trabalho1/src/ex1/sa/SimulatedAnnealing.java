package ex1.sa;

import java.util.*;

public class SimulatedAnnealing {
    private final double ZETA= -0.22314355131;
    private final double EULER = 2.71828182845904523536028747135266249775724709369995;
    int iter;
    Double cooling;

    public SimulatedAnnealing(Double cooling, int iter){
        this.cooling = cooling;
        this.iter = iter;
    }


    private Double getInitialTemperature(DeJong deJong){
        List<Double> diffs = new ArrayList<>();
        List<Double> results = new ArrayList<>();
        for(Double[] solution: deJong.getSetOfSolutions()){
            double value = DeJong.calcDeJongFunction(solution);
            if(!results.isEmpty()){
              double diff =  results.get(results.size() - 1) - value ;
              if(diff < 0)
                  diff = diff * -1;
              diffs.add(diff);
            }
            results.add(value);
        }

        Double totalValue = 0d;

        for(Double diff: diffs){
            totalValue += diff;
        }

        return   - (totalValue / diffs.size()) / ZETA;
    }

   public List<Solution> init(DeJong deJong){
        List<Solution> result = new ArrayList<>();
        double value = DeJong.calcDeJongFunction(deJong.getInitialSolution());
        Double[] finalSolution = deJong.getInitialSolution();
        double temp = getInitialTemperature(deJong);
        while (Math.round(temp) > 0.0) {
            for (int i = 0; i < this.iter; i++) {
                Double[] newSolution = deJong.getAnotherSolution(finalSolution);
                double newValue = DeJong.calcDeJongFunction(newSolution);
                if (newValue > value) {
                    finalSolution = newSolution;
                    value = newValue;
                } else if (temp != 0) {
                    double constant = Math.pow(EULER, (-1 * (-1 * (newValue - value)) / temp));
                    if (constant > Math.random()) {
                        finalSolution = newSolution;
                        value = newValue;
                    }
                }
            }
            result.add(new Solution(finalSolution,value));
            temp = temp * this.cooling;
        }
       return result;

   }

    public static Double getBest(List<List<Solution>> el, int numberOfColumn){
        List<Solution> column = new ArrayList<>();
        for(int i=0; i< el.size() - 1; i++){
            if(el.get(i).size() - 1 > numberOfColumn)
                column.add(el.get(i).get(numberOfColumn));
        }
        Double result = 0d;
        for(Solution x: column){
            result += x.getValue();
        }
        return result / column.size();
    }
}
