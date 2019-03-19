package spring_profile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import spring_profile.config.DefaultConfig;
import spring_profile.config.DevConfig;
import spring_profile.config.NormalConfig;
import spring_profile.obj.DefaultClass;
import spring_profile.obj.DevClass;
import spring_profile.obj.DuplicateBeanInDevAndDefault;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"dev", "default"})
@ContextConfiguration(classes = {DefaultConfig.class, NormalConfig.class, DevConfig.class})
public class DriveDupDef {

    @Autowired
    DuplicateBeanInDevAndDefault duplicateBeanInDevAndDefault;

    @Test
    public void notNull() {
        Assert.notNull(duplicateBeanInDevAndDefault, "should not be null");
    }
}