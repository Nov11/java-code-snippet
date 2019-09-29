package pkg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(System.class)
public class PowerMockSystemClass {
    private static final Logger logger = LoggerFactory.getLogger(PowerMockSystemClass.class);
    @Before
    public void setUp() throws Exception {
        mockStatic(System.class);
        when(System.currentTimeMillis()).thenReturn(100L);
    }

    @Test
    public void currentMillis() {
        logger.info("{}", System.currentTimeMillis());
    }
}
