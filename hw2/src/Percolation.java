import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    int side;
    WeightedQuickUnionUF p1;  //using for percolates
    WeightedQuickUnionUF p2; //using for isfull, the only difference with p1 is
                             //p1 doesn't has a dot union with the bottom row, p2
                             //is built to make sure that method isFull works well.
    int [][] grid;
    int size;

    public Percolation(int N) {
        if(N <= 0){
            throw new IllegalArgumentException("N must be positive number");
        }
        this.side = N;
        this.p1 = new WeightedQuickUnionUF(N * N + 2);
        this.p2 = new WeightedQuickUnionUF(N * N + 1);
        this.grid = new int[N][N];
        this.size = 0;
        for(int i = 0; i < N; i++){    //union first with top row, union last with bottom row.
            p2.union(0, xyTo1D(0, i) + 1);
            p1.union(0, xyTo1D(0, i) + 1);
            p1.union(N * N + 1, xyTo1D(N - 1, i) + 1);
        }
    }

    public int xyTo1D(int row, int col){
        return row * this.side + col;
    }

    public void open(int row, int col) {
        if(row < 0 || row >= this.side || col < 0 || col >= this.side){
            throw new IllegalArgumentException("index out of range!");
        }
        grid[row][col] = 1;
        size += 1;
        //do the union to left, right, up, down.
        if(col - 1 >= 0 && isOpen(row, col - 1)){
            p1.union(xyTo1D(row, col) + 1, xyTo1D(row, col - 1) + 1);
            p2.union(xyTo1D(row, col) + 1, xyTo1D(row, col - 1) + 1);
        }
        if(col + 1 <= this.side - 1 && isOpen(row, col + 1)){
            p1.union(xyTo1D(row, col) + 1, xyTo1D(row, col + 1) + 1);
            p2.union(xyTo1D(row, col) + 1, xyTo1D(row, col + 1) + 1);
        }
        if(row - 1 >= 0 && isOpen(row - 1, col)){
            p1.union(xyTo1D(row, col) + 1, xyTo1D(row - 1, col) + 1);
            p2.union(xyTo1D(row, col) + 1, xyTo1D(row - 1, col) + 1);
        }
        if(row + 1 <= this.side - 1 && isOpen(row + 1, col)){
            p1.union(xyTo1D(row, col) + 1, xyTo1D(row + 1, col) + 1);
            p2.union(xyTo1D(row, col) + 1, xyTo1D(row + 1, col) + 1);
        }

    }

    public boolean isOpen(int row, int col) {
        if(row < 0 || row >= this.side || col < 0 || col >= this.side){
            throw new IllegalArgumentException("index out of range!");
        }
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        if(row < 0 || row >= this.side || col < 0 || col >= this.side){
            throw new IllegalArgumentException("index out of range!");
        }
        return isOpen(row, col) && p2.connected(xyTo1D(row, col) + 1, 0);
    }

    public int numberOfOpenSites() {
        return this.size;
    }

    public boolean percolates() {
        return p1.connected(0, this.side * this.side + 1);
    }
}


