package pkg.factory_beans;

import org.springframework.beans.factory.FactoryBean;

public class ToolFactory implements FactoryBean<Tool> {
    private int id = 0;

    @Override
    public Tool getObject() throws Exception {
        return new Tool(id++);
    }

    @Override
    public Class<?> getObjectType() {
        return Tool.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ToolFactory{" +
                "id=" + id +
                '}';
    }
}
