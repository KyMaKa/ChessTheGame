package piece;

/**
 *
 * Coordinate Class
 * This class is used to store x, y coordinate for Piece
 *
 */
public class Coordinate {
    // coordinate
    private int x;  // x coordinate
    private int y;  // y coordinate

    /**
     * Constructor: set default x = y = -1
     */
    public Coordinate(){
        this.x = -1;
        this.y = -1;
    }

    /**
     * Constructor: set x and y
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Getter, return x
     * @return the x coordinate
     */
    public int getX(){
        return this.x;
    }

    /**
     * Setter, set x
     * @param x set value
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Getter, return Y
     * @return the y coordinate
     */
    public int getY(){
        return this.y;
    }

    /**
     * Setter, set Y
     * @param y set value
     */
    public void setY(int y){
        this.y = y;
    }
}
