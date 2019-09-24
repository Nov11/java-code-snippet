package basic;

public interface Inter {
    default void ptr() {
        System.out.println(this.getClass().getSimpleName());
    }
}
