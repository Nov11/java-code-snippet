package pkg;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ClassWithStaticMethod.class})
public class PowerMockTest {

    @Test
    public void nameTest() {
        String ret = ClassWithStaticMethod.foo().bar();
        Assert.assertEquals("foo", ret);
    }

    @Test
    public void mockChainTest() {
        mockStatic(ClassWithStaticMethod.class);
        ClassWithStaticMethod.RetValue mocked = mock(ClassWithStaticMethod.RetValue.class);
        when(ClassWithStaticMethod.foo()).thenReturn(mocked);
        when(mocked.bar()).thenReturn("mocked");
        String ret = ClassWithStaticMethod.foo().bar();

        Assert.assertEquals("mocked", ret);
    }

    @Test
    public void mockStaticTest() {
        mockStatic(ClassWithStaticMethod.class);
        ClassWithStaticMethod.RetValue retValue = new ClassWithStaticMethod.RetValue();
        when(ClassWithStaticMethod.foo()).thenReturn(retValue);
        String ret = ClassWithStaticMethod.foo().bar();

        Assert.assertEquals("bar", ret);
    }
}
