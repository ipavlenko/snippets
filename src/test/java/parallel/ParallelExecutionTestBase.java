package parallel;

import org.junit.jupiter.api.DynamicNode;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

interface ParallelExecutionTestBase {
    int ATTEMPTS_COUNT = 10;
    int THREADS_COUNT = 2;
    int MAX_VALUE = 10_000_000;

    default Stream<DynamicNode> doTestParallelExecution(ParallelExecution execution, Counters counters) {
        return IntStream.range(0, ATTEMPTS_COUNT).mapToObj(i -> dynamicTest(
                String.format("Attempt %s", i),
                () -> {
                    var result = execution.execute(MAX_VALUE, THREADS_COUNT);
                    System.out.printf("Boxed Value: %s%n", result.boxedValue);
                    boolean isOk = result.boxedValue >= MAX_VALUE;
                    if (!isOk) {
                        counters.belowMaxValueCounter.incrementAndGet();
                    }
                    if (result.boxedValue == MAX_VALUE * THREADS_COUNT) {
                        counters.hitsCounter.incrementAndGet();
                    }
                    assertTrue(
                            isOk,
                            String.format("Boxed Value: %s, By Thread: %s", result.boxedValue, Arrays.toString(result.resultsByThread)));
                }));
    }

    default Stream<DynamicNode> doTestTotals(Counters counters) {
        return Stream.of(
                dynamicTest(
                        String.format("Equal to %s: %s", THREADS_COUNT * MAX_VALUE, counters.hitsCounter.get()),
                        () -> {}),
                dynamicTest(
                        String.format("Below %s: %s", MAX_VALUE, counters.belowMaxValueCounter.get()),
                        () -> assertEquals(0, counters.belowMaxValueCounter.get()))
        );
    }

    class Counters {
        AtomicInteger hitsCounter = new AtomicInteger();
        AtomicInteger belowMaxValueCounter = new AtomicInteger();
    }
}
