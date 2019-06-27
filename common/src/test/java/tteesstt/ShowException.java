package tteesstt;

import org.junit.Assert;
import org.junit.Test;

public class ShowException {

    @Test
    public void name() {
        Assert.fail("name");
    }

    @Test
    public void name2() {
//        Assert.fail("name2");

        long ret = Long.parseLong("\"162210173\"".trim());
    }
}
