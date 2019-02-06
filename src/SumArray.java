/*
* @author Jeremy du Plessis
* The sumArray class  is essentially a thread which inherits
* from RecursiveTask, used by the fork-join framework, a fork-join
* pool of threads distributes work to cores.
*/
import java.util.concurrent.RecursiveTask;

public class SumArray extends RecursiveTask<Float> { // returns floating point value (a sum)

    int lo; // point to start summing in array
    int hi; // point to end summing in array
    bundle b; // matrix of sunlight exposure values & array of tree objects

    SumArray(int l, int h, bundle b){
        lo = l;
        hi = h;
        this.b = b;
    }

    @Override
    protected Float compute(){

        if ((hi - lo) < Constants.SEQUENTIAL_CUTOFF) {  // minimum tasks a thread must handle
            return treeUtils.sequentialSum(b, lo, hi);
        }

        else{
            SumArray left = new SumArray(lo,(hi+lo)/2, b); // new SumArray (RecursiveTask) object, interval is halved with each recursive step
            SumArray right= new SumArray((hi+lo)/2, hi, b);

            left.fork(); // branch off thread
            float rightAns = right.compute(); // main thread does this
            float leftAns = left.join(); // wait until left is done

            return leftAns + rightAns ; // merge left and right threads
        }

    }
}
