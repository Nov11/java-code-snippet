package reflect_generic_type;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import static reflect_generic_type.MapValueObject.genericDefaultReturnValue;

public class GenericDefaultValueTest {

    //类似protobuf，没有默认构造函数，但是有个getDefaultInstanceForType
    public static class B {
        private B(int i) {
        }

        public static B getDefaultInstance() {
            return new B(1);
        }
    }

    interface A {
        //value 有默认构造
        Map<String, Integer> m1();

        //value没有默认构造，是number
        Map<String, String> m2();

        //value 是collection
        Map<String, Map<String, Integer>> m3();

        //value是collection
        Map<String, List<String>> m4();

        //value是collection
        Map<String, Set<String>> m5();

        //value没有默认构造，是protobuf那种
        Map<String, B> m6();

        //value没有默认构造
        List<Integer> l1();

        //value有默认构造
        List<String> l2();

        //value是collection
        List<Map<String, Integer>> l3();

        //value是collection
        List<List<Integer>> l4();

        //value是collection
        List<Set<Integer>> l5();

        List<B> l6();
    }

    Map<String, Class> expect = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        expect.put("m1", Integer.class);
        expect.put("m2", String.class);
        expect.put("m3", Collections.emptyMap().getClass());
        expect.put("m4", Collections.emptyList().getClass());
        expect.put("m5", Collections.emptySet().getClass());
        expect.put("m6", B.class);

        expect.put("l1", Integer.class);
        expect.put("l2", String.class);
        expect.put("l3", Collections.emptyMap().getClass());
        expect.put("l4", Collections.emptyList().getClass());
        expect.put("l5", Collections.emptySet().getClass());
        expect.put("l6", B.class);
    }


    void test(String prefix) throws NoSuchMethodException {
        for (int i = 1; i <= 6; i++) {
            String name = prefix + i;
            Method method = A.class.getMethod(name);
            ParameterizedType parameterizedType = (ParameterizedType) method.getGenericReturnType();
            Object ret = genericDefaultReturnValue(parameterizedType);
            Class expected = expect.get(name);
            Assert.assertNotNull(ret);
            Assert.assertEquals(expected, ret.getClass());
        }
    }

    @Test
    public void genericDefaultReturnValueTest() throws NoSuchMethodException {
        test("m");
        test("l");
    }
}