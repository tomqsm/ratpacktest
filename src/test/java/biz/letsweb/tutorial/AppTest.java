package biz.letsweb.tutorial;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ratpack.test.MainClassApplicationUnderTest;

import static org.junit.Assert.assertEquals;

/**
 * @author tomasz
 */
@RunWith(JUnit4.class)
public class AppTest {
    MainClassApplicationUnderTest appUnderTest
            = new MainClassApplicationUnderTest(RatpackApp.class);

    @After
    public void shutdown() {
        appUnderTest.close();
    }

    @Test
    public void givenDefaultUrl_getStaticText() {
        assertEquals("Welcome to baeldung ratpack!!!",
                appUnderTest.getHttpClient().getText("/"));
    }
}
