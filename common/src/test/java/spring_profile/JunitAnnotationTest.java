package spring_profile;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JunitAnnotationTest.Config.class)
public class JunitAnnotationTest {

//    @Configuration
    @ComponentScan
    static class Config {

    }

    @Autowired
    TestObject testObject;

    @Test
    public void name() {
        Assert.assertNotNull(testObject);
    }
}
