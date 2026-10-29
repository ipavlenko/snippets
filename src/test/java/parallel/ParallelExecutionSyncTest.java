package parallel;

import org.junit.jupiter.api.*;

import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParallelExecutionSyncTest implements ParallelExecutionTestBase {
    private final Counters counters = new Counters();

    @Order(1)
    @TestFactory
    Stream<DynamicNode> testSequence() {
        return doTestParallelExecution(new ParallelExecutionSync(), counters);
    }

    @Order(2)
    @TestFactory
    Stream<DynamicNode> testResults() {
        return doTestTotals(counters);
    }
}
