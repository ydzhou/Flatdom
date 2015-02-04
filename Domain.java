/*
 * Author: yd.zhou
 * Class: Domain
 * Description: It is a 2D grid where a grid onside represents an element with power index ranging from 0 to 9.
 */

import java.util.*;

public class Domain {

    // Array of colors, 0: RESET, 1: WHITE, 2: RED, 3: GREEN, 4: YELLOW, 5: BLUE, 6: PURPLE, 7: CYAN
    private final String[] color = {"\u001B[37m", "\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m", "\u001B[36m", "\u001B[0m"};
    private final char[] event = {'w', 'd', 'e', '.'};
    private List<List<Grid>> d;

    private PriorityQueue<Event> eventQueue;
    private HashMap<Grid, Event> gridEventMap;

    public Domain(int n, int m, int maxC, int maxP) {
        d = new ArrayList<List<Grid>>();
        Random rand = new Random();
        for (int i=0; i<n; i++) {
            List<Grid> tmp = new ArrayList<Grid>();
            for (int j=0; j<m; j++) {
                tmp.add(new Grid(rand.nextInt(maxC+1), rand.nextInt(maxP+1)));
            }
            d.add(tmp);
        }
        init(n, m);
    }

    // flag: 1. print grid; 2. print event
    public void print(int flag) {
        if (flag == 1) {
            for (int i=0; i<d.size(); i++) {
                for (int j=0; j<d.get(i).size(); j++) {
                    /*System.out.print(d.get(i).get(j).color);*/
                    System.out.print(color[d.get(i).get(j).color] + d.get(i).get(j).power + color[7]);
                }
                System.out.println();
            }
        } else if (flag == 2) {
            for (int i=0; i<d.size(); i++) {
                for (int j=0; j<d.get(i).size(); j++) {
                    if (d.get(i).get(j).color == 0) {
                        System.out.print(".");
                    } else {
                        System.out.print(event[gridEventMap.get(d.get(i).get(j)).eve]);
                    }
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    /*
     * Rule:
     * Each grid has at most four neighbors, up, down, left and right. Each grid has it own clan color and power index. The grid with black color represents neutral and does not have following actions.
     * During each round,
     * two adjacent grids that share the same color, 
     * two adjacent grids that have different color,
     */

    /*
     * Algorithm:
     * Every time frame, the event with highest possibility occurs.
     */
    public void init(int n, int m) {
        eventQueue = new PriorityQueue<Event>(n * m, new Comparator<Event>(){
            public int compare(Event e1, Event e2) {
                if (e1.getGrids().get(0).color == 0) return 1;
                if (e2.getGrids().get(0).color == 0) return -1;
                return (int)e2.getPoss() - (int)e1.getPoss();
            }
        });
        gridEventMap = new HashMap<Grid, Event>();
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                /*if (d.get(i).get(j).color == 0) continue;*/
                Event e = new Event(d, i, j);
                gridEventMap.put(d.get(i).get(j), e);
                eventQueue.add(e);
            }
        }
    }

    // Every time frame, an event with highest possibility occurs.
    public void emulate() {
        Event e = eventQueue.poll();
        System.out.println(e.row + " " + e.col);
        e.occurs();
        List<Grid> grids = e.getGrids();
        for (int i=0; i<grids.size(); i++) {
            if (grids.get(i).color == 0) continue;
            eventQueue.remove(gridEventMap.get(grids.get(i)));
            gridEventMap.get(grids.get(i)).setPoss();
            eventQueue.add(gridEventMap.get(grids.get(i)));
        }
    }
}
