package bacis.generics;

import java.util.function.Function;

public class GenericTest {
    public static void foo(Function<? super AA, ? extends BB> function) {

    }

    public static B f1(A param) {
        return null;
    }

    public static B f2(AA param) {
        return null;
    }

    public static B f3(AAA param) {
        return null;
    }

    public static BB f11(A param) {
        return null;
    }

    public static BB f12(AA param) {
        return null;
    }

    public static BB f13(AAA param) {
        return null;
    }

    public static BBB f21(A param) {
        return null;
    }

    public static BBB f22(AA param) {
        return null;
    }

    public static BBB f23(AAA param) {
        return null;
    }

    public static <T extends AA> void t(T a) {
    }

    public static void main(String[] args) {
//        foo(GenericTest::f1);
//        foo(GenericTest::f2);
//        foo(GenericTest::f3);
//
//        foo(GenericTest::f11);
//        foo(GenericTest::f12);
//        foo(GenericTest::f13);
//
//        foo(GenericTest::f21);
//        foo(GenericTest::f22);
//        foo(GenericTest::f23);
//
//        t(new A());
//        t(new AA());
//        t(new AAA());
    }

    static class A {
    }

    static class AA {
    }

    static class AAA {
    }

    static class B {
    }

    static class BB {
    }

    static class BBB {
    }
}
