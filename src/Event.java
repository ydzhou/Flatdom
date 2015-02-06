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
    public double[] poss = new double[4];
    public int[] power = new int[3];
    public int[] count = new int[3];
    public List<Grid> grids;

    private final double warCoe = 1.0;
    private final double warBaseCoe = 0.1;
    private final double devCoe = 2.0;
    private final double expCoe = 1.0;
    private final double expBaseCoe = 0;
    private final double warMaxPower = (9);
    private final double devMaxPower = (4*9)+9;
    private final double expMaxPower= 9;
    private final double devIncCoe = 0.25;

    public Event(List<List<Grid>> d, int r, int c) {
        row = r;
        col = c;
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
        eve = 3;
        if (grids.get(0).color == 0) return;
        power[0] = 9; power[1] = 0; power[2] = 9;
        count[0] = -1; count[1] = 0; count[2] = -1;
        poss[0] = -1; poss[1] = -1; poss[2] = -1;
        for (int i=1; i<grids.size(); i++) {
            if (grids.get(i).color == grids.get(0).color) {
                power[1] += grids.get(i).power;
                count[1]++;
            } else if (grids.get(i).color == 0) {
                if (power[2] > grids.get(i).power) {
                    power[2] = grids.get(i).power;
                    count[2] = i;
                }
            } else {
                if (power[0] >= grids.get(i).power) {
                    power[0] = grids.get(i).power;
                    count[0] = i;
                }
            }
        }      
        // Calculate possibility of each action 
        // war possibility: abs(diffPower - selfPower)
        /*System.out.println(row+ " "+col+": "+count[0]+" "+count[1]+" "+count[2]+" ");*/
        if (count[0] != -1) poss[0] = (grids.get(0).power - power[0]) / warMaxPower * warCoe + warBaseCoe;
        // develop possibility: (samePower + selfPower)
        if (count[1] != 0 && grids.get(0).power < 9) poss[1] = (grids.get(0).power + power[1]) / devMaxPower * devCoe;
        // explore possibility: (selfPower - neutPower)
        if (count[2] != -1) poss[2] = (grids.get(0).power - power[2]) / expMaxPower * expCoe + expBaseCoe;
        /*System.out.println(row+ " "+col+": "+poss[0]+" "+poss[1]+" "+poss[2]+" ");*/
        double maxPoss = -1;
        for (int i=0; i<poss.length; i++) {
            if (maxPoss <= poss[i]) {
                eve = i;
                maxPoss = poss[i];
            }
        }
        /*System.out.println(row+ " "+col+" "+eve+": "+poss[0]+" "+poss[1]+" "+poss[2]);*/
    }

    public void occurs() {
        if (grids.get(0).color == 0) return;
        int i, casualty;
        switch (eve) {
            case 0: // war
                i = count[0];
                casualty = grids.get(0).power - grids.get(i).power;
                if (casualty == 0) casualty = grids.get(0).power/3;
                grids.get(0).power -= ((casualty>grids.get(i).power ? grids.get(i).power : casualty) + 2);
                grids.get(i).power -= (casualty>grids.get(i).power ? grids.get(i).power : casualty);
                if (grids.get(i).power <= 0) {
                    grids.get(i).power = 0;
                    grids.get(i).set(0, -1);
                }
                break;
            case 1: // develop
                for (int j=0; j<grids.size(); j++) {
                    if (grids.get(j).color == grids.get(0).color) {
                        grids.get(j).power += (j==0 ? (int)((power[1] + grids.get(j).power)*devIncCoe)+1 : 1);
                    }
                    if (grids.get(j).power > 9) {
                        grids.get(j).power = 9;
                    }
                }
                break;
            case 2: // explore
                i = count[2];
                casualty = grids.get(0).power - grids.get(i).power;
                grids.get(0).power -= (casualty>grids.get(i).power ? grids.get(i).power : casualty) + 1;
                grids.get(i).power -= (casualty>grids.get(i).power ? grids.get(i).power : casualty);
                if (grids.get(i).power <= 0) {
                    grids.get(i).power = 0;
                    grids.get(i).set(grids.get(0).color, -1);
                }
                break;
            case 3: // stay
                break;
        }
    }

    // Return possibility in integer with two decimal accuracy kept.
    public int getPoss() {
        return (int)(poss[eve] * 100);
    }

    public List<Grid> getGrids() {
        return grids;
    }
}
