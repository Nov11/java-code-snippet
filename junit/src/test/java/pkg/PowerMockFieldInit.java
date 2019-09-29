package pkg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ComplexClassInitialization.class)
public class PowerMockFieldInit {

    @Before
    public void setUp() throws Exception {
        Initialized initialized = PowerMockito.mock(Initialized.class);
        PowerMockito.when(initialized.returnSomeThing()).thenReturn("mocked");
        PowerMockito.whenNew(Initialized.class).withAnyArguments().thenReturn(initialized);
    }

    @Test
    public void testCase() {
        ComplexClassInitialization complexClassInitialization = new ComplexClassInitialization();
    }
}
