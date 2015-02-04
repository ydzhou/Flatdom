/*
 * Author: yd.zhou
 * Class: Event
 * Description: an event represents the most possible action the element on current gird tends to take. There are 3 events: war, develop or explore.
 * War: Adjacent grid has different color.
 * Develop: Adjacent grid shares the same color.
 * Explore: Adjacent grid is neutral.
 */

import java.util.*;

public class Event {
    public int row, col, eve;
    public double[] poss = {-1, -1, -1, 0};
    public List<Grid> grids;

    private final double warCoe = 4.0;
    private final double devCoe = 4.0;
    private final double expCoe = 1.0;
    private final double warMaxPoss = (4*9);
    private final double devMaxPoss = (4*9)+9;
    private final double expMaxPoss = 9;

    public Event(List<List<Grid>> d, int r, int c) {
        row = r;
        col = c;
        eve = 3;
        grids = new ArrayList<Grid>();
        grids.add(d.get(row).get(col));
        // Add all the grids related to current event
        if (row > 0) grids.add(d.get(row-1).get(col));
        if (row < d.size()-1) grids.add(d.get(row+1).get(col));
        if (col > 0) grids.add(d.get(row).get(col-1));
        if (col < d.get(row).size()-1) grids.add(d.get(row).get(col+1));
        setPoss();
    }

    public void setPoss() {
        if (grids.get(0).color == 0) {
            return;
        }
        for (int i=1; i<grids.size(); i++) {
            if (grids.get(i).color == grids.get(0).color) {
                poss[1] += grids.get(i).power;
            } else if (grids.get(i).color == 0) {
                poss[2] += grids.get(i).power;
            } else {
                poss[0] += grids.get(i).power;
            }
        }      
        // Calculate possibility of each action 
        // war possibility: abs(diffPower - selfPower)
        if (poss[0] != -1) poss[0] = Math.abs(grids.get(0).power - poss[0]) / warMaxPoss * warCoe;
        // develop possibility: (samePower + selfPower)
        if (poss[1] != -1) poss[1] = (grids.get(0).power + poss[1]) / devMaxPoss * devCoe;
        // explore possibility: (selfPower - neutPower)
        if (poss[2] != -1) poss[2] = (grids.get(0).power - poss[2]) / expMaxPoss * expCoe;
        /*System.out.println(row+ " "+col+" "+poss[0]+" "+poss[1]+" "+poss[2]);*/
        double maxPoss = -1;
        for (int i=0; i<poss.length; i++) {
            if (maxPoss < poss[i]) {
                eve = i;
                maxPoss = poss[i];
            }
        }
    }

    public double getPoss() {
        return poss[eve];
    }
}
