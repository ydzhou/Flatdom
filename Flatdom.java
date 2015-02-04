/*
 * Author: yd.zhou
 * Class: Flatdom
 * Description:
 */

import java.util.*;

public class Flatdom {

    private final static int maxTime = 6;

    public static void main(String[] args) {
        Domain d = new Domain(6, 6, 2, 9);
        for (int t = 0; t < maxTime; t++) {
            System.out.println("time " + t);
            /*System.out.println("event: "+d.topEvent().row+" "+d.topEvent().col);*/
            d.print(1);
            d.print(2);
            d.emulate();
            System.out.println("----------");
        }
    }
}
