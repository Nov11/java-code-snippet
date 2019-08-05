package pkg.factory_beans;

public class Tool {
    private int id;

    public Tool(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "id=" + id +
                '}';
    }
}
