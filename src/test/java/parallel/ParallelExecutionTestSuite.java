package parallel;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ParallelExecutionPlainTest.class,
        ParallelExecutionVolatileTest.class,
        ParallelExecutionSyncTest.class,
})
class ParallelExecutionTestSuite {
}
