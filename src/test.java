import java.util.*;
public class test {
    public static void main(String[] args) {
        HashedPriorityQueue<Grid> p = new HashedPriorityQueue<Grid>(new Comparator<Grid>(){
            public int compare(Grid g1, Grid g2) {
                return g2.power - g1.power;
            }
        });
        p.add(new Grid(2, 8));
        p.add(new Grid(2, 3));
        Grid a = new Grid(2, 2);
        Grid b = new Grid(2, 5);
        p.add(a);
        p.add(b);
        p.add(new Grid(2, 4));

        a.power = 6;
        b.power = 0;
        /*System.out.println(p.get(a).power);*/
        p.update(a);
        p.update(b);
        System.out.println(p.poll().power);
        System.out.println(p.poll().power);
        System.out.println(p.poll().power);
        System.out.println(p.poll().power);
        System.out.println(p.poll().power);
    }
}
