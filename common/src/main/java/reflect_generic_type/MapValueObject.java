package reflect_generic_type;

import java.lang.reflect.*;
import java.util.*;

public class MapValueObject {

    public Map<String, Integer> foo() {
        return new HashMap<>();
    }

    public List<String> bar() {
        return new ArrayList<>();
    }

    /**
     * list<...> / map<..,..>
     *
     * @param parameterizedType
     * @return
     */
    static Object genericDefaultReturnValue(ParameterizedType parameterizedType) {
        System.out.println(parameterizedType);
        Class<?> container = (Class) parameterizedType.getRawType();
        if (Collection.class.isAssignableFrom(container)) {
            Class<?> valueClass = getValueClass(parameterizedType, 0);
            return makeObj(valueClass);
        } else if (Map.class.isAssignableFrom(container)) {
            Class<?> valueClass = getValueClass(parameterizedType, 1);
            return makeObj(valueClass);
        } else {
            return null;
        }
    }

    private static Class<?> getValueClass(ParameterizedType parameterizedType, int idx) {
        Class<?> valueClass;
        Type p = parameterizedType.getActualTypeArguments()[idx];
        if (p instanceof ParameterizedType) {
            Type raw = ((ParameterizedType) p).getRawType();
            valueClass = (Class<?>) raw;
        } else {
            valueClass = (Class<?>) p;
        }
        return valueClass;
    }

    private static Object makeObj(Class<?> tClass) {
        //collections
        if (Map.class.isAssignableFrom(tClass)) {
            return Collections.emptyMap();
        }
        if (Collection.class.isAssignableFrom(tClass)) {
            if (List.class.isAssignableFrom(tClass)) {
                return Collections.emptyList();
            }
            if (Set.class.isAssignableFrom(tClass)) {
                return Collections.emptySet();
            }
            if (Enumeration.class.isAssignableFrom(tClass)) {
                return Collections.emptyEnumeration();
            }
        }

        //object with default ctors, including thrift/avro generated class
        try {
            Constructor constructor = tClass.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

        }

        //numbers
        if (Number.class.isAssignableFrom(tClass)) {
            return 0;
        }

        if (Boolean.class.isAssignableFrom(tClass)) {
            return false;
        }


        //protobuf
        Method method = null;
        try {
            method = tClass.getMethod("getDefaultInstance");
            return method.invoke(null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(e);
        }

        return null;
    }

    private static void test(String methodName) throws NoSuchMethodException, InstantiationException, IllegalAccessException {
        Method foo = MapValueObject.class.getMethod(methodName);
        ParameterizedType parameterizedType = (ParameterizedType) foo.getGenericReturnType();
        Object ret = genericDefaultReturnValue(parameterizedType);
        System.out.println(ret.getClass().getSimpleName());
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException {
//        test("foo");
//        test("bar");

//        Integer i = new Integer(0);

        Method foo = MapValueObject.class.getMethod("foo");
        ParameterizedType parameterizedType = (ParameterizedType) foo.getGenericReturnType();
        System.out.println(parameterizedType.getActualTypeArguments()[0]);
    }
}
