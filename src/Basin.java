import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Basin {

    static long startTime = 0;
    
    /*
     * This method is used to obtain the current system time.
     */
    private static void tick(){
        startTime = System.currentTimeMillis();
    }

    /*
     * This method is used to obtain the current system time after an algorithm has run and deduct the original time obtained in tick() from it.
     */
    private static float tock(){
        return (System.currentTimeMillis() - startTime) / 1000.0f;
    }

    static final ForkJoinPool fjPool = new ForkJoinPool();

    static int basinFinal(float[][] grid, int column){
        return fjPool.invoke(new ParallelBasin(grid,0,grid.length, column));
    }

    /*
     * This method is the main method. It is used to read in the data files, output the data and obtain the necessary values for the time trials.
     * @param args
     */
    public static void main(String[] args) {
        String fileIn = args[0];
        String fileOut = args[1];

        try {
            //outputs data to fileOut
            PrintStream out = new PrintStream(new File("./Data/" + fileOut));
            System.setOut(out);
            
            //reads in data from fielIn
            Scanner scan = new Scanner(new File("./Data/" + fileIn), "UTF-8");
            int row = scan.nextInt();
            int col = scan.nextInt();
            float [][] grid = new float[row][col];

            while(scan.hasNextFloat()) {
                for(int r = 0; r < row; r++) {
                    for (int c = 0; c < col; c++) {
                        grid[r][c] = scan.nextFloat();
                    }
                }
            }

            scan.close();
            
            //for loop used to run the two algorithms mutliple times in order to obtain a variation of results and obtain an average.
            //for (int i = 0; i < 20; i++) {

                System.gc();
                tick();
                int par = basinFinal(grid, col);
                float timePar = tock();
                System.out.println(par);

                SequentialBasin seq = new SequentialBasin(grid, row, col);
                System.gc();
                tick();
                seq.BasinCount();
                float timeSeq = tock();
                seq.getResults();
                
                //if statements used for testing the parallel and sequential processing times
                /*
                if(i > 4){
                    timePar += timePar;
                    timeSeq += timeSeq;
                }

                if(i == 19){
                    System.out.println("Parallel: " + timePar/15);
                    System.out.println("Sequential: " + timeSeq/15);
                }
                */
            //}
        }
        catch (FileNotFoundException e) {
            System.out.println("Unable to open input file ");
            e.printStackTrace();
        }
    }
}
