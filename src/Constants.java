public class Constants { // Shared constant values

    public static int SEQUENTIAL_CUTOFF = 500;
    public static final String MAIN_INPUT = "data/main_input.txt";
    public static final String SAMPLE_INPUT = "data/sample_input.txt";

    // this setter method is only used for experiments, not for running the solution code
    public static void setSequentialCutoff(int sequentialCutoff) {
        SEQUENTIAL_CUTOFF = sequentialCutoff;
    }
}
