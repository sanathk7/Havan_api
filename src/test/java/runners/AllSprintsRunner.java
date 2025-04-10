package runners;

import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectClasses;

@Suite
@SelectClasses({
    TestRunner.class,
    SecondSprintRunner.class
})
public class AllSprintsRunner {}
