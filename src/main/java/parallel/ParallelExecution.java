package parallel;

public interface ParallelExecution {

    Result execute(int max, int count) throws InterruptedException;

    class Result {
        public final int boxedValue;
        public final int[] resultsByThread;

        Result(int boxedValue, int[] resultsByThread) {
            this.boxedValue = boxedValue;
            this.resultsByThread = resultsByThread;
        }
    }
}
