package parallel;

public class ParallelExecutionVolatile implements ParallelExecution {

    public Result execute(int max, int count) throws InterruptedException {
        final Box box = new Box();
        final int[] results = new int[count];
        Thread[] threads = new Thread[count];

        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(createRunnable(box, max, i, results));
        }

        for (int i = 0; i < count; i++) {
            threads[i].start();
        }

        for (int i = 0; i < count; i++) {
            threads[i].join();
        }

        return new Result(box.value, results);
    }

    Runnable createRunnable(final Box box, int max, int index, int[] results) {
        return () -> {
            for(int i = 0; i < max; i++) {
                box.value++;
            }
            results[index] = box.value;
        };
    }

    static class Box {
        public volatile int value = 0;
    }
}
