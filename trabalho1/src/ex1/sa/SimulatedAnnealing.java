package ex1.sa;

import java.util.*;

public class SimulatedAnnealing {
    private final double ZETA= -0.22314355131;
    private final double EULER = 0.5772156649;

    int iter;
    Double cooling;
    Random rand = new Random();

    public SimulatedAnnealing(Double cooling, int iter){
        this.cooling = cooling;
        this.iter = iter;
    }

    public List<Double[]> getSolutionsSet(Double[] initialSolution){

        List<Double[]> solution = new ArrayList<>();
        for(int i = 0; i <= 10; i++){
            double numberOne =   (Math.random() * (2.048 - (-2.048)) + -2.048);
            double numberTwo =   (Math.random() * (2.048 - (-2.048)) + -2.048);
            solution.add(new Double[]{numberOne ,numberTwo });
        }

        return  solution;
    }

    public Double getInitialTemperature(DeJong deJong, List<Double[]> initialSolutions){
        List<Double> diffs = new ArrayList<>();
        List<Double> results = new ArrayList<>();
        for(Double[] solution: initialSolutions){
            double value = deJong.calcDeJongFunction(solution[0], solution[1]);
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

        return - (totalValue / diffs.size()) / ZETA;

    }

   public void init(Double initialTemp, Double[] solution, DeJong deJong){
        double value = deJong.calcDeJongFunction(1, 1);
        Double[] finalSolution = solution;
        double temp = initialTemp;
        while (Math.round(temp) > 0.0) {
            for (int i = 0; i < this.iter; i++) {
                Double[] newSolution = getAnotherSolution(finalSolution);
                double newValue = deJong.calcDeJongFunction(newSolution[0], newSolution[1]);
                double constant = Math.pow(EULER, -((newValue - value) / temp));
                if(newValue > value){
                    finalSolution = newSolution;
                    value = newValue;
                }else {
                    double salt = rand.nextDouble();
                    if (constant > salt) {
                        finalSolution = newSolution;
                        value = newValue;
                    }
                }
            }
            temp = temp * this.cooling;
        }

      System.out.println("Solução => x: "+ finalSolution[0] + " y:" + finalSolution[1]);
       System.out.println("Resultado: " + value);
        System.out.println("Cooling" + this.cooling);
       System.out.println("Temperatura inicial: "+initialTemp + " Temperatura final: "+ temp);

   }


   private  Double[] getAnotherSolution(Double[] solution){
        Double[] newSolution = new Double[2];
        double salt = rand.nextDouble();
//       System.out.println(salt);
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

}
