package ex2.ga;

import java.util.List;

public class Solution implements  Comparable<Solution>{
    private Double distance;
    private Integer[] solution;

    public Solution(Double distance, Integer[] solution) {
        this.distance = distance;
        this.solution = solution;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer[] getSolution() {
        return solution;
    }

    public void setSolution(Integer[] solution) {
        this.solution = solution;
    }


    @Override
    public int compareTo(Solution solution) {
        double d = this.distance - solution.distance;
        if(d > 0){
            return  1;
        }else if(d < 0){
            return -1;

        }else{
            return 0;
        }
    }
}
