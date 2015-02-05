/*
 * Author: yd.zhou
 * Class: Grid 
 * Description: A grid locates an element for the domain.
 */

public class Grid {
    public int color;
    public int power;
    
    public Grid(int c, int p) {
        color = c;
        power = p;
    }

    public void set(int c, int p) {
        color = c==-1 ? color : c;
        power = p==-1 ? power : p;
    }
}
