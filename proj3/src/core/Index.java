package core;


/**
helper class, help to create or get the indices in Tetile[][]
 */
public class Index {

    private int x;
    private int y;

    /**
     *Constructor
     * @param col: the column corresponds to x-coordinate;
     * @param row: the row corresponds to y-coordinate;
     *
     */
    public Index(int col, int row) {
        this.x = col;
        this.y = row;
    }


    /**
     *@return x-coordinate;
     */
    public int GetX() {
        return x;
    }

    /**
     *
     *@return y-coordinate
     */
    public int GetY() {
        return y;
    }
}
