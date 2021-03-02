package pkg;

import javassist.*;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.SignatureAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class ClassWithStaticField {
    private static final Logger logger = LoggerFactory.getLogger(ClassWithStaticField.class);
    private static final List<String> strings = Arrays.asList("a", "b", "c");

    public static void main(String[] args) throws NotFoundException, CannotCompileException, BadBytecode, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass("pkg.MadeFromAir");

        StringBuilder sb = new StringBuilder();
        sb.append("private static java.util.List init() {");
        sb.append("java.util.List list = new java.util.ArrayList();");
        for (String s : strings) {
            sb.append(String.format("list.add(\"%s\");", s));
        }
        sb.append("return list;}");

        logger.info("init : {}", sb.toString());

        ctClass.addMethod(CtNewMethod.make(sb.toString(), ctClass));
        ctClass.addField(CtField.make("public static java.util.List list = init();", ctClass));

        Class<?> aClass = ctClass.toClass();


        Object obj = aClass.getConstructors()[0].newInstance();
        Field f = obj.getClass().getDeclaredField("list");
        List<String> actual = (List<String>) f.get(obj);
        logger.info("actual : {}", actual);
    }

    private static String getGenericSignature(Class relatedClass) throws BadBytecode {
        String fieldSignature = "L" + List.class.getCanonicalName().replace(".", "/") + "<L" + String.class.getCanonicalName().replace(".", "/") + ";>;";
        return SignatureAttribute.toClassSignature(fieldSignature).encode();
    }
}
