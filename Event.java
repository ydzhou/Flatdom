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
    public int[] power = {9, 0, 9};
    public int[] count = new int[3];
    public List<Grid> grids;

    private final double warCoe = 1.0;
    private final double devCoe = 3.0;
    private final double expCoe = 1.0;
    private final double warMaxPoss = (9);
    private final double devMaxPoss = (4*9)+9;
    private final double expMaxPoss = 9;
    private final double devIncCoe = 0.25;

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
			eve = 3;
            return;
        }
        for (int i=1; i<grids.size(); i++) {
            if (grids.get(i).color == grids.get(0).color) {
                power[1] += grids.get(i).power;
                count[1]++;
            } else if (grids.get(i).color == 0) {
                power[2] = Math.min(power[2], grids.get(i).power);
                count[2]++;
            } else {
                power[0] = Math.min(power[0], grids.get(i).power);
                count[0]++;
            }
        }      
        // Calculate possibility of each action 
        // war possibility: abs(diffPower - selfPower)
        if (count[0] != 0) poss[0] = (grids.get(0).power - power[0]) / warMaxPoss * warCoe;
        // develop possibility: (samePower + selfPower)
        if (count[1] != 0) poss[1] = (grids.get(0).power + power[1]) / devMaxPoss * devCoe;
        // explore possibility: (selfPower - neutPower)
        if (count[2] != 0) poss[2] = (grids.get(0).power - power[2]) / expMaxPoss * expCoe;
        /*System.out.println(row+ " "+col+" "+poss[0]+" "+poss[1]+" "+poss[2]);*/
        double maxPoss = -1;
        for (int i=0; i<poss.length; i++) {
            if (maxPoss <= poss[i]) {
                eve = i;
                maxPoss = poss[i];
            }
        }
		if (eve == 1 && grids.get(0).power == 9) eve = 3;
    }

    public void occurs() {
        if (grids.get(0).color == 0) {
			return;
        }
        switch (eve) {
            case 0: // war
                for (int i=0; i<grids.size(); i++) {
                    if (grids.get(i).power == power[0]) {
                        int casualty = grids.get(0).power - grids.get(i).power;
                        grids.get(0).power -= (casualty>grids.get(i).power ? grids.get(i).power : casualty) + 1;
                        grids.get(i).power -= (casualty>grids.get(i).power ? grids.get(i).power : casualty);
                        if (grids.get(i).power <= 0) {
                            grids.get(i).power = 0;
                            grids.get(i).set(0, -1);
                        }
                        break;
                    }
                }
                break;
            case 1: // develop
                for (int i=0; i<grids.size(); i++) {
                    if (grids.get(i).color == grids.get(0).color) {
                        grids.get(i).power += (i==0 ? (int)((power[1] + grids.get(i).power)*devIncCoe)+1 : 1);
                    }
                    if (grids.get(i).power > 9) {
                        grids.get(i).power = 9;
                    }
                }
                break;
            case 2: // explore
                for (int i=0; i<grids.size(); i++) {
                    if (grids.get(i).power == power[2]) {
                        int casualty = grids.get(0).power - grids.get(i).power;
                        grids.get(0).power -= (casualty>grids.get(i).power ? grids.get(i).power : casualty) - 1;
                        grids.get(i).power -= (casualty>grids.get(i).power ? grids.get(i).power : casualty);
                        if (grids.get(i).power <= 0) {
                            grids.get(i).power = 0;
                            grids.get(i).set(grids.get(0).color, -1);
                        }
                        break;
                    }
                }
                break;
            case 3: // stay
                grids.get(0).power++;
                break;
        }
    }

    public double getPoss() {
        return poss[eve];
    }

    public List<Grid> getGrids() {
        return grids;
    }
}
