package pkg;

public class ClassB {
    private ClassA classA;

    public ClassA getClassA() {
        return classA;
    }

    public void setClassA(ClassA classA) {
        this.classA = classA;
    }

    public ClassB() {
        System.out.println("classb ctor");
    }
}
