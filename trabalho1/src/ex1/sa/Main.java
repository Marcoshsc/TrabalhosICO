package ex1.sa;


public class Main {
    public static void main(String[] args) {
        SimulatedAnnealing simulatedAnnealing =  new SimulatedAnnealing(0.99999,3);
        Double firstValue = (Math.random() * (2.048 - (-2.048)) + -2.048);
        Double secondValue = (Math.random() * (2.048 - (-2.048)) + -2.048);
        DeJong deJong = new DeJong(firstValue,secondValue ,10 );
        simulatedAnnealing.init(deJong);
    }
}
