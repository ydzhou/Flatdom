/*
 * Author: yd.zhou
 * Class: Domain
 * Description: It is a 2D grid where a grid onside represents an element with power index ranging from 0 to 9.
 */

import java.util.*;

public class Domain {

    // Array of colors, 1: WHITE, 2: RED, 3: GREEN, 4: YELLOW, 5: BLUE, 6: PURPLE, 7: CYAN, 8: RESET
    private final String[] color = {"\u001B[37m", "\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m", "\u001B[36m", "\u001B[0m"};
    private final char[] event = {'w', 'd', 'e', '.'};
    private List<List<Grid>> d;

    private HashedPriorityQueue<Event> eventQueue;
    private HashMap<Grid, Event> gridEventMap;

    public Domain(int n, int m, int maxC, int maxP) {
        init(n, m, maxC, maxP);
    }

    public void print() {
        for (int i=0; i<d.size(); i++) {
            String g = "";
            String e = "";
            for (int j=0; j<d.get(i).size(); j++) {
                g += (color[d.get(i).get(j).color] + d.get(i).get(j).power + color[7]);
                e += event[gridEventMap.get(d.get(i).get(j)).eve];
            }
            System.out.println(g + "    " + e);
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
    public void init(int n, int m, int maxC, int maxP) {
        d = new ArrayList<List<Grid>>();
        Random rand = new Random();
        for (int i=0; i<n; i++) {
            List<Grid> tmp = new ArrayList<Grid>();
            for (int j=0; j<m; j++) {
                tmp.add(new Grid(rand.nextInt(maxC+1), rand.nextInt(maxP+1)));
            }
            d.add(tmp);
        }

        /*List<Grid> tmp = new ArrayList<Grid>();*/
        /*tmp.add(new Grid(1, 9));*/
        /*tmp.add(new Grid(0, 9));*/
        /*tmp.add(new Grid(1, 9));*/
        /*d.add(tmp);*/
        /*tmp = new ArrayList<Grid>();*/
        /*tmp.add(new Grid(2, 9));*/
        /*tmp.add(new Grid(1, 9));*/
        /*tmp.add(new Grid(1, 9));*/
        /*d.add(tmp);*/
        /*tmp = new ArrayList<Grid>();*/
        /*tmp.add(new Grid(0, 9));*/
        /*tmp.add(new Grid(0, 9));*/
        /*tmp.add(new Grid(0, 9));*/
        /*d.add(tmp);*/
        
        eventQueue = new HashedPriorityQueue<Event>(new Comparator<Event>(){
            public int compare(Event e1, Event e2) {
                return e2.getPoss() - e1.getPoss();
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

        List<Event> h = eventQueue.getHeap();
        for (int i=1; i<h.size(); i++) {
            System.out.print(h.get(i).getPoss() + " ");
        }
        System.out.println();
    }

    // Every time frame, an event with highest possibility occurs.
    public boolean emulate() {
        Event e = eventQueue.peek();
        // If the event with highest possibility is stay, then the domain remain stable.
        if (e.eve == 3) return false;
        System.out.println(e.row + " " + e.col + " " + event[e.eve]);
        List<Event> tmp = eventQueue.getHeap();
        /*for (int i=1; i<tmp.size(); i++) {*/
        /*    System.out.print(tmp.get(i).getPoss() + " ");*/
        /*}*/
        /*System.out.println();*/
        e.occurs();
        List<Grid> grids = e.getGrids();
        for (int i=0; i<grids.size(); i++) {
            gridEventMap.get(grids.get(i)).setPoss();
            eventQueue.update(gridEventMap.get(grids.get(i)));
        }
        tmp = eventQueue.getHeap();
        /*for (int i=1; i<tmp.size(); i++) {*/
        /*    System.out.print(tmp.get(i).getPoss() + " ");*/
        /*}*/
        /*System.out.println();*/
        return true;
    }
}
