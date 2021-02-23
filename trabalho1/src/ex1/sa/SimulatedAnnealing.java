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

   public void init(DeJong deJong){
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

                    if (constant > 0.5) {
                        finalSolution = newSolution;
                        value = newValue;
                    }
                }
            }
            temp = temp * this.cooling;
        }
       printSolution(finalSolution, value);


   }

   private void printSolution(Double[] solution, Double value){
       System.out.println("Função DeJong com a meteheurística de Simulated Annealing\n");
       System.out.format("Solução =>  X = (%s , %s)%n", solution[0], solution[1]);
       System.out.println("Resultado => F = " + value);
   }



}
