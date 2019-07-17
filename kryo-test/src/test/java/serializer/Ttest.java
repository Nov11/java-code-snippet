package serializer;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ttest {
    @Test
    public void foutput() throws IOException {
        File file = new File("/tmp/1234");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileInputStream fileInputStream = new FileInputStream(file);
        fileOutputStream.write(1);
        fileOutputStream.close();
        int ret = fileInputStream.read();
        System.out.println(ret);
        fileInputStream.close();
        file.delete();
    }
}
