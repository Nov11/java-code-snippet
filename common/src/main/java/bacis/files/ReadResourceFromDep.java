package bacis.files;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadResourceFromDep {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Class c = ReadResourceFromDep.class;

        Path path = Paths.get(c.getClassLoader().getResource("dumb.txt").toURI());

        System.out.println(path);

        String s = new String(Files.readAllBytes(path));

        System.out.println(s);
    }
}
