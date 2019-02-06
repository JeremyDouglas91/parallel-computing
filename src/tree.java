public class tree { // a tree object
    int x;          // x,y coordinates of top left corner of tree in the sunlight exposure matrix
    int y;
    int dimension;  // Dimension of one side of a square canopy
    float sun;      // total sun exposure for tree

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getSun(){
        return sun;
    }

    public void setSun(float s){
        this.sun = s;
    }

    public int getDimension() {
        return dimension;
    }

    public tree(int y, int x, int dimension){
        this.x = x;
        this.y = y;
        this.dimension = dimension;
        this.sun = 0;
    }

}
