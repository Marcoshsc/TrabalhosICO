package anneling2;

import java.util.Arrays;

public class Solution {

    private int[] stops;

    public Solution(int[] stops) {
        this.stops = stops;
    }

    public int[] getStops() {
        return stops;
    }

    public void replace(Solution solution) {
        this.stops = solution.getStops();
    }

    public Solution copy() {
        int[] copiedStops = new int[8];
        for (int i = 0; i < stops.length; i++) {
            copiedStops[i] = stops[i];
        }
        return new Solution(copiedStops);
    }

    @Override
    public String toString() {
        return "Solution{" +
                "stops=" + Arrays.toString(stops) +
                '}';
    }
}
