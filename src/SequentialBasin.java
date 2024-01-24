public class SequentialBasin {
    private float[][] grid;
    private int row, col, basins;
    private String coordinates;

    /*
     * This method initializes SequentialBasin and sets its parameters
     * @param grid, row and start col
     */
    public SequentialBasin(float[][] grid, int row, int col) {
        this.grid = grid;
        this.row = row;
        this.col = col;
        basins = 0;
        coordinates = "";
    }

    /*
     * This method makes use of a combination of for loops and if statements in order to compare specific values inside the grid array to its surrounding values to determine basins in the terrain. 
     * It then totals the number of basins and outputs their coordinates.
     */
    public void BasinCount() {
        float threshold = 0.01f;
        for(int r = 1; r < row - 1; r++){
            for(int c = 1; c < col - 1; c++ ){

                if( (grid[r][c] + threshold <= grid[r-1][c-1]) && (grid[r][c] + threshold <= grid[r-1][c])
                        && (grid[r][c] + threshold <= grid[r-1][c+1]) && (grid[r][c] + threshold <= grid[r][c-1])
                        && (grid[r][c] + threshold <= grid[r][c+1]) && (grid[r][c] + threshold <= grid[r+1][c-1])
                        && (grid[r][c] + threshold <= grid[r+1][c]) && (grid[r][c] + threshold <= grid[r+1][c+1]))
                {
                    basins += 1;
                    coordinates += r + " " + c + "\n";
                }
            }
        }
    }

    /*
     * This method prints the number of basins and their coordinates
     */
    public void getResults() {
        System.out.println(basins);
        System.out.println(coordinates);
    }
}