import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.StringTokenizer;

public class treeUtils {

    public static bundle getTreeMatrix(String filename) throws IOException{

        File file = new File(filename); // load input text file
        BufferedReader br = new BufferedReader(new FileReader(file));

        String[] coords = br.readLine().split(" ");
        int Xsize = Integer.parseInt(coords[0]); // dimension of square sunlight matrix
        int Ysize = Integer.parseInt(coords[1]);
        StringTokenizer digits = new StringTokenizer(br.readLine());    // array of sunlight exposure per square meter

        float[][] sunMatrix = new float[Ysize][Xsize];

        for(int row=0; row<Ysize ; row ++){
            for(int col=0; col<Xsize; col ++){
                sunMatrix[row][col] = Float.parseFloat(digits.nextToken()); // fill sunlight matrix
            }
        }

        int numTrees = Integer.parseInt(br.readLine()); // total number of trees
        tree[] treeList = new tree[numTrees];

        for(int k=0; k<numTrees ; k++){
            String[] temp = br.readLine().trim().split(" ");    // x-coordinate, y-coordinate, dimension of canopy
            treeList[k] = new tree(Integer.parseInt(temp[0]),         // create new tree object
                                Integer.parseInt(temp[1]),
                                Integer.parseInt(temp[2]));
        }
        return (new bundle(treeList, sunMatrix)); // return sunlight matrix and array of tree objects
    }

    static void toFile(tree[] treeList, float avg, String fileName) throws IOException{

        StringBuilder s = new StringBuilder(treeList.length + 2);
        Locale currentLocale = Locale.getDefault();
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.000000", otherSymbols);

        s.append(df.format(avg)); // average sunlight per tree
        s.append(System.getProperty("line.separator"));

        s.append(Integer.toString(treeList.length)); // number of trees

        for(int k=0; k<treeList.length ; k++){
            s.append(System.getProperty("line.separator"));
            s.append(df.format(treeList[k].getSun()));
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(s.toString());
        bw.close();
    }

    public static float sequentialSum(bundle b, int start, int stop){

        tree[] treeList = b.getTrees();         // array of tree objects
        float[][] sunMatrix = b.getSunMatrix(); // sunlight matrix (p/square meter)

        double totalSun = 0.0;

        for(int i=start; i<stop; i++){
            float sum = 0;

            tree tempTree  = treeList[i];
            int x = tempTree.getX(); // coordinates of top left corner of tree in matrix
            int y = tempTree.getY();
            int dim = tempTree.getDimension(); // dimension of tree canopy

            int Xdim = Math.min(x+dim, sunMatrix[0].length); // to handle "overflow" in the X-axis
            int Ydim = Math.min(y+dim, sunMatrix.length); // to handle "overflow" in the Y-axis

            for(int row=y; row<Ydim; row ++){
                for(int col=x; col<Xdim; col ++){
                    float sunLight = sunMatrix[row][col]; // get sun exposure value at point (x,y)
                    sum += sunLight;
                }
            }
            tempTree.setSun(sum); // set total sunlight exposure for a tree
            totalSun += sum; // add sunlight exposure for tree to total sum
        }
        return (float)totalSun; // return total sum
    }

}
