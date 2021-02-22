package ex1.sa;

import java.util.Set;

public class DeJong {
//   int x;
//   int y;

//   public DeJong(int x, int y) {
//       this.x = x;
//       this.y = y;
//   }

    public double calcDeJongFunction(double x, double y){
        return 3905.93 - Math.pow(((100 * Math.pow(x,2)) - (100*y)),2) - Math.pow((1 - x),2);
    }
}
