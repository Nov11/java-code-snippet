package basic.files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class LoadResourceFilePath {
    private static final Logger logger = LoggerFactory.getLogger(LoadResourceFilePath.class);
    private static final String filePath = "some_file.json";

    private void foo() throws URISyntaxException {
        Class c = LoadResourceFilePath.class;
        {
            Path path = Paths.get(Objects.requireNonNull(c.getClassLoader().getResource("some_file.json")).toURI());
            System.out.println(path);
        }

        {
            Path path = Paths.get(c.getResource("/some_file.json").toURI());
            System.out.println(path);
        }
    }

    private void bar1() {
        {
            URL url = getClass().getResource("blabla.json");
            System.out.println("class get relative url: " + url);
        }

        {
            URL url = getClass().getResource("/" + filePath);
            System.out.println("class get absolute url: " + url);
        }
    }

    private void bar2() {
        {
            URL url = getClass().getClassLoader().getResource(filePath);
            System.out.println("class loader get relative url: " + url);
        }

        {
            URL url = getClass().getClassLoader().getResource("basic/files/blabla.json");
            System.out.println("class loader get absolute url: " + url);
        }
    }

    public static void main(String[] args) throws URISyntaxException {
        LoadResourceFilePath loadResourceFilePath = new LoadResourceFilePath();
        loadResourceFilePath.bar1();
        loadResourceFilePath.bar2();
    }
}
