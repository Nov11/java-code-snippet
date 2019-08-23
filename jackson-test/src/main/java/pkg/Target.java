package pkg;

import java.util.Objects;

public class Target {
    public int a;
    private int b;

    private Integer integer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Target target = (Target) o;
        return a == target.a &&
                b == target.b &&
                Objects.equals(integer, target.integer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, integer);
    }
}
