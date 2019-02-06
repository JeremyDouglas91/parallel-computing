/*
* @author Jeremy du Plessis
* treeSumParallel is the driver class which takes in a text file
* containing data pertaining to sunlight exposure of a sqaure terrain
* and the coordinates and dimensions of trees on the terrain
* and writes the results of the average sunlight exposure, number of trees
* and total sunlight per tree to an output file*/
import java.util.concurrent.ForkJoinPool;
import java.io.IOException;

public class treeSumParallel {

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
    * main method driving treeSumParallel program
    */
    public static void main(String[] args) throws IOException {
        String fileNameIn = args[0].trim();
        String fileNameOut = args[1].trim();

        System.out.println("Running treeSumParallel...");
        System.out.println("Reading data from "+fileNameIn);
        bundle b = treeUtils.getTreeMatrix(fileNameIn); // returns matrix of sunlight dispersion, and a list of tree objects in a bundle object
        float total = 0;
        float time = 0;

        // run parallel sum 6 times, take the average time of last 3 runs
	System.out.println("Sequential cut off: "+Constants.SEQUENTIAL_CUTOFF);
        System.out.println("Running 8 iterations of parallel sum (allowing first 3 for optimisation)");
        for(int i=0; i<8; i++){
            System.gc();        // call garbage collector before parallel sum algorithm to maximise efficiency
            tick();             // record start time
            total = sum(b);     // calculate sum in parallel
            if(i>2){
                time += tock(); // time taken for one run
	    }
        }

        float avg = total/b.getTrees().length*1.0f; // average sunlight per tree

        System.out.println("Writing results to "+fileNameOut);
        treeUtils.toFile(b.getTrees(), avg, fileNameOut);       // write results to file
        System.out.println("Done.");
        System.out.println("Average Time Taken: " + Float.toString(time/5) + " Seconds");
    }
}