package ex2.ga;


public class Distances {
    Double[][] distances;

    public Distances(Double[][] distances){
        this.distances = distances;
    }


    public Double getDistance(int from, int to){
        return this.distances[from - 1][to - 1];
    }

    public Double[][] getDistances() {
        return distances;
    }

    public void setDistances(Double[][] distances) {
        this.distances = distances;
    }

    public static Double calculateDistanceOfTwoPoints(Integer[] solution, Distances distances){
        Double result = 0d;
        for (int i = 0; i < solution.length - 1; i++){
            result += distances.getDistance(solution[i], solution[i+1]);
        }
        return result;
    }


    public static Solution calculateDistanceOfTwoPointsAndGetASolution(Integer[] solution, Distances distances){
        Double result = 0d;
        for (int i = 0; i < solution.length - 1; i++){
            result += distances.getDistance(solution[i], solution[i+1]);
        }
        return new Solution(result, solution);
    }
}


