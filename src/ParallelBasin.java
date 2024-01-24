import java.util.concurrent.RecursiveTask;

public class ParallelBasin extends RecursiveTask<Integer> {

    int lo; // arguments
    int hi;
    int column;
    float[][] grid;
    static final int seqCutoff = 750;

    private String coordinates = "";
    int basins = 0; // result

     /*
     * This method initializes ParallellBasin and sets its parameters
     * @param grid, lo, hi and start column
     */
    ParallelBasin(float[][] grid, int lo, int hi, int column) {
        this.lo = lo;
        this.hi = hi;
        this.column = column;
        this.grid = grid;
    }

    /*
     * This method makes use of an if statement in order to determin whether the input array is smaller or larger than the sequential cutoff.
     * It then uses a combination of for loops and if statements in order to compare specific values inside the grid array to its surrounding values to determine basins in the terrain. 
     * It then totals the number of basins and outputs their coordinates.
     */
    protected Integer compute(){ // return answer - instead of run
        if((hi - lo) < seqCutoff) {
            float threshold = 0.01f;
            for(int r = 1; r < hi - 1; r++){
                for(int c = 1; c < column - 1; c++ ){

                    if( (grid[r][c] + threshold <= grid[r-1][c-1]) && (grid[r][c] + threshold <= grid[r-1][c])
                            && (grid[r][c] + threshold <= grid[r-1][c+1]) && (grid[r][c] + threshold <= grid[r][c-1])
                            && (grid[r][c] + threshold <= grid[r][c+1]) && (grid[r][c] + threshold <= grid[r+1][c-1])
                            && (grid[r][c] + threshold <= grid[r+1][c]) && (grid[r][c] + threshold <= grid[r+1][c+1]))
                    {
                        basins += 1;
                        coordinates = r + " " + c + "\n";
                    }
                }
            }
            return basins;
        }
        else {
            ParallelBasin left = new ParallelBasin(grid,lo,(hi+lo)/2, column);
            ParallelBasin right= new ParallelBasin(grid,(hi+lo)/2,hi, column);

            // order of next 4 lines
            // essential – why?
            left.fork();
            int rightAns = right.compute();
            int leftAns  = left.join();
            return leftAns + rightAns;
        }
    }

    /*
     * This method prints coordinates
     */
    public String getResults() {
        return coordinates;
    }
}

