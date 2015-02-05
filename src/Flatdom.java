/*
 * Author: yd.zhou
 * Class: Flatdom
 * Description:
 */

import java.util.*;

public class Flatdom {

    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("usage: java Flatdom n m maxC maxP maxT");
            System.out.println("       n, m: size of the domain");
            System.out.println("       maxC: maximum number of clans, in [1, 6]");
            System.out.println("       maxP: maximum power index, in [1, 9]");
            System.out.println("       maxT: maximum time rounds");
            return;
        }
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        int maxC = Integer.parseInt(args[2]);
        int maxP = Integer.parseInt(args[3]);
        int maxT = Integer.parseInt(args[4]);
        if (n <= 0 || m <= 0) return;
        if (maxC <=0 || maxC > 6) return;
        if (maxP <=0 || maxP > 9) return;
        Domain d = new Domain(n, m, maxC, maxP);
        for (int t = 0; t < maxT; t++) {
            System.out.println("time " + t);
            d.print();
            if (!d.emulate()) break;
            System.out.println("----------");
        }
    }
}
