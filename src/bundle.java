/*
* @author Jeremy du Plessis
* The bundle class is used to pass the tree object array
* and sunlight exposure matrix between threads efficiently
*/
public class bundle {

    public static tree[] trees;         // an array of tree objects
    public static float[][] sunMatrix;  // a square matrix of per-meter sunglight exposure

    bundle(tree[] trees, float[][] sunMatrix){
        this.sunMatrix = sunMatrix;
        this.trees = trees;
    }

    public static tree[] getTrees() {
        return trees;
    }

    public static float[][] getSunMatrix() {
        return sunMatrix;
    }
}
