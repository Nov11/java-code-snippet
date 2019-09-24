package basic.losingEx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//exception is caught but swallowed.
public class SwallowInFinallyWIthACatch {
    private static final Logger logger = LoggerFactory.getLogger(SwallowInFinallyWIthACatch.class);
    private static void foo(){
        try{
            throw new RuntimeException("in try");
        }catch (RuntimeException ex){
            logger.info("caught");
            throw new RuntimeException("catch " + ex.getMessage());
        }finally {
            throw new RuntimeException("finally");
        }
    }

    public static void main(String[] args) {
        foo();
    }
}
