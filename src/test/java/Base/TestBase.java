package Base;

import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class TestBase {

        @Rule
        public TestWatcher watchman = new TestWatcher() {
            @Override
            protected void failed(Throwable e, Description description) {
                System.out.println("Fail!");
            }

            @Override
            protected void succeeded(Description description) {

                System.out.println("Success!");
            }
        };

        @Test
        public void succeedingTest() {
            Assert.assertTrue(true);
        }

        @Test
        public void failingTest() {
            Assert.assertTrue(false);
        }
    }

