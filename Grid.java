/*
 * Author: yd.zhou
 * Class: Grid 
 * Description: A grid locates an element for the domain.
 */

public class Grid {
    public int color;
    public int power;
    public int nextColor;
    public int nextPower;
    
    public Grid(int c, int p) {
        color = c;
        power = p;
        nextColor = c;
        nextPower = p;
    }

    public void set(int c, int p) {
        color = c==-1 ? color : c;
        power = p==-1 ? power : p;
    }
}
