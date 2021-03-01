package ex1.sa;



import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<List<Solution>> solutions = new ArrayList<>();
        SimulatedAnnealing simulatedAnnealing =  new SimulatedAnnealing(0.99,5);
        for (int i = 0; i < 500; i++){
            Double firstValue = (Math.random() * (2.048 - (-2.048)) + -2.048);
            Double secondValue = (Math.random() * (2.048 - (-2.048)) + -2.048);
            DeJong deJong = new DeJong(firstValue,secondValue ,500 );
            solutions.add(simulatedAnnealing.init(deJong));
        }

        List<Double> finals = new ArrayList<>();
        int m = 0;
        for (List<Solution> f: solutions){
           if(f.size() - 1 > m){
               m = f.size() - 1;
           }
        }

        for (int i = 0; i < m; i++){
            finals.add(SimulatedAnnealing.getBest(solutions,i));
        }

        StringBuilder builder2 = new StringBuilder();
        int u = 0;
        for (Double value : finals) {
            builder2.append(value);
            builder2.append("\n");
            u++;
        }

        String str2 = builder2.toString();
        try{
            FileWriter myWriter = new FileWriter("finalSA.txt");
            myWriter.write(str2);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e ) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
