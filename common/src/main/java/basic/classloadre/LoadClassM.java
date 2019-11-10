package basic.classloadre;

public class LoadClassM {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class c = LoadClassM.class.getClassLoader().loadClass("basic.classloadre.StaticField");


        System.out.println("before newInstance");

        StaticField obj = (StaticField) c.newInstance();
    }
}
