/*
* the treeExperiments class is used for running experiments on
* the speed of parallel vs sequential algorithms for summing
* the data encoded in the sample input file.
* Outputs a text file of speed results of the sequential
* algorithm, as well as speed results of the parallel
* algorithm for variations of the SEQUENTIAL_CUTOFF
*/
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class treeExperinments {

    static final ForkJoinPool fjPool = new ForkJoinPool();
    static long startTime = 0;

    public static void tick(){
        startTime = System.currentTimeMillis();
    }

    public static float tock(){
        return (System.currentTimeMillis() - startTime) / 1000.0f ;
    }

    /*
     * sum creates new fork-join pool for threads
     * calls invoke() on SumArray object which inherits
     * from RecursiveTask<V>
     */
    static float sum(bundle b){
        return fjPool.commonPool().invoke(new SumArray(0, b.getTrees().length, b));

    }

    /*
    * runs 20 iterations of sequential sum algorithm
    * returns average time for the final 17
    * iterations
    */
    static String runSequential(bundle b){
        float avgTime = 0;
        for(int i=0; i<20; i++){
	    System.gc(); // garbage collector
            tick();
            treeUtils.sequentialSum(b, 0, bundle.getTrees().length);
            float time = tock();
            if(i>2)
                avgTime +=time;
        }
        return "Average time (seconds) taken for sequential sum: \n" + Float.toString(avgTime/17)+"\n";
    }

    /*
     * runs 20 iterations of sequential sum algorithm
     * returns average time for the final 17
     * iterations
     */
    static String runParallel(bundle b){
        float avgTime = 0;
        for(int i=0; i<20; i++){
	    System.gc(); // garbage collector
            tick();
            sum(b);
            float time = tock();
            if(i>2)
                avgTime +=time;
        }
        return Float.toString(avgTime/17)+",";
    }

    /*
     * calls runSequential() and then runParallel() with
     * varying values of the SEQUENTIAL_CUTOFF
     * writes results to file
     */
    static void runAll(bundle b, int dataSize) throws IOException{
        StringBuilder sb = new StringBuilder();
        sb.append("Test Results: Dataset Size " + dataSize +"\n");

        sb.append(runSequential(b)); // run sequential sum algorithm

        sb.append("Average time (seconds) taken for parallel sums with different sequential cutoffs (50 to 2000, steps of 25):\n");
        for(int s=50; s<=1000 ; s+=25) {
            Constants.setSequentialCutoff(s); // increment sequential cutoff by 25
            sb.append(runParallel(b)+"\n"); // run parallel sum algorithm
        }
        System.out.println("Writing results to: testResults/...");
        BufferedWriter bw = new BufferedWriter(new FileWriter("testResults/Data_Set_Size:_"+Integer.toString(dataSize)));
        bw.write(sb.toString());
        bw.close();
    }

    public static void main(String[] args) throws IOException {
        String fileNameIn = Constants.MAIN_INPUT;
        int size = Integer.parseInt(args[0]);

        System.out.println("Running Experiment with Data Set Size "+size+"...");

        bundle b = treeUtils.getTreeMatrix(fileNameIn); // returns matrix of sunlight exposure, and a list of tree objects in a bundle object

        tree[] tempTrees = Arrays.copyOfRange(b.getTrees(), 0, size); // create a subset of the tree object array
        bundle tempBundle = new bundle(tempTrees, b.getSunMatrix()); // create a new bundle for the experiment
        runAll(tempBundle, size); // run experiment

        System.out.println("Done");

    }
}
