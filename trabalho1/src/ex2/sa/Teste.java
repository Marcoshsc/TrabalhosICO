package ex2.sa;

import ex2.sa.SimulatedAnneling;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Teste {
    public static void main(String[] args) {
        SimulatedAnneling ga = new SimulatedAnneling();
//        System.out.println(ga.evaluate(new Solution(1, 1)));
        List<SimulatedAnneling> solutions = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            System.out.println("Execution " + i);
            SimulatedAnneling ex = new SimulatedAnneling();
            ex.execute();
            solutions.add(ex);
        }
        List<List<Double>> doubleMatrix = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            List<Double> values = new ArrayList<>(solutions.get(i).fitnessPerTemperature);
            doubleMatrix.add(values);
        }
        StringBuilder builder = new StringBuilder();
        for (List<Double> line : doubleMatrix) {
            for (Double column : line) {
                builder.append(column);
                builder.append(",");
            }
            builder.append("\n");
        }
        String str = builder.toString();
        try {
            FileWriter myWriter = new FileWriter("anneling2.csv");
            myWriter.write(str);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
